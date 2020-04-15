package moe.gensoukyo.mcgproject.cilent.entity.fish;

import moe.gensoukyo.mcgproject.cilent.model.fish.ModelTropicalFishB;
import moe.gensoukyo.mcgproject.common.entity.fish.EntityTropicalFishB;
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
public class RenderTropicalFishB extends RenderLiving<EntityTropicalFishB> {

    public static final Factory FACTORY = new Factory();

    public RenderTropicalFishB(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelTropicalFishB(), 0F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityTropicalFishB entity) {
        return new ResourceLocation("minecraft", "textures/entity/fish/tropical_b.png");
    }

    public static class Factory implements IRenderFactory<EntityTropicalFishB> {

        @Override
        public Render<? super EntityTropicalFishB> createRenderFor(RenderManager manager) {
            return new RenderTropicalFishB(manager);
        }

    }

    @Override
    protected void applyRotations(EntityTropicalFishB entityLiving, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, ageInTicks, rotationYaw, partialTicks);
        float f = (float) (4.3F * sin(0.6F * ageInTicks));
        GlStateManager.rotate(f, 0.0F, 1.0F, 0.0F);
        if (!entityLiving.isInWater()) {
            GlStateManager.translate(0.2F, 0.1F, 0.0F);
            GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
        }
    }

}
