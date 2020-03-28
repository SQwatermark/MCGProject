package moe.gensoukyo.mcgproject.cilent.entity.butterfly;

import moe.gensoukyo.mcgproject.cilent.model.butterfly.ModelCloudShimmer;
import moe.gensoukyo.mcgproject.common.entity.butterfly.EntityCloudShimmer;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

//代码取自 kathairis mod 并进行了修改，仅用于测试
@SideOnly(Side.CLIENT)
public class RenderCloudShimmer extends RenderLiving<EntityCloudShimmer>
{
    public static final Factory FACTORY = new Factory();

    public RenderCloudShimmer(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelCloudShimmer(), 0F);
    }

    
    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityCloudShimmer entity)
    {
        return new ResourceLocation(MCGProject.ID, "textures/entity/butterfly/cloudshimmer.png");
    }
    
    public static class Factory implements IRenderFactory<EntityCloudShimmer> {

        @Override
        public Render<? super EntityCloudShimmer> createRenderFor(RenderManager manager) {
            return new RenderCloudShimmer(manager);
        }

    }
    
    
    protected void applyRotations(EntityCloudShimmer entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
    {
    	GlStateManager.scale(0.3, 0.3, 0.3);
        super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
        
    }
    
}
