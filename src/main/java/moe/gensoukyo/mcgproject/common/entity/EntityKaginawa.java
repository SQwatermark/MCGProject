package moe.gensoukyo.mcgproject.common.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * @author SQwatermark
 * @date 2020/3/27
 * 普通的钩绳，在竹 mod 的钩绳基础上进行了修改
 *   存在时间 50 tick
 *   速度为 1.5F
 *   不精准度为 1.0F
 *   重力 0.03 F
 *   不会对玩家产生向下的速度
 */
@MCGEntity("kaginawa")
public class EntityKaginawa extends EntityThrowable {

    @SuppressWarnings("unused")
    public EntityKaginawa(World worldIn) {
        super(worldIn);
        this.setDead();
    }

    public EntityKaginawa(World worldIn, EntityLivingBase throwerIn) {
        super(worldIn, throwerIn);
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
        this.rotationPitch = (float)(MathHelper.atan2(y, f1) * (180D / Math.PI));
        this.prevRotationYaw = this.rotationYaw;
        this.prevRotationPitch = this.rotationPitch;
    }

    public void onUpdate() {
        super.onUpdate();
        if (this.ticksExisted > 50) {
            this.setDead();
        }

    }

    protected void onImpact(@NotNull RayTraceResult result)
    {
        double vMotionX = (this.posX - Objects.requireNonNull(this.getThrower()).posX) / 7.0D;

        double vMotionY;
        if (this.posY - this.getThrower().posY > 0.0D) {
            vMotionY = (this.posY - this.getThrower().posY) / (10.0D - Math.pow(this.posY - this.getThrower().posY - 30.0D, 2.0D) / 95.0D);
        } else {
            vMotionY = 0.4D;
        }

        double vMotionZ = (this.posZ - this.getThrower().posZ) / 7.0D;
        if (this.world.isRemote) {
            EntityLivingBase var10000 = this.getThrower();
            var10000.motionX += vMotionX;
            var10000.motionY += vMotionY;
            var10000.motionZ += vMotionZ;
        }
        this.getThrower().fallDistance = 0.0F;

        this.setDead();

    }

}