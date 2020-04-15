package moe.gensoukyo.mcgproject.cilent.entity.fish;

import moe.gensoukyo.mcgproject.cilent.model.fish.ModelSalmon;
import moe.gensoukyo.mcgproject.common.entity.fish.EntitySalmon;
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
public class RenderSalmon extends RenderLiving<EntitySalmon> {

    public static final Factory FACTORY = new Factory();

    public RenderSalmon(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelSalmon(), 0F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntitySalmon entity) {
        return new ResourceLocation("minecraft", "textures/entity/fish/salmon.png");
    }

    public static class Factory implements IRenderFactory<EntitySalmon> {

        @Override
        public Render<? super EntitySalmon> createRenderFor(RenderManager manager) {
            return new RenderSalmon(manager);
        }

    }

    @Override
    protected void applyRotations(EntitySalmon entityLiving, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, ageInTicks, rotationYaw, partialTicks);
        float i;
        float j;

        if (entityLiving.isInWater()) {
            i = 1f;
            j = 1f;
        } else {
            i = 1.3f;
            j = 1.7f;
        }

        float k = (float) (i * 4.3f * sin(j * 0.6f * ageInTicks));
        GlStateManager.rotate(k, 0.0f, 1.0f, 0.0f);
        GlStateManager.translate(0.0f, 0.0f, -0.4f);
        if (!entityLiving.isInWater()) {
            GlStateManager.translate(0.2f, 0.1f, 0.0f);
            GlStateManager.rotate(90.0f, 0.0f, 0.0f, 1.0f);
        }
    }

}