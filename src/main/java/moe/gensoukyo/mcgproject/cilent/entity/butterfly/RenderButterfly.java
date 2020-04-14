package moe.gensoukyo.mcgproject.cilent.entity.butterfly;

import moe.gensoukyo.mcgproject.cilent.model.butterfly.ModelButterfly;
import moe.gensoukyo.mcgproject.common.entity.butterfly.EntityButterfly;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

//代码取自 kathairis mod 并进行了修改
@SideOnly(Side.CLIENT)
public class RenderButterfly extends RenderLiving<EntityButterfly>
{
    public static final Factory FACTORY = new Factory();

    public RenderButterfly(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelButterfly(), 0F);
    }

    
    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityButterfly entity)
    {
        return new ResourceLocation(MCGProject.ID, "textures/entity/butterfly/butterfly.png");
    }
    
    public static class Factory implements IRenderFactory<EntityButterfly> {

        @Override
        public Render<? super EntityButterfly> createRenderFor(RenderManager manager) {
            return new RenderButterfly(manager);
        }

    }
    
    protected void applyRotations(EntityButterfly entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
    {
    	GlStateManager.scale(0.2, 0.2, 0.2);
        super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
        
    }
    
}
