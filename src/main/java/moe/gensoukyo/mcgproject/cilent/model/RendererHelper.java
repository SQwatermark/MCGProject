package moe.gensoukyo.mcgproject.cilent.model;

import moe.gensoukyo.mcgproject.cilent.model.obj.WavefrontObject;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

/**
 * @author drzzm32
 * @date 2020/3/27
 */
@SideOnly(Side.CLIENT)
public class RendererHelper {

    public static void beginSpecialLightingNoDepth() {
        RenderHelper.disableStandardItemLighting();

        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_CULL_FACE);

        if (Minecraft.isAmbientOcclusionEnabled()) {
            GL11.glShadeModel(GL11.GL_SMOOTH);
        } else {
            GL11.glShadeModel(GL11.GL_FLAT);
        }

        GL11.glDepthMask(false);
    }

    public static void endSpecialLightingNoDepth() {
        GL11.glDepthMask(true);
        RenderHelper.enableStandardItemLighting();
    }

    public static void beginSpecialLighting() {
        RenderHelper.disableStandardItemLighting();

        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_CULL_FACE);

        if (Minecraft.isAmbientOcclusionEnabled()) {
            GL11.glShadeModel(GL11.GL_SMOOTH);
        } else {
            GL11.glShadeModel(GL11.GL_FLAT);
        }
    }

    public static void endSpecialLighting() {
        RenderHelper.enableStandardItemLighting();
    }

    public static void renderWithResourceAndRotation(WavefrontObject model, float angle, ResourceLocation texture) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        GL11.glPushMatrix();
        GL11.glScalef(0.0625F, 0.0625F, 0.0625F);
        GL11.glPushMatrix();
        GL11.glRotatef(angle, 0.0F, -1.0F, 0.0F);
        model.renderAll();
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }

    public static void renderWithResource(WavefrontObject model, ResourceLocation texture) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        GL11.glPushMatrix();
        GL11.glScalef(0.0625F, 0.0625F, 0.0625F);
        model.renderAll();
        GL11.glPopMatrix();
    }

    public static void renderPartWithResource(WavefrontObject model, String part, ResourceLocation texture) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        GL11.glPushMatrix();
        GL11.glScalef(0.0625F, 0.0625F, 0.0625F);
        model.renderPart(part);
        GL11.glPopMatrix();
    }

    public static void renderPartWithResourceAndRotation(WavefrontObject model, String part, float angle, ResourceLocation texture) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        GL11.glPushMatrix();
        GL11.glScalef(0.0625F, 0.0625F, 0.0625F);
        GL11.glPushMatrix();
        GL11.glRotatef(angle, 0.0F, -1.0F, 0.0F);
        model.renderPart(part);
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }

    public static void renderOtherPartWithResourceAndRotation(WavefrontObject model, String part, float angle, ResourceLocation texture) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        GL11.glPushMatrix();
        GL11.glScalef(0.0625F, 0.0625F, 0.0625F);
        GL11.glPushMatrix();
        GL11.glRotatef(angle, 0.0F, -1.0F, 0.0F);
        model.renderAllExcept(part);
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }

    public static void renderWithResource4(WavefrontObject model, ResourceLocation texture) {
        for (int i = 0; i < 4; i++)
            renderWithResourceAndRotation(model, 90.0F * i, texture);
    }

}
