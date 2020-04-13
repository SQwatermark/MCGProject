package moe.gensoukyo.mcgproject.cilent.entity;

import moe.gensoukyo.mcgproject.common.entity.EntityGap;
import moe.gensoukyo.mcgproject.core.Information;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;

@Information(author = {"RiskyKen", "SQwatermark"}, licence = "CC BY-NC-SA 3.0", source = "https://github.com/RiskyKen/Armourers-Workshop")
@SideOnly(Side.CLIENT)
public class RenderGap extends Render<EntityGap> {

    public static IRenderFactory<EntityGap> FACTORY = RenderGap::new;
    ResourceLocation circle = new ResourceLocation(MCGProject.ID, "textures/other/nanoha-circle.png");

    protected RenderGap(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void doRender(@NotNull EntityGap entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        int r = 255, g = 255, b = 255;
        GlStateManager.pushMatrix();
        GlStateManager.pushAttrib();
        GlStateManager.color(1F, 1F, 1F, 1F);
        GlStateManager.disableLighting();
        GlStateManager.disableCull();
        GlStateManager.translate(x, y, z);
        GlStateManager.translate(0F, 0.1F, 0F);
        float lightX = OpenGlHelper.lastBrightnessX;
        float lightY = OpenGlHelper.lastBrightnessY;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);

        //大小
        float circleScale = 2F;
        GlStateManager.scale(circleScale, circleScale, circleScale);

        //动态和静态的旋转（本体坐标系，坐标轴跟着一起转的）
        float rotation = (float)((Minecraft.getMinecraft().world.getTotalWorldTime()) / 0.8D % 360D) + partialTicks;
        GlStateManager.rotate(rotation, 0, 1, 0);

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

        Minecraft.getMinecraft().renderEngine.bindTexture(circle);

        Tessellator tess = Tessellator.getInstance();
        tess.getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
        tess.getBuffer().pos(-1, 0, -1).tex(1, 0).color(r & 0xFF, g & 0xFF, b & 0xFF, 255).endVertex();
        tess.getBuffer().pos(1, 0, -1).tex(0, 0).color(r & 0xFF, g & 0xFF, b & 0xFF, 255).endVertex();
        tess.getBuffer().pos(1, 0, 1).tex(0, 1).color(r & 0xFF, g & 0xFF, b & 0xFF, 255).endVertex();
        tess.getBuffer().pos(-1, 0, 1).tex(1, 1).color(r & 0xFF, g & 0xFF, b & 0xFF, 255).endVertex();
        tess.draw();

        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lightX, lightY);
        GlStateManager.disableBlend();
        GlStateManager.enableCull();
        GlStateManager.enableLighting();
        GlStateManager.color(1F, 1F, 1F, 1F);
        GlStateManager.popAttrib();
        GlStateManager.popMatrix();

    }


    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityGap entity) {
        return null;
    }
}
