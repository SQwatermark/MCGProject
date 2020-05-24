package moe.gensoukyo.mcgproject.common.entity.cart;

import moe.gensoukyo.mcgproject.common.entity.MCGEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
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

    public void update() {
        if (this.getBogieA() != null && this.getBogieB() != null) {
            Entity bogieA = this.getBogieA();
            Entity bogieB = this.getBogieB();
            Vec3d vec = bogieA.getPositionVector().subtract(bogieB.getPositionVector());
            vec = vec.normalize().scale(1.0D);
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

    }

}
