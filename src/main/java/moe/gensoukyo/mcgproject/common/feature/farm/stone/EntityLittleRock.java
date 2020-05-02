package moe.gensoukyo.mcgproject.common.feature.farm.stone;

import moe.gensoukyo.mcgproject.common.entity.MCGEntity;
import moe.gensoukyo.mcgproject.common.init.ModItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@MCGEntity("little_rock")
public class EntityLittleRock extends EntityThrowable {

    public EntityLittleRock(World worldIn) {
        super(worldIn);
    }

    public EntityLittleRock(World worldIn, EntityLivingBase throwerIn)
    {
        super(worldIn, throwerIn);
    }

    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 3) {
            for (int i = 0; i < 8; ++i) {
                this.world.spawnParticle(EnumParticleTypes.ITEM_CRACK, this.posX, this.posY, this.posZ, ((double)this.rand.nextFloat() - 0.5D) * 0.08D, ((double)this.rand.nextFloat() - 0.5D) * 0.08D, ((double)this.rand.nextFloat() - 0.5D) * 0.08D, Item.getIdFromItem(ModItem.ITEM_LITTLE_STONE));
            }
        }
    }

    @Override
    public void onUpdate() {
        if (!this.world.isRemote && this.ticksExisted > 200) this.setDead();
        super.onUpdate();
    }

    /**
     * Called when this EntityThrowable hits a block or entity.
     */
    protected void onImpact(RayTraceResult result)
    {
        if (result.entityHit != null) {
            result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 10.0F);
        }
        if (!this.world.isRemote) this.setDead();
    }
}
