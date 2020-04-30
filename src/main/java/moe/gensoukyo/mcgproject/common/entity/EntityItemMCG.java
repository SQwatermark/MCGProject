package moe.gensoukyo.mcgproject.common.entity;

import moe.gensoukyo.mcgproject.common.init.ModItem;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

import javax.annotation.Nullable;

@MCGEntity("item_mcg")
public class EntityItemMCG extends Entity {
    private static final DataParameter<ItemStack> ITEM = EntityDataManager.createKey(EntityItemMCG.class, DataSerializers.ITEM_STACK);
    /**
     * The age of this EntityItem (used to animate it up and down as well as expire it)
     */
    private int age;
    private int pickupDelay;
    public int angle;
    /**
     * The health of this EntityItem. (For example, damage for tools)
     */
    private int health;

    /**
     * The EntityItem's random initial float height.
     */
    public float hoverStart;

    /**
     * The maximum age of this EntityItem.  The item is expired once this is reached.
     */
    public int lifespan = 6000;

    public EntityItemMCG(World worldIn, double x, double y, double z) {
        super(worldIn);
        this.health = 5;
        this.hoverStart = (float) (Math.random() * Math.PI * 2.0D);
        this.setSize(0.25F, 0.25F);
        this.setPosition(x, y, z);
        this.rotationYaw = (float) (Math.random() * 360.0D);
        this.motionX = (float) (Math.random() * 0.20000000298023224D - 0.10000000149011612D);
        this.motionY = 0.20000000298023224D;
        this.motionZ = (float) (Math.random() * 0.20000000298023224D - 0.10000000149011612D);
        this.angle = rand.nextInt(90);
    }

    public EntityItemMCG(World worldIn, double x, double y, double z, ItemStack stack) {
        this(worldIn, x, y, z);
        this.setItem(stack);
        stack.getItem();
        this.lifespan = stack.getItem().getEntityLifespan(stack, worldIn);
    }

    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
    protected boolean canTriggerWalking() {
        return false;
    }

    public EntityItemMCG(World worldIn) {
        super(worldIn);
        this.health = 5;
        this.hoverStart = (float) (Math.random() * Math.PI * 2.0D);
        this.setSize(0.25F, 0.25F);
        this.setItem(ItemStack.EMPTY);
    }

    protected void entityInit() {
        this.getDataManager().register(ITEM, ItemStack.EMPTY);
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        if (this.getItem().isEmpty())
        {
            this.setDead();
        }
        else
        {
            super.onUpdate();

            this.prevPosX = this.posX;
            this.prevPosY = this.posY;
            this.prevPosZ = this.posZ;
            double d0 = this.motionX;
            double d1 = this.motionY;
            double d2 = this.motionZ;

            if (!this.hasNoGravity())
            {
                this.motionY -= 0.03999999910593033D;
            }

            if (this.world.isRemote)
            {
                this.noClip = false;
            }
            else
            {
                this.noClip = this.pushOutOfBlocks(this.posX, (this.getEntityBoundingBox().minY + this.getEntityBoundingBox().maxY) / 2.0D, this.posZ);
            }

            this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
            boolean flag = (int)this.prevPosX != (int)this.posX || (int)this.prevPosY != (int)this.posY || (int)this.prevPosZ != (int)this.posZ;

            if (flag || this.ticksExisted % 25 == 0)
            {
                if (this.world.getBlockState(new BlockPos(this)).getMaterial() == Material.LAVA)
                {
                    this.motionY = 0.20000000298023224D;
                    this.motionX = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F;
                    this.motionZ = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F;
                    this.playSound(SoundEvents.ENTITY_GENERIC_BURN, 0.4F, 2.0F + this.rand.nextFloat() * 0.4F);
                }

            }

            float f = 0.98F;

            if (this.onGround)
            {
                BlockPos underPos = new BlockPos(MathHelper.floor(this.posX), MathHelper.floor(this.getEntityBoundingBox().minY) - 1, MathHelper.floor(this.posZ));
                net.minecraft.block.state.IBlockState underState = this.world.getBlockState(underPos);
                f = underState.getBlock().getSlipperiness(underState, this.world, underPos, this) * 0.98F;
            }

            this.motionX *= f;
            this.motionY *= 0.9800000190734863D;
            this.motionZ *= f;

            if (this.onGround)
            {
                this.motionY *= -0.5D;
            }

            if (this.age != -32768)
            {
                ++this.age;
            }

            this.handleWaterMovement();

            if (!this.world.isRemote)
            {
                double d3 = this.motionX - d0;
                double d4 = this.motionY - d1;
                double d5 = this.motionZ - d2;
                double d6 = d3 * d3 + d4 * d4 + d5 * d5;

                if (d6 > 0.01D)
                {
                    this.isAirBorne = true;
                }
            }

            ItemStack item = this.getItem();

            if (item.isEmpty())
            {
                this.setDead();
            }
        }
    }

    public boolean handleWaterMovement() {
        if (this.world.handleMaterialAcceleration(this.getEntityBoundingBox(), Material.WATER, this)) {
            if (!this.inWater && !this.firstUpdate) {
                this.doWaterSplashEffect();
            }

            this.inWater = true;
        } else {
            this.inWater = false;
        }

        return this.inWater;
    }

    protected void dealFireDamage(int amount) {
        this.attackEntityFrom(DamageSource.IN_FIRE, (float) amount);
    }



    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound compound) {
        compound.setShort("Health", (short) this.health);
        compound.setShort("Age", (short) this.age);
        compound.setShort("PickupDelay", (short) this.pickupDelay);
        compound.setInteger("Lifespan", lifespan);

        if (!this.getItem().isEmpty()) {
            compound.setTag("Item", this.getItem().writeToNBT(new NBTTagCompound()));
        }
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound) {
        this.health = compound.getShort("Health");
        this.age = compound.getShort("Age");

        if (compound.hasKey("PickupDelay")) {
            this.pickupDelay = compound.getShort("PickupDelay");
        }

        NBTTagCompound nbttagcompound = compound.getCompoundTag("Item");
        this.setItem(new ItemStack(nbttagcompound));

        if (this.getItem().isEmpty()) {
            this.setDead();
        }
        if (compound.hasKey("Lifespan")) lifespan = compound.getInteger("Lifespan");
    }

    public String getName() {
        return this.hasCustomName() ? this.getCustomNameTag() : I18n.translateToLocal("item." + this.getItem().getTranslationKey());
    }

    public ItemStack getItem() {
        return this.getDataManager().get(ITEM);
    }

    public void setItem(ItemStack stack) {
        this.getDataManager().set(ITEM, stack);
        this.getDataManager().setDirty(ITEM);
    }

    @Override
    public boolean canBePushed() {
        return true;
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        return dropItem();
    }

    @Override
    public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
        return dropItem();
    }

    public boolean dropItem() {
        if (!world.isRemote) {
            ItemStack itemStack = this.getItem();
            if (Block.getBlockFromItem(this.getItem().getItem()) == Blocks.STONE) itemStack = new ItemStack(ModItem.ITEM_LITTLE_STONE);
            this.entityDropItem(itemStack, 0);
            this.setDead();
        }
        return true;
    }

    @Nullable
    public AxisAlignedBB getCollisionBox(Entity entityIn)
    {
        return entityIn.canBePushed() ? entityIn.getEntityBoundingBox() : null;
    }

    @Nullable
    public AxisAlignedBB getCollisionBoundingBox()
    {
        return this.getEntityBoundingBox();
    }
}
