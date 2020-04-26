package moe.gensoukyo.mcgproject.common.feature.musicplayer;

import java.util.HashSet;

/**
 * @author MrMks
 */
public class MusicThread extends Thread{
    private final HashSet<MusicPlayer> set = new HashSet<>();
    private final boolean running;
    private final MusicPlayerManager manager;
    private boolean startNoticed = false;
    private boolean stopNoticed = true;
    public MusicThread(MusicPlayerManager manager){
        running = true;
        this.manager = manager;
        start();
    }

    public void add(MusicPlayer player) {
        synchronized (set) {
            set.add(player);
        }
    }

    @Override
    public void run() {
        while (running || !set.isEmpty()) {
            synchronized (set) {
                for (MusicPlayer player : set) {
                    if (player.isPlaying() && player.isRequestStop()) player.stop();
                }
                set.removeIf(player -> !player.isPlaying() && player.isRequestStop());
                if (set.isEmpty()) {
                    if (!stopNoticed) {
                        stopNoticed = true;
                        startNoticed = false;
                        manager.onMusicStopped();
                    }
                } else {
                    if (!startNoticed) {
                        stopNoticed = false;
                        startNoticed = true;
                        manager.onMusicStarted();
                    }
                }
                try {
                    set.wait(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void clean(){
        synchronized (set) {
            set.forEach(MusicPlayer::requestStop);
        }
    }

    public void disable() {
    }
}
