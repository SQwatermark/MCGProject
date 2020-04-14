package moe.gensoukyo.mcgproject.cilent.entity;

import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 未使用的文件（因为没弄懂版本控制就一起提交上来了）
 */
@SideOnly(Side.CLIENT)
public class RendererMCGPlayer implements LayerRenderer<EntityPlayer> {

    @Override
    public void doRenderLayer(EntityPlayer entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {

    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}
