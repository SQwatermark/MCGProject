package moe.gensoukyo.mcgproject.common.entity.boat;

import moe.gensoukyo.mcgproject.common.entity.MCGEntity;
import moe.gensoukyo.mcgproject.common.init.ModItem;
import moe.gensoukyo.mcgproject.common.network.BoatPacket;
import moe.gensoukyo.mcgproject.common.network.NetworkWrapper;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.MovementInput;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * @author drzzm32
 * @date 2020/3/10
 */
@MCGEntity("mcg_boat")
public class EntityMCGBoat extends EntityBoat implements IBoat {

    private static Field status; //field_184469_aF
    private static Field lastYd; // field_184473_aH

    static {
        try {
            status = EntityBoat.class.getDeclaredField("field_184469_aF");
            status.setAccessible(true);
            lastYd = EntityBoat.class.getDeclaredField("field_184473_aH");
            lastYd.setAccessible(true);
        } catch (Exception ignored) {
            status = null;
            lastYd = null;
        }
    }

    public Status getStatus() {
        try {
            return (Status) status.get(this);
        } catch (Exception ignored) {
            return Status.IN_AIR;
        }
    }

    public void setLastYd(double v) {
        try {
            lastYd.set(this, v);
        } catch (Exception ignored) { }
    }

    public static final DataParameter<Float> THRESHOLD = EntityDataManager.createKey(EntityMCGBoat.class, DataSerializers.FLOAT);
    public static final DataParameter<Float> DAMAGE = EntityDataManager.createKey(EntityMCGBoat.class, DataSerializers.FLOAT);
    public static final DataParameter<Float> MASS = EntityDataManager.createKey(EntityMCGBoat.class, DataSerializers.FLOAT);
    public static final DataParameter<Float> JUMP = EntityDataManager.createKey(EntityMCGBoat.class, DataSerializers.FLOAT);
    public static final DataParameter<Float> MAXV = EntityDataManager.createKey(EntityMCGBoat.class, DataSerializers.FLOAT);
    public static final DataParameter<Float> MINV = EntityDataManager.createKey(EntityMCGBoat.class, DataSerializers.FLOAT);
    public static final DataParameter<Float> ACC = EntityDataManager.createKey(EntityMCGBoat.class, DataSerializers.FLOAT);
    public static final DataParameter<Float> BRK = EntityDataManager.createKey(EntityMCGBoat.class, DataSerializers.FLOAT);

    public static final DataParameter<Integer> COLOR = EntityDataManager.createKey(EntityMCGBoat.class, DataSerializers.VARINT);

    /**
     * @apiNote 客户端-服务端同步用，用于撞击判定，不需要存入NBT
     * */
    public double prevVel = 0, vel = 0;

    public void setThreshold(float threshold) { dataManager.set(THRESHOLD, threshold); }
    public void setMaxDamage(float damage) { dataManager.set(DAMAGE, damage); }
    public void setBoatMass(float mass) { dataManager.set(MASS, mass); }
    public void setBoatJump(float jump) { dataManager.set(JUMP, jump); }
    public void setBoatMaxV(float vel) { dataManager.set(MAXV, vel); }
    public void setBoatMinV(float vel) { dataManager.set(MINV, vel); }
    public void setBoatAcc(float vel) { dataManager.set(ACC, vel); }
    public void setBoatBrk(float vel) { dataManager.set(BRK, vel); }

    public float getThreshold() {
        float val = dataManager.get(THRESHOLD);
        val = MathHelper.clamp(val, 0, 8.0F);
        dataManager.set(THRESHOLD, val);
        return val;
    }
    public float getMaxDamage() {
        float val = dataManager.get(DAMAGE);
        val = MathHelper.clamp(val, 0, 9961.0F);
        dataManager.set(DAMAGE, val);
        return val;
    }
    public float getBoatMass() {
        float val = dataManager.get(MASS);
        val = MathHelper.clamp(val, 1.0F, 100.0F);
        dataManager.set(MASS, val);
        return val;
    }
    public float getBoatJump() {
        float val = dataManager.get(JUMP);
        val = MathHelper.clamp(val, 0, 8.0F);
        dataManager.set(JUMP, val);
        return val;
    }
    public float getBoatMaxV() {
        float val = dataManager.get(MAXV);
        val = MathHelper.clamp(val, 0, 8.0F);
        dataManager.set(MAXV, val);
        return val;
    }
    public float getBoatMinV() {
        float val = dataManager.get(MINV);
        val = MathHelper.clamp(val, 0, 8.0F);
        dataManager.set(MINV, val);
        return val;
    }
    public float getBoatAcc() {
        float val = dataManager.get(ACC);
        val = MathHelper.clamp(val, 0, 1.0F);
        dataManager.set(ACC, val);
        return val;
    }
    public float getBoatBrk() {
        float val = dataManager.get(BRK);
        val = MathHelper.clamp(val, 0, 1.0F);
        dataManager.set(BRK, val);
        return val;
    }

    public void setBoatColor(int color) { dataManager.set(COLOR, color); }
    public int getBoatColor() { return dataManager.get(COLOR); }

    public EntityMCGBoat(World world) {
        super(world);
        this.stepHeight = 0.5F;
    }

    public EntityMCGBoat(World world, double x, double y, double z) {
        super(world, x, y, z);
        this.stepHeight = 0.5F;
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        dataManager.register(THRESHOLD, 0.1F);
        dataManager.register(DAMAGE, 10.0F);
        dataManager.register(MASS, 10.0F);
        dataManager.register(JUMP, 0.4F);
        dataManager.register(MAXV, 2.5F);
        dataManager.register(MINV, 1.667F);
        dataManager.register(ACC, 0.05F);
        dataManager.register(BRK, 0.05F);
        
        dataManager.register(COLOR, 0xFFFFFF);
    }

    /**
     * @apiNote 控制乘客数量
     * @param entity 乘客实体
     * */
    @Override
    protected boolean canFitPassenger(Entity entity) {
        return this.getPassengers().size() < 2;
    }

    @Override
    protected void readEntityFromNBT(@Nonnull NBTTagCompound tagCompound) {
        if (tagCompound.hasKey("jump"))
            dataManager.set(JUMP, tagCompound.getFloat("jump"));
        if (tagCompound.hasKey("mass"))
            dataManager.set(MASS, tagCompound.getFloat("mass"));
        if (tagCompound.hasKey("damage"))
            dataManager.set(DAMAGE, tagCompound.getFloat("damage"));
        if (tagCompound.hasKey("threshold"))
            dataManager.set(THRESHOLD, tagCompound.getFloat("threshold"));
        if (tagCompound.hasKey("maxV"))
            dataManager.set(MAXV, tagCompound.getFloat("maxV"));
        if (tagCompound.hasKey("minV"))
            dataManager.set(MINV, tagCompound.getFloat("minV"));
        if (tagCompound.hasKey("acc"))
            dataManager.set(ACC, tagCompound.getFloat("acc"));
        if (tagCompound.hasKey("brk"))
            dataManager.set(BRK, tagCompound.getFloat("brk"));

        if (tagCompound.hasKey("color"))
            dataManager.set(COLOR, tagCompound.getInteger("color"));
    }

    @Override
    protected void writeEntityToNBT(@Nonnull NBTTagCompound tagCompound) {
        tagCompound.setFloat("jump", getBoatJump());
        tagCompound.setFloat("mass", getBoatMass());
        tagCompound.setFloat("damage", getMaxDamage());
        tagCompound.setFloat("threshold", getThreshold());
        tagCompound.setFloat("maxV", getBoatMaxV());
        tagCompound.setFloat("minV", getBoatMinV());
        tagCompound.setFloat("acc", getBoatAcc());
        tagCompound.setFloat("brk", getBoatBrk());

        tagCompound.setInteger("color", getBoatColor());
    }

    @Override
    @Nonnull
    public Item getItemBoat() {
        return ModItem.ITEM_MCG_BOAT;
    }

    /**
     * @apiNote 用于检测船下面是否为正常路面方块
     * */
    public boolean isOnSoftSurface() {
        List<Material> hardFace = Arrays.asList(
                Material.GROUND, Material.WOOD, Material.ROCK, Material.IRON, Material.GLASS,
                Material.SAND, Material.SNOW, Material.CRAFTED_SNOW, Material.CLAY
        );
        Material material = this.world.getBlockState(this.getPosition().down()).getMaterial();
        return !hardFace.contains(material);
    }
    
    /**
     * @apiNote 船的接触面光滑度，用于控制在地面上移动的最高速度
     * @apiNote 当值为 0.984 时，最高速度为 2.5m/t (180km/h)
     * @apiNote 当值为 0.976 时，最高速度为 1.6m/t (120km/h)
     * @apiNote v(t+1) = v(t) * glide;
     * @apiNote v(t+1) = v(t) + 0.04;
     * */
    @Override
    public float getBoatGlide() {
        return 1 - (getBoatAcc() / getMaxSpeed());
    }

    public float getMaxSpeed() {
        return isOnSoftSurface() ? getBoatMaxV() : getBoatMinV();
    }

    /**
     * @apiNote 把客户端的船速率同步至服务端
     * @apiNote 同时实现船起跳及加速的功能
     * */
    @SideOnly(Side.CLIENT)
    public void controlBoatExtra() {
        if (this.getControllingPassenger() instanceof EntityPlayerSP) {
            BoatPacket packet = new BoatPacket(this);
            NetworkWrapper.INSTANCE.sendToServer(packet);

            EntityPlayerSP player = (EntityPlayerSP) this.getControllingPassenger();
            MovementInput input = player.movementInput;
            input.updatePlayerMoveState();
            player.updateEntityActionState();
            player.moveVertical = input.jump ? 1.0F : 0.0F;
            if ((this.onGround || this.getStatus() == Status.IN_WATER) && Math.abs(this.motionY) <= 0.5 * this.getBoatJump())
                this.motionY += (player.moveVertical * this.getBoatJump());

            float acc = 0;
            if (input.forwardKeyDown)
                acc += (getBoatAcc() - 0.04F);
            if (input.backKeyDown)
                acc -= (getBoatBrk() - 0.005F);
            this.motionX += (MathHelper.sin(-this.rotationYaw * 0.017453292F) * acc);
            this.motionZ += (MathHelper.cos(this.rotationYaw * 0.017453292F) * acc);

            this.motionX = MathHelper.clamp(this.motionX, -8, 8);
            this.motionY = MathHelper.clamp(this.motionY, -8, 8);
            this.motionZ = MathHelper.clamp(this.motionZ, -8, 8);
        }
    }

    @Override
    public void onUpdate() {
        // 使船能够空格起跳，及额外加速
        // TODO: 你能信，船的核心代码是客户端执行
        if (this.world.isRemote)
            controlBoatExtra();

        super.onUpdate();

        this.prevVel = vel;
        this.vel = Math.sqrt(
                this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);

        // 禁止非人类生物上船
        if (!this.world.isRemote) {
            for (Entity entity : this.getPassengers())
                if (!(entity instanceof EntityPlayer))
                    entity.dismountRidingEntity();
        }
    }

    public Vec3d getMotionVector() {
        return new Vec3d(this.motionX, this.motionY, this.motionZ);
    }

    public void setVelocity(Vec3d vec) {
        this.motionX = vec.x;
        this.motionY = vec.y;
        this.motionZ = vec.z;
        this.isAirBorne = true;
    }

    /**
     * @apiNote 船与实体碰撞
     * @param entity 目标实体
     * */
    @Override
    public void applyEntityCollision(Entity entity) {
        super.applyEntityCollision(entity);

        // 撞击船然后按动量守恒
        if (entity instanceof EntityMCGBoat && !this.isPassenger(entity)) {
            EntityMCGBoat boat = (EntityMCGBoat) entity;
            Vec3d v1 = this.getMotionVector(), v2 = boat.getMotionVector();
            float m1 = this.getBoatMass(), m2 = boat.getBoatMass();
            Vec3d va = v1.scale(m1 - m2).add(v2.scale(2 * m2)).scale(1 / (m1 + m2));
            Vec3d vb = v2.scale(m2 - m1).add(v1.scale(2 * m1)).scale(1 / (m1 + m2));
            if (boat.getPassengers().isEmpty()) {
                boat.addVelocity(vb.x, vb.y, vb.z);
            } else {
                this.setVelocity(va);
                boat.setVelocity(vb);
            }
        }

        // 撞击生物并造成伤害
        if (entity instanceof EntityLivingBase && !this.isPassenger(entity) && this.vel > this.getThreshold()) {
            EntityLivingBase living = (EntityLivingBase) entity;

            if (living instanceof EntityPlayer && ((EntityPlayer) living).isCreative()) {
                Vec3d tar = living.getPositionVector();
                Vec3d src = this.getPositionVector();
                Vec3d vec = tar.subtract(src).normalize().scale(this.vel * this.getBoatMass());
                living.addVelocity(vec.x, vec.y, vec.z);
                return;
            }

            living.setHealth(living.getHealth() - (float) (this.getMaxDamage() * this.vel / this.getMaxSpeed()));
            PotionType type;
            type = PotionType.getPotionTypeForName("weakness");
            if (type != null)
                for (PotionEffect effect : type.getEffects())
                    effect.getPotion().affectEntity(null, null, living, effect.getAmplifier(), 1.0F);
            type = PotionType.getPotionTypeForName("harming");
            if (type != null)
                for (PotionEffect effect : type.getEffects())
                    effect.getPotion().affectEntity(null, null, living, effect.getAmplifier(), 1.0F);
            Vec3d tar = living.getPositionVector();
            Vec3d src = this.getPositionVector();
            Vec3d vec = tar.subtract(src).normalize().scale(this.vel * this.getBoatMass());
            living.addVelocity(vec.x, vec.y, vec.z);
        }
    }

    /**
     * @apiNote 删除让船撞坏的代码
     * */
    @Override
    protected void updateFallState(double dist, boolean onGround, IBlockState state, BlockPos pos) {
        this.setLastYd(this.motionY);
        if (!this.isRiding()) {
            if (onGround) {
                if (this.fallDistance > 3.0F) {
                    if (getStatus() != EntityBoat.Status.ON_LAND) {
                        this.fallDistance = 0.0F;
                        return;
                    }
                    this.fall(this.fallDistance, 1.0F);
                }

                this.fallDistance = 0.0F;
            } else if (this.world.getBlockState((new BlockPos(this)).down()).getMaterial() != Material.WATER && dist < 0.0D) {
                this.fallDistance = (float)((double)this.fallDistance - dist);
            }
        }
    }

    /**
     * @apiNote 让船耐打一点
     * @param source 攻击源
     * @param value 伤害值
     * */
    @Override
    public boolean attackEntityFrom(@Nonnull DamageSource source, float value) {
        if (this.isEntityInvulnerable(source)) {
            return false;
        } else if (!this.world.isRemote && !this.isDead) {
            if (source instanceof EntityDamageSourceIndirect && source.getTrueSource() != null && this.isPassenger(source.getTrueSource())) {
                return false;
            } else {
                this.setForwardDirection(-this.getForwardDirection());
                this.setTimeSinceHit(10);
                this.setDamageTaken(this.getDamageTaken() + value);
                this.markVelocityChanged();
                boolean flag = source.getTrueSource() instanceof EntityPlayer && ((EntityPlayer)source.getTrueSource()).capabilities.isCreativeMode;
                if (flag || this.getDamageTaken() > 40.0F) {
                    if (!flag && this.world.getGameRules().getBoolean("doEntityDrops")) {
                        this.dropItemWithOffset(this.getItemBoat(), 1, 0.0F);
                    }

                    this.setDead();
                }

                return true;
            }
        } else {
            return true;
        }
    }

    /**
     * @apiNote 防止玩家抢别人的船
     * @param player 玩家实体
     * @param hand 玩家的手
     * */
    @Override
    public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
        if (player.isRiding()) {
            return false;
        } else {
            return super.processInitialInteract(player, hand);
        }
    }

}
