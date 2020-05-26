package moe.gensoukyo.mcgproject.cilent.entity.cart;

import club.nsdn.nyasamarailway.api.cart.AbsContainer;
import club.nsdn.nyasamarailway.renderer.entity.AbsCartRenderer;
import club.nsdn.nyasamarailway.renderer.entity.AbsContainerRenderer;
import club.nsdn.nyasamatelecom.api.render.RendererHelper;
import cn.ac.nya.forgeobj.WavefrontObject;
import moe.gensoukyo.mcgproject.common.entity.cart.GRW4;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import org.lwjgl.opengl.GL11;

public class RenderGRW4 extends AbsCartRenderer {

    public static IRenderFactory<EntityMinecart> FACTORY = RenderGRW4::new;
    public static IRenderFactory<AbsContainer> BASKET = RenderGRW4.Basket::new;
    private static final String _name = "grw_4";
    private static final WavefrontObject modelBase = new WavefrontObject(new ResourceLocation(MCGProject.ID, "models/entity/cart/" + _name + "_base.obj"));
    private static final WavefrontObject modelHead = new WavefrontObject(new ResourceLocation(MCGProject.ID, "models/entity/cart/" + _name + "_head.obj"));
    private static final ResourceLocation textureBase = new ResourceLocation(MCGProject.ID, "textures/entity/cart/" + _name + "_base.png");

    public RenderGRW4(RenderManager manager) {
        super(manager);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityMinecart cart) {
        return textureBase;
    }

    @Override
    public void render(EntityMinecart minecart, double x, double y, double z, float yaw) {
        if (minecart instanceof GRW4) {
            GRW4 cart = (GRW4) minecart;
            GL11.glTranslated(0.0D, cart.getShiftY() - 1.0D, 0.0D);
            RendererHelper.renderWithResource(modelHead, textureBase);
        }

    }

    public static class Basket extends AbsContainerRenderer {

        public Basket(RenderManager manager) {
            super(manager);
        }

        @Override
        public void render(AbsContainer container, double x, double y, double z, float yaw) {
            RendererHelper.renderWithResource(RenderGRW4.modelBase, RenderGRW4.textureBase);
        }

    }

}
