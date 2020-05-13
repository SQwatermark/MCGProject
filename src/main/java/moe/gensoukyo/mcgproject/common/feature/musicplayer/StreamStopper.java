package moe.gensoukyo.mcgproject.common.feature.musicplayer;

import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 当客户端卸载世界时，停止所有播放器
 */
public class StreamStopper {

    private static StreamStopper instance;
    public static StreamStopper instance() {
        if(instance == null) instance = new StreamStopper();
        return instance;
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onWorldUnload(WorldEvent.Unload event) {
        MCGProject.proxy.playerManager.clean();
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onPlayerDisconnected(FMLNetworkEvent.ClientDisconnectionFromServerEvent e){
        MCGProject.proxy.playerManager.clean();
    }
}