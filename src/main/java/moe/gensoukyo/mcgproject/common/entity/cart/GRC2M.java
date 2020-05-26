package moe.gensoukyo.mcgproject.common.entity.cart;

import club.nsdn.nyasamarailway.api.cart.CartUtil;
import club.nsdn.nyasamarailway.ext.AbsCart;
import club.nsdn.nyasamarailway.ext.AbsLoco;
import club.nsdn.nyasamarailway.ext.AbsMotor;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class GRC2M extends AbsLoco {

    public GRC2M(World world) {
        super(world);
        setSize(1.0F, 1.5F);
    }

    public GRC2M(World world, double x, double y, double z) {
        super(world, x, y, z);
        setSize(1.0F, 1.5F);
    }

    @Override
    public boolean shouldSit() {
        return true;
    }

    @Override
    public double passengerYOffset() {
        return 0.8;
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

}
