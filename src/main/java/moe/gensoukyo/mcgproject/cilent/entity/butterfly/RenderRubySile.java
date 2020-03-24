package moe.gensoukyo.mcgproject.cilent.entity.butterfly;

import moe.gensoukyo.mcgproject.cilent.model.butterfly.ModelRubySile;
import moe.gensoukyo.mcgproject.common.entity.butterfly.EntityRubySile;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderRubySile extends RenderLiving<EntityRubySile>
{
    public static final Factory FACTORY = new Factory();

    public RenderRubySile(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelRubySile(), 0F);
    }

    
    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityRubySile entity)
    {
            return new ResourceLocation(MCGProject.ID, "textures/entity/butterfly/rubysile.png");
    }
    
    public static class Factory implements IRenderFactory<EntityRubySile> {

        @Override
        public Render<? super EntityRubySile> createRenderFor(RenderManager manager) {
            return new RenderRubySile(manager);
        }

    }
    
    protected void applyRotations(EntityRubySile entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
    {
    	GlStateManager.scale(0.2, 0.2, 0.2);
        super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
        
    }
    
}
