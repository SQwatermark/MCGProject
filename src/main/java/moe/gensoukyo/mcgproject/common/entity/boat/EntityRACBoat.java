package moe.gensoukyo.mcgproject.common.entity.boat;

import moe.gensoukyo.mcgproject.common.entity.MCGEntity;
import moe.gensoukyo.mcgproject.common.init.ModItem;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

/**
 * @author drzzm32
 * @date 2020/3/27
 */
@MCGEntity("rac_boat")
public class EntityRACBoat extends EntityMCGBoat {

    public EntityRACBoat(World world) {
        super(world);
        this.stepHeight = 1.0F;
    }

    public EntityRACBoat(World world, double x, double y, double z) {
        super(world, x, y, z);
        this.stepHeight = 1.0F;
    }

    @Override
    protected void entityInit() {
        super.entityInit();

        dataManager.set(MAXV, 3.0F); // 216km/h
        dataManager.set(MINV, 1.0F); // 72km/h
        dataManager.set(ACC, 0.2F);
        dataManager.set(BRK, 0.1F);
        dataManager.set(JUMP, 0.8F);
    }

    /**
     * @apiNote 控制乘客数量
     * @param entity 乘客实体
     * */
    @Override
    protected boolean canFitPassenger(Entity entity) {
        return this.getPassengers().size() < 1;
    }

    @Override
    @Nonnull
    public Item getItemBoat() {
        return ModItem.ITEM_RAC_BOAT;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (this.world.isRemote) {
            Vec3d pos = this.getPositionVector();
            Vec3d vec = this.getMotionVector();
            if (vec.length() < 0.05)
                vec = vec.add(0, -this.rand.nextGaussian() * 0.1 - 0.3, 0);

            Vec3d off = new Vec3d(-0.4375, 0.6875, 1);
            Vec3d par = pos.add(off.rotateYaw((float) ((180 - this.rotationYaw) / 180 * Math.PI)));
            Vec3d ext = IBoat.vVec(vec).scale(this.rand.nextGaussian() * 0.05);
            Vec3d pav = vec.scale(-0.5).add(ext);
            this.world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK,
                    par.x, par.y, par.z,
                    pav.x, pav.y, pav.z, 0
            );
            off = new Vec3d(0.4375, 0.6875, 1);
            par = pos.add(off.rotateYaw((float) ((180 - this.rotationYaw) / 180 * Math.PI)));
            ext = IBoat.vVec(vec).scale(this.rand.nextGaussian() * 0.05);
            pav = vec.scale(-0.5).add(ext);
            this.world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK,
                    par.x, par.y, par.z,
                    pav.x, pav.y, pav.z, 0
            );
        }
    }

}
