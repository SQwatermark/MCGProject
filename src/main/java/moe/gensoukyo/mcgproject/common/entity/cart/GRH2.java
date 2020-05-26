package moe.gensoukyo.mcgproject.common.entity.cart;

import club.nsdn.nyasamarailway.api.cart.CartUtil;
import club.nsdn.nyasamarailway.api.cart.IHighSpeedCart;
import club.nsdn.nyasamarailway.ext.AbsCart;
import club.nsdn.nyasamarailway.ext.MultiCartSpawn;
import club.nsdn.nyasamarailway.network.TrainPacket;
import club.nsdn.nyasamarailway.util.TrainController;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class GRH2 extends AbsCart implements IHighSpeedCart {

    @MultiCartSpawn
    public static void spawn(World world, double x, double y, double z, @Nullable EnumFacing facing) {
        GRH2 cart = new GRH2(world, x, y, z);
        world.spawnEntity(cart);
    }

    private static final DataParameter<Boolean> HIGH = EntityDataManager.createKey(GRH2.class, DataSerializers.BOOLEAN);

    public GRH2(World world) {
        super(world);
        setSize(1.75F, 2.0F);
    }

    public GRH2(World world, double x, double y, double z) {
        super(world, x, y, z);
        setSize(1.75F, 2.0F);
    }

    @Override
    public void initEntity() {
        dataManager.register(HIGH, false);
    }

    @Override
    public void fromNBT(@Nonnull NBTTagCompound tag) {
        dataManager.set(HIGH, tag.getBoolean("high"));
    }

    @Override
    public void toNBT(@Nonnull NBTTagCompound tag) {
        tag.setBoolean("high", dataManager.get(HIGH));
    }

    @Override
    public float getMaxCartSpeedOnRail() {
        return 6.0F;
    }

    @Override
    public float getLinkageDistance(EntityMinecart cart) {
        return 5.0F;
    }

    @Override
    public float getOptimalDistance(EntityMinecart cart) {
        return 3.0F;
    }

    @Override
    public void doMotion(TrainPacket packet, EntityMinecart cart) {
        if (getHighSpeedMode())
            TrainController.doMotionWithAirHigh(packet, cart);
        else {
            TrainController.doMotionWithAir(packet, cart);
        }
    }

    @Override
    public boolean shouldSit() {
        return getHighSpeedMode();
    }

    @Override
    public double passengerYOffset() {
        return getHighSpeedMode() ? 0.4 : 0.8;
    }

    @Override
    public void passengerUpdate(@Nonnull Entity passenger) {
        double x = this.posX, z = this.posZ;
        double y = this.posY + this.getMountedYOffset() + passenger.getYOffset();

        BlockPos pos = this.getPosition();
        IBlockState state = world.getBlockState(pos);
        double dx = this.posX - this.prevPosX;
        double dz = this.posZ - this.prevPosZ;
        if (dx * dx + dz * dz < 0.001D) {
            if (state.getBlock() instanceof BlockRailBase) {
                BlockRailBase railBase = (BlockRailBase) state.getBlock();
                BlockRailBase.EnumRailDirection direction = railBase.getRailDirection(world, pos, state, this);
                switch (direction) {
                    case EAST_WEST: this.rotationYaw = 0.0F; break;
                    case NORTH_SOUTH: this.rotationYaw = 90.0F; break;
                }
            }
        }

        if (this.isPassenger(passenger)) {
            double index = this.getPassengers().indexOf(passenger);
            double dist = 0.75;
            double vx, vz;
            vx = dist * Math.cos(index * Math.PI);
            vz = dist * Math.sin(index * Math.PI);
            Vec3d vec = new Vec3d(vx, 0.0D, vz);
            vec = CartUtil.rotatePitchFix(vec, (float) ((this.rotationPitch + 360) / 180 * Math.PI));
            vec = vec.rotateYaw((float) ((180 - this.rotationYaw) / 180 * Math.PI));
            passenger.setPosition(x + vec.x, y + vec.y, z + vec.z);
        }
    }

    @Override
    public void modifyHighSpeedMode(EntityPlayer entityPlayer) { }

    @Override
    public void setHighSpeedMode(boolean b) {
        dataManager.set(HIGH, b);
    }

    @Override
    public boolean getHighSpeedMode() {
        return dataManager.get(HIGH);
    }

    @Override
    public boolean hasSpecialUpdate() {
        return world.isRemote;
    }

    public static final float ANGLE_MAX = 18;
    public float angle = 0;

    @Override
    public void specialUpdate() {
        if (getHighSpeedMode())
            angle += (ANGLE_MAX / 60);
        else angle -= (ANGLE_MAX / 40);
        if (angle > ANGLE_MAX) angle = ANGLE_MAX;
        if (angle < 0) angle = 0;
    }

}
