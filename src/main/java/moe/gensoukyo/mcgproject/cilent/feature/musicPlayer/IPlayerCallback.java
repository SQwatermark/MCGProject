package moe.gensoukyo.mcgproject.cilent.feature.musicPlayer;

public interface IPlayerCallback {
    void onMusicStopped();
    void onMusicStopped(String hash);
    void onMusicStarted();
    void onMusicStarted(String hash);
}
