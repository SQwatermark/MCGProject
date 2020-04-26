package moe.gensoukyo.mcgproject.common.feature.musicplayer;

import java.util.HashSet;

/**
 * @author MrMks
 */
public class MusicThread extends Thread{
    private final HashSet<MP3Player> set = new HashSet<>();
    private boolean running;
    public MusicThread(){
        running = true;
        start();
    }

    public void add(MP3Player player) {
        synchronized (set) {
            set.add(player);
        }
    }

    @Override
    public void run() {
        while (running || !set.isEmpty()) {
            synchronized (set) {
                for (MP3Player player : set) {
                    if (player.isPlaying() && player.isRequestStop()) player.stop();
                }
                set.removeIf(player -> !player.isPlaying() && player.isRequestStop());
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
            set.forEach(MP3Player::requestStop);
        }
    }

    public void disable() {
    }
}
