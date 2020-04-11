package moe.gensoukyo.mcgproject.cilent.init;

import moe.gensoukyo.mcgproject.cilent.entity.RenderMCGPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PlayerRenderManager {

    private static PlayerRenderManager instance;
    public static PlayerRenderManager instance() {
        if(instance == null) instance = new PlayerRenderManager();
        return instance;
    }

    private PlayerRenderManager() {
        this.addRenderLayer();
    }

    @SideOnly(Side.CLIENT)
    public void addRenderLayer() {
        for (RenderPlayer playerRender : Minecraft.getMinecraft().getRenderManager().getSkinMap().values()) {
            playerRender.addLayer(new RenderMCGPlayer());
        }
    }
}
