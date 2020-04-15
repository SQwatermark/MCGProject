package moe.gensoukyo.mcgproject.cilent.entity.fish;

import moe.gensoukyo.mcgproject.cilent.model.fish.ModelCod;
import moe.gensoukyo.mcgproject.common.entity.fish.EntityCod;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static java.lang.Math.sin;

@SideOnly(Side.CLIENT)
public class RenderCod extends RenderLiving<EntityCod>
{
    public static final Factory FACTORY = new Factory();

    public RenderCod(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelCod(), 0F);
    }


    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityCod entity)
    {
        return new ResourceLocation("minecraft", "textures/entity/fish/cod.png");
    }

    public static class Factory implements IRenderFactory<EntityCod> {

        @Override
        public Render<? super EntityCod> createRenderFor(RenderManager manager) {
            return new RenderCod(manager);
        }

    }

    @Override
    protected void applyRotations(EntityCod entityLiving, float ageInTicks, float rotationYaw, float partialTicks)
    {
        super.applyRotations(entityLiving, ageInTicks, rotationYaw, partialTicks);
        float f = (float) (4.3F * sin(0.6F * ageInTicks));
        GlStateManager.rotate(f, 0.0F, 1.0F, 0.0F);
        if (!entityLiving.isInWater()) {
            GlStateManager.translate(0.1F, 0.1F, -0.1F);
            GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
        }
    }

}
