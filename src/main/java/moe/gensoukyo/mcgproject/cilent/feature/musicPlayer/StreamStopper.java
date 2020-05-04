package moe.gensoukyo.mcgproject.cilent.feature.musicPlayer;

import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 当客户端卸载世界时，停止所有播放器
 * 应当仅在物理客户端存在
 */
@SideOnly(Side.CLIENT)
public class StreamStopper {

    private static StreamStopper instance;
    public static StreamStopper instance() {
        if(instance == null) instance = new StreamStopper();
        return instance;
    }

    @SubscribeEvent
    public void onWorldUnload(WorldEvent.Unload event) {
        if (event.getWorld().isRemote) {
            MCGProject.proxy.getMusicManager().closeAll();
        }
    }
}