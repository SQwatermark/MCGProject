package moe.gensoukyo.mcgproject.cilent.tileentity;

import moe.gensoukyo.mcgproject.cilent.util.Texture;
import moe.gensoukyo.mcgproject.cilent.util.TextureLoader;
import moe.gensoukyo.mcgproject.common.feature.sticker.TileSticker;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nonnull;

/**
 * @author drzzm32
 * @date 2020/4/12
 */
public class TileStickerRenderer extends TileEntitySpecialRenderer<TileSticker> {

    private static final String DEFAULT_RES = MCGProject.ID + ":" + "textures/sticker/";
    private static final String DEFAULT_URL = "http://update.gensoukyo.moe:9961/";

    private final ResourceLocation defTexture = new ResourceLocation(
            MCGProject.ID, "textures/blocks/fantasy/gap.png");

    @Override
    public void render(@Nonnull TileSticker sticker, double x, double y, double z, float partialTicks, int destroyStage, float partial) {
        if (sticker.shouldReload) {
            sticker.shouldReload = false;
            sticker.texture = null;
            TextureLoader.removeTexture(sticker);
            return;
        }

        if (sticker.texture == null) {
            if (sticker.url.toLowerCase().equals("null"))
                sticker.texture = defTexture;
            else {
                IResourceManager manager = Minecraft.getMinecraft().getResourceManager();
                ResourceLocation loc = new ResourceLocation(DEFAULT_RES + sticker.url);
                try {
                    manager.getResource(loc);
                    sticker.texture = loc;
                } catch (Exception e) {
                    sticker.texture = TextureLoader.getTexture(sticker, DEFAULT_URL + sticker.url);
                }
            }
        }

        TextureManager manager = Minecraft.getMinecraft().getTextureManager();
        Object texture = sticker.texture;
        if (texture instanceof ResourceLocation)
            manager.bindTexture((ResourceLocation) texture);
        else if (texture instanceof Texture)
            GlStateManager.bindTexture(((Texture) texture).getGlTextureId());
        else
            manager.bindTexture(defTexture);

        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);
        {
            GL11.glPushMatrix();
            GL11.glTranslated(sticker.offsetX, sticker.offsetY, sticker.offsetZ);
            {
                GL11.glPushMatrix();
                GL11.glRotated(sticker.rotateX, 1, 0, 0);
                GL11.glPushMatrix();
                GL11.glRotated(sticker.rotateY, 0, 1, 0);
                GL11.glPushMatrix();
                GL11.glRotated(sticker.rotateZ, 0, 0, 1);
                {
                    GL11.glPushMatrix();
                    GL11.glScaled(sticker.scaleX, sticker.scaleY, sticker.scaleZ);
                    {
                        switch (sticker.model) {
                            case TileSticker.MODEL_SINGLE:
                                renderSingle(sticker, partialTicks);
                                break;
                            case TileSticker.MODEL_DOUBLE:
                                renderDouble(sticker, partialTicks);
                                break;
                            default:
                                renderSingle(sticker, partialTicks);
                                GL11.glPushMatrix();
                                GL11.glRotated(180, 0, 1, 0);
                                renderSingle(sticker, partialTicks);
                                GL11.glPopMatrix();
                                break;
                        }
                    }
                    GL11.glPopMatrix();
                }
                GL11.glPopMatrix();
                GL11.glPopMatrix();
                GL11.glPopMatrix();
            }
            GL11.glPopMatrix();
        }
        GL11.glPopMatrix();
    }

    public void renderSingle(TileSticker sticker, float partialTicks) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder builder = tessellator.getBuffer();

        double x = sticker.x, y = sticker.y, u = sticker.u, v = sticker.v;
        switch (sticker.animate) {
            case TileSticker.ANIMATE_SCROLL_V:
            case TileSticker.ANIMATE_SCROLL_V_INV:
            case TileSticker.ANIMATE_SCROLL_H:
            case TileSticker.ANIMATE_SCROLL_H_INV:
                if (Math.abs(sticker.x - sticker.px) > 0.5)
                    x = sticker.x;
                else x = sticker.px + (sticker.x - sticker.px) * partialTicks;
                if (Math.abs(sticker.y - sticker.py) > 0.5)
                    y = sticker.y;
                else y = sticker.py + (sticker.y - sticker.py) * partialTicks;
                if (Math.abs(sticker.u - sticker.pu) > 0.5)
                    u = sticker.u;
                else u = sticker.pu + (sticker.u - sticker.pu) * partialTicks;
                if (Math.abs(sticker.v - sticker.pv) > 0.5)
                    v = sticker.v;
                else v = sticker.pv + (sticker.v - sticker.pv) * partialTicks;
            default:
                break;
        }

        int a = (int) ((sticker.color >> 24) & 0xFF);
        int r = (int) ((sticker.color >> 16) & 0xFF);
        int g = (int) ((sticker.color >> 8) & 0xFF);
        int b = (int) (sticker.color & 0xFF);
        builder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
        builder.pos(-0.5, 0.5, 0).tex(x + u, y)
                .color(a, r, g, b).normal(0, 0, 1).endVertex();
        builder.pos(0.5, 0.5, 0).tex(x, y)
                .color(a, r, g, b).normal(0, 0, 1).endVertex();
        builder.pos(0.5, -0.5, 0).tex(x, y + v)
                .color(a, r, g, b).normal(0, 0, 1).endVertex();
        builder.pos(-0.5, -0.5, 0).tex(x + u, y + v)
                .color(a, r, g, b).normal(0, 0, 1).endVertex();

        tessellator.draw();
    }

    public void renderDouble(TileSticker sticker, float partialTicks) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder builder = tessellator.getBuffer();

        double x = sticker.x, y = sticker.y, u = sticker.u, v = sticker.v;
        switch (sticker.animate) {
            case TileSticker.ANIMATE_SCROLL_V:
            case TileSticker.ANIMATE_SCROLL_V_INV:
            case TileSticker.ANIMATE_SCROLL_H:
            case TileSticker.ANIMATE_SCROLL_H_INV:
                x = sticker.px + (sticker.x - sticker.px) * partialTicks;
                y = sticker.py + (sticker.y - sticker.py) * partialTicks;
                u = sticker.pu + (sticker.u - sticker.pu) * partialTicks;
                v = sticker.pv + (sticker.v - sticker.pv) * partialTicks;
            default:
                break;
        }

        final double offset = 0.005;
        int a = (int) ((sticker.color >> 24) & 0xFF);
        int r = (int) ((sticker.color >> 16) & 0xFF);
        int g = (int) ((sticker.color >> 8) & 0xFF);
        int b = (int) (sticker.color & 0xFF);
        builder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
        builder.pos(-0.5, 0.5, offset).tex(x + u, y)
                .color(a, r, g, b).normal(0, 0, 1).endVertex();
        builder.pos(0.5, 0.5, offset).tex(x, y)
                .color(a, r, g, b).normal(0, 0, 1).endVertex();
        builder.pos(0.5, -0.5, offset).tex(x, y + v)
                .color(a, r, g, b).normal(0, 0, 1).endVertex();
        builder.pos(-0.5, -0.5, offset).tex(x + u, y + v)
                .color(a, r, g, b).normal(0, 0, 1).endVertex();

        builder.pos(0.5, 0.5, -offset).tex(x + u, y)
                .color(a, r, g, b).normal(0, 0, -1).endVertex();
        builder.pos(-0.5, 0.5, -offset).tex(x, y)
                .color(a, r, g, b).normal(0, 0, -1).endVertex();
        builder.pos(-0.5, -0.5, -offset).tex(x, y + v)
                .color(a, r, g, b).normal(0, 0, -1).endVertex();
        builder.pos(0.5, -0.5, -offset).tex(x + u, y + v)
                .color(a, r, g, b).normal(0, 0, -1).endVertex();

        tessellator.draw();
    }

}
