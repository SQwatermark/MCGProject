package moe.gensoukyo.mcgproject.common.feature.musicplayer;

import net.minecraft.client.Minecraft;
import net.minecraft.util.SoundCategory;

/**
 * @author MrMks
 */
public class MusicPlayerManager {
    private MusicThread thread;
    private float volume = 0.0f;

    /**
     * 获取一个新的音乐播放器，这个音乐播放器会自动被加入MusicThread
     * @param url 播放曲目链接
     * @return a new MusicPlayer
     */
    public MusicPlayer getNewPlayer(String url) {
        thread = new MusicThread(this);
        MusicPlayer player = new MusicPlayer(url);
        thread.add(player);
        return player;
    }

    /**
     * 这个方法会将所有正在播放的播放器关闭
     */
    public void clean(){
        thread.clean();
    }

    /**
     * 这个方法暂时没有实际效果。这个方法在设计上会将线程关闭，应当在进程结束时调用这个方法。
     */
    public void disable(){
        thread.disable();
    }

    /**
     * 下两个方法用于调整播放时Music的音量。
     * 不需要手动调用，会在在MusicThread中进行处理
     */
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
