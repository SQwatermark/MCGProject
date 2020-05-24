package moe.gensoukyo.mcgproject.cilent.entity.cart;

import club.nsdn.nyasamarailway.entity.cart.NSBT2;
import club.nsdn.nyasamarailway.renderer.entity.AbsCartRenderer;
import club.nsdn.nyasamarailway.renderer.entity.NSBT2Renderer;
import cn.ac.nya.forgeobj.WavefrontObject;
import moe.gensoukyo.mcgproject.common.entity.cart.GRBogie;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderGRBogie extends AbsCartRenderer {

    public static IRenderFactory<GRBogie> FACTORY = RenderGRBogie::new;

    public RenderGRBogie(RenderManager manager) {
        super(manager);
    }

    private final String _name = "grb";
    private final WavefrontObject modelBase = new WavefrontObject(new ResourceLocation(MCGProject.ID, "models/entity/cart/" + _name + "_base.obj"));
    private final WavefrontObject modelWheel = new WavefrontObject(new ResourceLocation(MCGProject.ID, "models/entity/cart/" + _name + "_wheel.obj"));
    private final ResourceLocation textureBase = new ResourceLocation(MCGProject.ID, "textures/entity/cart/" + _name + "_base.png");

    protected ResourceLocation getEntityTexture(EntityMinecart cart) {
        return this.textureBase;
    }

    public void render(EntityMinecart cart, double x, double y, double z, float yaw) {
        GlStateManager.pushMatrix();
        GlStateManager.scale(0.0625D, 0.0625D, 0.0625D);
        Minecraft.getMinecraft().getTextureManager().bindTexture(this.textureBase);
        this.modelBase.renderAllExcept("wheel1", "wheel2");
        GlStateManager.popMatrix();
        float angle = 0.0F;
        if (cart instanceof GRBogie) {
            angle = ((GRBogie)cart).angle;
        }

        GlStateManager.pushMatrix();
        GlStateManager.scale(0.0625D, 0.0625D, 0.0625D);
        GlStateManager.pushMatrix();
        GlStateManager.translate(10.0F, -3.0F, 0.0F);
        GlStateManager.pushMatrix();
        GlStateManager.rotate(angle, 0.0F, 0.0F, 1.0F);
        this.modelWheel.renderAll();
        GlStateManager.popMatrix();
        GlStateManager.popMatrix();
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.scale(0.0625D, 0.0625D, 0.0625D);
        GlStateManager.pushMatrix();
        GlStateManager.translate(-10.0F, -3.0F, 0.0F);
        GlStateManager.pushMatrix();
        GlStateManager.rotate(angle, 0.0F, 0.0F, 1.0F);
        this.modelWheel.renderAll();
        GlStateManager.popMatrix();
        GlStateManager.popMatrix();
        GlStateManager.popMatrix();
    }

}
