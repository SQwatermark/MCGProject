package moe.gensoukyo.mcgproject.cilent.feature;

import moe.gensoukyo.mcgproject.cilent.entity.RendererMCGPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 未使用的文件（因为没弄懂版本控制就一起提交上来了）
 */
@SideOnly(Side.CLIENT)
public class MorePlayerRenderLayer {

    private static MorePlayerRenderLayer instance;
    public static MorePlayerRenderLayer instance() {
        if(instance == null) instance = new MorePlayerRenderLayer();
        return instance;
    }

    private MorePlayerRenderLayer() {
        this.addRenderLayer();
    }

    public void addRenderLayer() {
        for (RenderPlayer playerRender : Minecraft.getMinecraft().getRenderManager().getSkinMap().values()) {
            playerRender.addLayer(new RendererMCGPlayer());
        }
    }
}
