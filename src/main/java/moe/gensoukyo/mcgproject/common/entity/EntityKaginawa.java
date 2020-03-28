package moe.gensoukyo.mcgproject.common.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.Objects;

@MCGEntity("kaginawa")
public class EntityKaginawa extends EntityThrowable {

    private double vMotionX;
    private double vMotionY;
    private double vMotionZ;
    private int timelimit;

    public EntityKaginawa(World worldIn) {
        super(worldIn);
        this.setDead();
    }

    public EntityKaginawa(World worldIn, EntityLivingBase throwerIn) {
        super(worldIn, throwerIn);
        this.timelimit = 0;
    }

    public void shoot(double x, double y, double z, float velocity, float inaccuracy)
    {
        float f = MathHelper.sqrt(x * x + y * y + z * z);
        x = x / (double)f;
        y = y / (double)f;
        z = z / (double)f;
        x = x + 0.0037499999161809683D * (double)inaccuracy;
        y = y + 0.0037499999161809683D * (double)inaccuracy;
        z = z + 0.0037499999161809683D * (double)inaccuracy;
        x = x * (double)velocity;
        y = y * (double)velocity;
        z = z * (double)velocity;
        this.motionX = x;
        this.motionY = y;
        this.motionZ = z;
        float f1 = MathHelper.sqrt(x * x + z * z);
        this.rotationYaw = (float)(MathHelper.atan2(x, z) * (180D / Math.PI));
        this.rotationPitch = (float)(MathHelper.atan2(y, (double)f1) * (180D / Math.PI));
        this.prevRotationYaw = this.rotationYaw;
        this.prevRotationPitch = this.rotationPitch;
    }

    public void onUpdate() {
        super.onUpdate();
        if (this.timelimit++ > 60) {
            this.setDead();
        }

    }

    protected void onImpact(RayTraceResult result)
    {
        this.vMotionX = (this.posX - Objects.requireNonNull(this.getThrower()).posX) / 7.0D;

        if (this.posY - this.getThrower().posY > 0.0D) {
            this.vMotionY = (this.posY - this.getThrower().posY) / (10.0D - Math.pow(this.posY - this.getThrower().posY - 30.0D, 2.0D) / 95.0D);
        } else {
            this.vMotionY = 0.4D;
        }

        this.vMotionZ = (this.posZ - this.getThrower().posZ) / 7.0D;
        if (this.world.isRemote) {
            EntityLivingBase var10000 = this.getThrower();
            var10000.motionX += this.vMotionX;
            var10000.motionY += this.vMotionY;
            var10000.motionZ += this.vMotionZ;
        }
        this.getThrower().fallDistance = 0.0F;

        this.setDead();

    }

}