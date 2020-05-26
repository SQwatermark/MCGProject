package moe.gensoukyo.mcgproject.common.entity.cart;

import club.nsdn.nyasamarailway.api.cart.CartPart;
import moe.gensoukyo.mcgproject.common.entity.MCGEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

@MCGEntity("grm_3b")
public class GRM3B extends GRM3A {

    public GRM3B(World world) {
        super(world);
    }

    public GRM3B(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    @Override
    public int getMaxPassengerSize() { return 9; }

    @Override
    public double getSeatDist() { return 1.5; }

    @Override
    public double getSeatOffset() { return 0.5; }

    public void applyYawToEntity(Entity entity) {
        float yaw = this.rotationYaw - 90;
        yaw = (yaw + 180) % 360;
        entity.setRenderYawOffset(yaw);
        float f = MathHelper.wrapDegrees(entity.rotationYaw - yaw);
        float f1 = MathHelper.clamp(f, -105.0F, 105.0F);
        entity.prevRotationYaw += f1 - f;
        entity.rotationYaw += f1 - f;
        entity.setRotationYawHead(entity.rotationYaw);
    }

    @Override
    public void update() {
        if (this.getBogieA() != null && this.getBogieB() != null) {
            Entity bogieA = this.getBogieA();
            Entity bogieB = this.getBogieB();
            Vec3d vec = bogieA.getPositionVector().subtract(bogieB.getPositionVector());
            vec = vec.normalize().scale(2.0D);
            double x = (bogieA.posX + bogieB.posX + vec.x) / 2.0D;
            double y = (bogieA.posY + bogieB.posY + vec.y) / 2.0D + this.getTrainYOffset();
            double z = (bogieA.posZ + bogieB.posZ + vec.z) / 2.0D;
            vec = bogieB.getPositionVector().subtract(bogieA.getPositionVector());
            double yaw = Math.atan2(vec.z, vec.x) * 180.0D / Math.PI;
            double hlen = Math.sqrt(vec.x * vec.x + vec.z * vec.z);
            double pitch = Math.atan(vec.y / hlen) * 180.0D / Math.PI;
            this.setRotation((float)yaw, (float)pitch);
            this.motionX = x - this.posX;
            this.motionY = y - this.posY;
            this.motionZ = z - this.posZ;
            this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
        }

        for (CartPart p : parts) {
            if (!world.loadedEntityList.contains(p))
                world.spawnEntity(p);
        }

        for (CartPart p : parts)
            p.onUpdate();
    }

}
