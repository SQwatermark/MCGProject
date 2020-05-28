package moe.gensoukyo.mcgproject.cilent.tileentity;

import moe.gensoukyo.mcgproject.cilent.model.obj.WavefrontObject;
import moe.gensoukyo.mcgproject.common.feature.lightbulb.TileLightBulb;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nonnull;

/**
 * @author drzzm32
 * @date 2020/4/26
 */
public class TileLightBulbLightRenderer extends TileEntitySpecialRenderer<TileLightBulb> {

    public static final WavefrontObject MODEL = new WavefrontObject(
            new ResourceLocation(MCGProject.ID, "models/block/light_bulb_light.obj")
    );

    public static final ResourceLocation[] TEXTURES;

    static {
        TEXTURES = new ResourceLocation[16];
        for (int i = 0; i < 16; i++) {
            TEXTURES[i] = new ResourceLocation(MCGProject.ID,
                    "textures/blocks/ranstone/ranstone_lamp_light_"+ EnumDyeColor.byMetadata(i).getName() + ".png"
            );
        }
    }

    @Override
    public void render(@Nonnull TileLightBulb bulb, double x, double y, double z, float partialTicks, int destroyStage, float partial) {
        if (!bulb.isLit())
            return;

        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 0.5, y + 0.5, z + 0.5);
        Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURES[bulb.getBlockMetadata()]);

        RenderHelper.disableStandardItemLighting();

        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_CULL_FACE);

        if (Minecraft.isAmbientOcclusionEnabled()) {
            GL11.glShadeModel(GL11.GL_SMOOTH);
        } else {
            GL11.glShadeModel(GL11.GL_FLAT);
        }

        MODEL.renderAll();

        RenderHelper.enableStandardItemLighting();

        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }

}
