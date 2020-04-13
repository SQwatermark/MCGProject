package moe.gensoukyo.mcgproject.cilent.gui;

import moe.gensoukyo.mcgproject.common.feature.backpack.BackpackCore;
import moe.gensoukyo.mcgproject.common.feature.backpack.ContainerGensoChest;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Mouse;

/**
 * @author drzzm32
 * @date 2020/3/17
 * @apiNote 幻想箱/背包的GUI类
 */
@SideOnly(Side.CLIENT)
public class GuiGensoChest extends GuiContainer {
    private static final ResourceLocation CHEST_GUI_TEXTURE = new ResourceLocation(MCGProject.ID, "textures/gui/backpack.png");
    private final IInventory upperChestInventory;
    private final IInventory lowerChestInventory;
    private int counter = 0;

    public GuiGensoChest(IInventory playerPack, IInventory backpack) {
        super(new ContainerGensoChest(playerPack, backpack, Minecraft.getMinecraft().player));
        this.upperChestInventory = playerPack;
        this.lowerChestInventory = backpack;
        this.allowUserInput = false;
        this.ySize = 114 + backpack.getSizeInventory() / BackpackCore.COLUMN * 18;
        this.xSize = 500;
    }

    public void drawScreen(int x, int y, float f) {
        if (counter < 42) {
            counter += 1;
            if (Mouse.isCreated())
                Mouse.setGrabbed(false);
        }
        if (this.mc == null)
            return;
        this.drawDefaultBackground();
        super.drawScreen(x, y, f);
        this.renderHoveredToolTip(x, y);
    }

    protected void drawGuiContainerForegroundLayer(int x, int y) {
        this.fontRenderer.drawString(this.lowerChestInventory.getDisplayName().getUnformattedText(), 8, 6, 4210752);
        this.fontRenderer.drawString(this.upperChestInventory.getDisplayName().getUnformattedText(), 170, this.ySize - 96 + 2, 4210752);
    }

    private void draw(int x, int y, float u, float v, int w, int h, float tw, float th) {
        float us = 1.0F / tw;
        float vs = 1.0F / th;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        buffer.pos((double)x, (double)(y + h), 0.0D).tex((double)(u * us), (double)((v + (float)h) * vs)).endVertex();
        buffer.pos((double)(x + w), (double)(y + h), 0.0D).tex((double)((u + (float)w) * us), (double)((v + (float)h) * vs)).endVertex();
        buffer.pos((double)(x + w), (double)y, 0.0D).tex((double)((u + (float)w) * us), (double)(v * vs)).endVertex();
        buffer.pos((double)x, (double)y, 0.0D).tex((double)(u * us), (double)(v * vs)).endVertex();
        tessellator.draw();
    }

    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(CHEST_GUI_TEXTURE);
        x = (this.width - this.xSize) / 2;
        y = (this.height - this.ySize) / 2;
        draw(x, y, 0, 0, xSize, ySize, 512, 512);
    }

}
