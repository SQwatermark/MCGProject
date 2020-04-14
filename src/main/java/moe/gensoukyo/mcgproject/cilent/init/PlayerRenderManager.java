package moe.gensoukyo.mcgproject.cilent.init;

import moe.gensoukyo.mcgproject.cilent.entity.RendererMCGPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 未使用的文件（因为没弄懂版本控制就一起提交上来了）
 */
@SideOnly(Side.CLIENT)
public class PlayerRenderManager {

    private static PlayerRenderManager instance;
    public static PlayerRenderManager instance() {
        if(instance == null) instance = new PlayerRenderManager();
        return instance;
    }

    private PlayerRenderManager() {
        this.addRenderLayer();
    }

    public void addRenderLayer() {
        for (RenderPlayer playerRender : Minecraft.getMinecraft().getRenderManager().getSkinMap().values()) {
            playerRender.addLayer(new RendererMCGPlayer());
        }
    }
}
