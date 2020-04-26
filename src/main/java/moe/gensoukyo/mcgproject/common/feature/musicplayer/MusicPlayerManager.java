package moe.gensoukyo.mcgproject.common.feature.musicplayer;

import net.minecraft.client.Minecraft;
import net.minecraft.util.SoundCategory;

/**
 * @author MrMks
 */
public class MusicPlayerManager {
    private MusicThread thread;
    private float volume = 0.0f;
    public MusicPlayer getNewPlayer(String url) {
        thread = new MusicThread(this);
        MusicPlayer player = new MusicPlayer(url);
        thread.add(player);
        return player;
    }

    public void clean(){
        thread.clean();
    }

    public void disable(){
        thread.disable();
    }

    public void onMusicStopped(){
        Minecraft.getMinecraft().addScheduledTask(()->{
            Minecraft.getMinecraft().gameSettings.setSoundLevel(SoundCategory.MUSIC, volume);
            Minecraft.getMinecraft().gameSettings.saveOptions();
        });
    }

    public void onMusicStarted(){
        Minecraft.getMinecraft().addScheduledTask(()->{
            volume = Minecraft.getMinecraft().gameSettings.getSoundLevel(SoundCategory.MUSIC);
            Minecraft.getMinecraft().gameSettings.setSoundLevel(SoundCategory.MUSIC, 0);
            Minecraft.getMinecraft().gameSettings.saveOptions();
        });
    }
}
