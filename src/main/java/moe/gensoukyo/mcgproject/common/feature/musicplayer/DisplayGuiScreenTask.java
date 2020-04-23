package moe.gensoukyo.mcgproject.common.feature.musicplayer;

import moe.gensoukyo.mcgproject.cilent.gui.GuiMusicPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

/**
 * 用于显示GUI，客户端的GuiMusicPlayer类不能直接出现在EntityMusicPlayer类下，所以用了一层套娃
 */
public class DisplayGuiScreenTask {
    public DisplayGuiScreenTask(EntityPlayer entityPlayer, EntityMusicPlayer musicPlayer) {
        Minecraft mc = Minecraft.getMinecraft();
        mc.addScheduledTask(() -> mc.displayGuiScreen(new GuiMusicPlayer(entityPlayer, musicPlayer)));
    }
}
