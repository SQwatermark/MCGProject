package moe.gensoukyo.mcgproject.cilent.entity.cart;

import club.nsdn.nyasamarailway.renderer.entity.AbsCartRenderer;
import cn.ac.nya.forgeobj.WavefrontObject;
import moe.gensoukyo.mcgproject.common.entity.cart.GRH2M;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderGRH2M extends AbsCartRenderer {

    public static IRenderFactory<EntityMinecart> FACTORY = RenderGRH2M::new;
    private final String _name = "grh_2m";
    private final WavefrontObject modelBase = new WavefrontObject(new ResourceLocation(MCGProject.ID, "models/entity/cart/" + _name + "_base.obj"));
    private final WavefrontObject modelPrint = new WavefrontObject(new ResourceLocation(MCGProject.ID, "models/entity/cart/" + _name + "_print.obj"));
    private final ResourceLocation textureBase = new ResourceLocation(MCGProject.ID, "textures/entity/cart/" + _name + "_base.png");
    private final ResourceLocation texturePrint = new ResourceLocation(MCGProject.ID, "textures/entity/cart/" + _name + "_print.png");

    public RenderGRH2M(RenderManager manager) {
        super(manager);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityMinecart cart) {
        return textureBase;
    }

    @Override
    public void render(EntityMinecart cart, double x, double y, double z, float yaw) {
        GlStateManager.pushMatrix();
        GlStateManager.scale(0.0625D, 0.0625D, 0.0625D);
        Minecraft.getMinecraft().getTextureManager().bindTexture(textureBase);
        modelBase.renderOnly("base", "stand");

        if (cart instanceof GRH2M) {
            float angle = ((GRH2M) cart).angle;
            GlStateManager.pushMatrix();
            GlStateManager.rotate(angle, 0, 0, 1);
            modelBase.renderOnly("front");
            GlStateManager.popMatrix();

            GlStateManager.pushMatrix();
            GlStateManager.rotate(angle, 0, 0, -1);
            modelBase.renderOnly("back");
            GlStateManager.popMatrix();

            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            modelBase.renderOnly("mask");

            Minecraft.getMinecraft().getTextureManager().bindTexture(texturePrint);
            modelPrint.renderAll();
            GlStateManager.disableAlpha();
            GlStateManager.disableBlend();
        }

        GlStateManager.popMatrix();
    }

}
