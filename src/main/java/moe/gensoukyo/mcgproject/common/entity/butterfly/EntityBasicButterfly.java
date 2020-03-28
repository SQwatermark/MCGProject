package moe.gensoukyo.mcgproject.common.entity.butterfly;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

//代码取自 kathairis mod 并进行了修改，仅用于测试
public abstract class EntityBasicButterfly extends EntityAmbientCreature
{
    private static final DataParameter<Integer> VARIANT = EntityDataManager.createKey(EntityBasicButterfly.class, DataSerializers.VARINT);

    /** Coordinates of where the bat spawned. */
    private BlockPos spawnPosition;

    public EntityBasicButterfly(World worldIn)
    {
        super(worldIn);
        this.setSize(0.6F, 0.5F);
        this.experienceValue=1;
    }

    public boolean canBeLeashedTo(EntityPlayer player)
    {
        return false;
    }

    public int getMaxSpawnedInChunk()
    {
        return 2;
    }

    /**
     * Returns the volume for the sounds this mob makes.
     */
    protected float getSoundVolume()
    {
        return 1F;
    }

    /**
     * Gets the pitch of living sounds in living entities.
     */
    protected float getSoundPitch()
    {
        return super.getSoundPitch() * 0.95F;
    }

    @Nullable
    public SoundEvent getAmbientSound()
    {
        return null;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return null;
    }

    protected SoundEvent getDeathSound()
    {
        return null;
    }

    /**
     * Returns true if this entity should push and be pushed by other entities when colliding.
     */
    public boolean canBePushed()
    {
        return false;
    }

    protected void collideWithEntity(Entity entityIn)
    {
    }

    protected void collideWithNearbyEntities()
    {
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        super.onUpdate();
        this.motionY *= 0.6000000238418579D;
    }

    protected void updateAITasks()
    {
        super.updateAITasks();

        if (this.spawnPosition != null && (!this.world.isAirBlock(this.spawnPosition) || this.spawnPosition.getY() < 1))
        {
            this.spawnPosition = null;
        }

        if (this.spawnPosition == null || this.rand.nextInt(30) == 0 || this.spawnPosition.distanceSq((int)this.posX, (int)this.posY, (int)this.posZ) < 4.0D)
        {
            this.spawnPosition = new BlockPos((int)this.posX + this.rand.nextInt(7) - this.rand.nextInt(7), (int)this.posY + this.rand.nextInt(6) - 2, (int)this.posZ + this.rand.nextInt(7) - this.rand.nextInt(7));
        }

        double d0 = (double)this.spawnPosition.getX() + 0.5D - this.posX;
        double d1 = (double)this.spawnPosition.getY() + 0.1D - this.posY;
        double d2 = (double)this.spawnPosition.getZ() + 0.5D - this.posZ;
        this.motionX += (Math.signum(d0) * 0.5D - this.motionX) * 0.10000000149011612D;
        this.motionY += (Math.signum(d1) * 0.699999988079071D - this.motionY) * 0.10000000149011612D;
        this.motionZ += (Math.signum(d2) * 0.5D - this.motionZ) * 0.10000000149011612D;
        float f = (float)(MathHelper.atan2(this.motionZ, this.motionX) * (180D / Math.PI)) - 90.0F;
        float f1 = MathHelper.wrapDegrees(f - this.rotationYaw);
        this.moveForward = 0.5F;
        this.rotationYaw += f1;

    }

    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
    protected boolean canTriggerWalking()
    {
        return false;
    }

    public void fall(float distance, float damageMultiplier)
    {
    }

    protected void updateFallState(double y, boolean onGroundIn, @NotNull IBlockState state, @NotNull BlockPos pos)
    {
    }

    /**
     * Return whether this entity should NOT trigger a pressure plate or a tripwire.
     */
    public boolean doesEntityNotTriggerPressurePlate()
    {
        return true;
    }

    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(VARIANT, 0);
    }

    public void setVariant(int metaIn)
    {
        this.dataManager.set(VARIANT, metaIn);
    }

    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        this.setVariant(compound.getInteger("Variant"));

    }

    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setInteger("Variant", this.getVariant());
    }

    public int getVariant()
    {
        return MathHelper.clamp(this.dataManager.get(VARIANT), 0, ButterflyType.values().length-1);
    }

    public float getEyeHeight()
    {
        return this.height / 2.0F;
    }

    public enum ButterflyType implements IStringSerializable
    {
        BASIC1(0,"basic1"),
        BASIC2(1,"basic2"),
        CLOUDSHIMMER(2,"cloud_shimmer"),
        ILLUKINI(3,"illukini"),
        RUBYSILE(4,"ruby_sile");

        private final int meta;
        private final String name;

        ButterflyType(int metaIn, String nameIn)
        {
            this.meta = metaIn;
            name=nameIn;
        }

        public int getMetadata()
        {
            return this.meta;
        }

        @NotNull
        @Override
        public String getName() {
            return name;
        }

    }
}