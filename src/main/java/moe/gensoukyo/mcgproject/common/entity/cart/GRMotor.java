package moe.gensoukyo.mcgproject.common.entity.cart;

import club.nsdn.nyasamarailway.api.cart.*;
import club.nsdn.nyasamarailway.ext.AbsBogie;
import club.nsdn.nyasamarailway.ext.AbsMotor;
import club.nsdn.nyasamarailway.network.TrainPacket;
import club.nsdn.nyasamarailway.util.TrainController;

import moe.gensoukyo.mcgproject.common.entity.MCGEntity;
import net.minecraft.block.BlockRailBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;

@MCGEntity("gr_motor")
public class GRMotor extends AbsMotor {

    private static final DataParameter<Integer> TARGET_BOGIE = EntityDataManager.createKey(GRMotor.class, DataSerializers.VARINT);
    public UUID targetUUID = UUID.randomUUID();

    private static final DataParameter<Boolean> IS_CENTER = EntityDataManager.createKey(GRMotor.class, DataSerializers.BOOLEAN);

    public float bogieDist = 1.0F;
    public float couplerDist = 1.0F;

    public GRMotor(World world) {
        super(world);
    }

    public GRMotor(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    public GRMotor(World world, double x, double y, double z, float bogieDist, float couplerDist) {
        super(world, x, y, z);
        this.bogieDist = bogieDist;
        this.couplerDist = couplerDist;
    }

    public void setIsCenter(boolean val) { dataManager.set(IS_CENTER, val); }
    public boolean isCenter() { return dataManager.get(IS_CENTER); }

    @Override
    public double getRadius() { return 0.2734375; }

    @Override
    public float getMaxCartSpeedOnRail() {
        return (float) (40 / 3.6 / 20);
    }

    @Override
    public float getLinkageDistance(EntityMinecart cart) {
        return 8.0F;
    }

    @Override
    public float getOptimalDistance(EntityMinecart cart) {
        return (cart == getTargetBogie() || isCenter()) ? bogieDist : couplerDist;
    }

    @Override
    public boolean canBeAdjusted(EntityMinecart cart) {
        return false;
    }

    @Override
    public void initEntity() {
        dataManager.register(TARGET_BOGIE, -1);
        dataManager.register(IS_CENTER, false);
    }

    @Override
    public void fromNBT(@Nonnull NBTTagCompound tag) {
        dataManager.set(TARGET_BOGIE, tag.getInteger("targetBogie"));
        targetUUID = tag.getUniqueId("targetUUID");
        dataManager.set(IS_CENTER, tag.getBoolean("isCenter"));

        bogieDist = tag.getFloat("bogieDist");
        couplerDist = tag.getFloat("couplerDist");
    }

    @Override
    public void toNBT(@Nonnull NBTTagCompound tag) {
        tag.setInteger("targetBogie", dataManager.get(TARGET_BOGIE));
        tag.setUniqueId("targetUUID", targetUUID);
        tag.setBoolean("isCenter", dataManager.get(IS_CENTER));

        tag.setFloat("bogieDist", bogieDist);
        tag.setFloat("couplerDist", couplerDist);
    }

    public void setTargetBogie(Entity bogie) {
        if (isCenter()) return;
        if (bogie != null) {
            if (!world.isRemote) {
                targetUUID = bogie.getUniqueID();
            }

            dataManager.set(TARGET_BOGIE, bogie.getEntityId());
        }
    }

    public Entity getTargetBogie() {
        Entity entity = world.getEntityByID(dataManager.get(TARGET_BOGIE));
        if (!world.isRemote && world instanceof WorldServer) {
            Entity serverEntity = ((WorldServer)world).getEntityFromUuid(targetUUID);
            if (serverEntity != null) {
                if (!serverEntity.equals(entity)) {
                    this.dataManager.set(TARGET_BOGIE, serverEntity.getEntityId());
                }

                return serverEntity;
            } else {
                targetUUID = UUID.randomUUID();
                this.dataManager.set(TARGET_BOGIE, -1);
                return null;
            }
        } else {
            return entity;
        }
    }

    public static boolean hasCart(World world, EntityMinecart me, BlockPos pos) {
        float bBoxSize = 0.125F;
        List<EntityMinecart> bBox = world.getEntitiesWithinAABB(
                EntityMinecart.class,
                new AxisAlignedBB(pos).shrink(bBoxSize)
        );
        if (!bBox.isEmpty()) {
            for (EntityMinecart i : bBox) {
                if (i instanceof AbsBogie)
                    continue;   // Non-Motor Bogie

                if (i instanceof AbsMotoCart) {
                    AbsMotoCart cart = (AbsMotoCart) i;
                    if (cart.equals(me))
                        return false;
                    return !cart.getPassengers().isEmpty();
                }
                return !i.equals(me);
            }
        }
        return false;
    }

    public static double getNearestCartDist(World world, EntityMinecart cart, int maxDist) {
        BlockPos pos = cart.getPosition();
        if (!BlockRailBase.isRailBlock(world, pos))
            return Double.NaN;

        EnumFacing facing = EnumFacing.DOWN;
        if (cart instanceof AbsCartBase) {
            facing = ((AbsCartBase) cart).facing;
        } else {
            if (cart.motionX > 0)
                facing = EnumFacing.EAST;
            else if (cart.motionX < 0)
                facing = EnumFacing.WEST;
            else if (cart.motionZ > 0)
                facing = EnumFacing.SOUTH;
            else if (cart.motionZ < 0)
                facing = EnumFacing.NORTH;
            else if (cart.motionX  == 0 && cart.motionZ == 0)
                facing = cart.getHorizontalFacing();
        }

        Tuple<BlockPos, EnumFacing> now = new Tuple<>(pos, facing);
        if (IMobileBlocking.findNextRail(world, cart, now) == null)
            return Double.NaN;

        for (int i = 0; i < maxDist; i++) {
            now = IMobileBlocking.findNextRail(world, cart, now);
            if (now == null)
                return i;
            if (hasCart(world, cart, now.getFirst()))
                return i;
        }

        return Double.NaN;
    }

    public void setBlocking(float val) {
        try {
            Field field = AbsCartBase.class.getDeclaredField("BLOCKING");
            field.setAccessible(true);
            DataParameter<Float> BLOCKING = (DataParameter<Float>) field.get(null);
            if (BLOCKING != null)
                this.dataManager.set(BLOCKING, val);
        } catch (Exception ignored) { }
    }

    @Override
    public void scanBlocking() {
        double dist = getNearestCartDist(this.world, this, 192);
        if (Double.isNaN(dist) || dist > 192)
            dist = 999;

        setBlocking((float) dist);
    }

    public static boolean preMobileBlocking(World world, EntityMinecart cart) {
        double dist = getNearestCartDist(world, cart, 160);
        if (Double.isNaN(dist)) return false;
        return dist < 160;
    }

    public static boolean mobileBlocking(World world, EntityMinecart cart) {
        double dist = getNearestCartDist(world, cart, 128);
        if (Double.isNaN(dist)) return false;
        return dist < 128;
    }

    private int tmpEngineBrake = -1;

    @Override
    protected void doEngine() {
        if (getBlockingState()) {
            if (preMobileBlocking(world, this)) {
                TrainPacket packet = new TrainPacket(0, 1, getEngineDir());
                packet.Velocity = this.Velocity;
                if (mobileBlocking(world, this)) {
                    TrainController.doMotion(packet, this); // stop!
                } else {
                    double vel = getSpeed();
                    if (vel < 0.1) {
                        packet.P = 1; packet.R = 10;
                        doMotion(packet, this); // slow move
                    } else if (vel < 0.2) {
                        doMotion(packet, this); // control speed
                    } else {
                        TrainController.doMotion(packet, this); // EB!
                    }
                }
                setEnginePrevVel(this.Velocity);
                setEngineVel(packet.Velocity);

                return;
            }
        }

        tmpPacket = new TrainPacket(getEnginePower(), getEngineBrake(), getEngineDir());
        tmpPacket.Velocity = this.Velocity;
        if (this.maxVelocity > 0) {
            if (this.Velocity > this.maxVelocity && tmpEngineBrake == -1) {
                tmpEngineBrake = getEngineBrake();
                setEngineBrake(1);
            } else if (this.Velocity > this.maxVelocity && tmpEngineBrake != -1) {
                setEngineBrake(1);
            } else if (this.Velocity <= this.maxVelocity && tmpEngineBrake != -1) {
                setEngineBrake(tmpEngineBrake);
                tmpEngineBrake = -1;
            }
        }
        doMotion(tmpPacket, this);
        setEnginePrevVel(this.Velocity);
        setEngineVel(tmpPacket.Velocity);
    }

    @Override
    public void update() {
        super.update();

        if (!world.isRemote)
            scanBlocking();
    }

}
