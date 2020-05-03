package moe.gensoukyo.mcgproject.cilent.feature.musicPlayer;

import java.util.HashSet;

/**
 * @author MrMks
 */
class MusicThread extends Thread{
    private final HashSet<MusicPlayer> set = new HashSet<>();
    private final boolean running;
    private final IPlayerCallback callback;
    private boolean startNoticed = false;
    private boolean stopNoticed = true;
    public MusicThread(IPlayerCallback callback){
        running = true;
        this.callback = callback;
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
                set.forEach(player->{
                    if (player.isRequestStop() && !player.isPlaying()) {
                        callback.onMusicStopped(player.getName());
                    }
                });
                set.removeIf(player -> !player.isPlaying() && player.isRequestStop());
                if (set.isEmpty()) {
                    if (!stopNoticed) {
                        stopNoticed = true;
                        startNoticed = false;
                        callback.onMusicStopped();
                    }
                } else {
                    if (!startNoticed) {
                        stopNoticed = false;
                        startNoticed = true;
                        callback.onMusicStarted();
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
