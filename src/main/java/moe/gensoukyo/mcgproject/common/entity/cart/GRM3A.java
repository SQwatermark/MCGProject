package moe.gensoukyo.mcgproject.common.entity.cart;

import club.nsdn.nyasamarailway.api.cart.CartPart;
import club.nsdn.nyasamarailway.ext.AbsMetro;
import club.nsdn.nyasamarailway.api.cart.CartUtil;

import moe.gensoukyo.mcgproject.common.entity.MCGEntity;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.List;

@MCGEntity("grm_3a")
public class GRM3A extends AbsMetro {

    public static final float DOOR_DIST = 5.0F;

    public final LinkedList<CartPart> parts = new LinkedList<>();

    @Override
    public boolean hasMultiPart() { return true; }

    @Override
    public List<CartPart> getMultiPart() { return parts; }

    public GRM3A(World world) {
        this(world, 0, 0, 0);
    }

    public GRM3A(World world, double x, double y, double z) {
        super(world, x, y, z);
        this.setSize(1.5F, 1.75F);

        parts.add(new CartPart(this, "front", new Vec3d(DOOR_DIST, 0, 0)));
        parts.add(new CartPart(this, "back", new Vec3d(-DOOR_DIST, 0, 0)));
    }

    @Nonnull
    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return super.getRenderBoundingBox().expand(7, 1, 7).expand(-7, 0, -7);
    }

    @Override
    public void initEntity() { }

    @Override
    public void fromNBT(@Nonnull NBTTagCompound nbtTagCompound) { }

    @Override
    public void toNBT(@Nonnull NBTTagCompound nbtTagCompound) { }

    @Override
    public double passengerYOffset() { return 0.35; }

    @Override
    public void passengerUpdate(@Nonnull Entity entity) {
        double x = this.posX;
        double z = this.posZ;
        double y = this.posY + this.getMountedYOffset() + entity.getYOffset();
        if (this.isPassenger(entity)) {
            int index = this.getPassengers().indexOf(entity);
            double vx = 6 - (index + 1) + 0.5;
            Vec3d vec = new Vec3d(vx, 0.0D, 0.0D);
            vec = CartUtil.rotatePitchFix(vec, (float)((double)((this.rotationPitch + 360.0F) / 180.0F) * Math.PI));
            vec = vec.rotateYaw((float)((double)((180.0F - this.rotationYaw) / 180.0F) * Math.PI));
            entity.setPosition(x + vec.x, y + vec.y, z + vec.z);
        }
    }

    @Override
    public boolean shouldSit() { return false; }

    @Override
    public int getMaxPassengerSize() { return 12; }

    @Override
    public void update() {
        super.update();

        for (CartPart p : parts) {
            if (!world.loadedEntityList.contains(p))
                world.spawnEntity(p);
        }

        for (CartPart p : parts)
            p.onUpdate();
    }

}
