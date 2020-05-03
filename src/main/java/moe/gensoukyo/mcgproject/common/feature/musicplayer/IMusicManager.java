package moe.gensoukyo.mcgproject.common.feature.musicplayer;

import javax.annotation.Nullable;
import java.util.UUID;

public interface IMusicManager {
    /**
     * equal to playNew(uuid, fullPath, x, y, z, 0)
     * {@link IMusicManager#playNew(UUID, String, double, double, double, int)}
     */
    String playNew(UUID uuid, String fullPath, double x, double y, double z);

    /**
     * generate a new IMusic
     * @param uuid uuid of the EntityMusicPlayer
     * @param fullPath the fullPath of the .mp3 file
     * @param x posX of entity
     * @param y posY of entity
     * @param z posZ of entity
     * @param start the first frame to play
     * @return a string use to identify the music
     */
    String playNew(UUID uuid, String fullPath, double x, double y, double z, int start);

    boolean isExist(@Nullable String hash);
    boolean isPlaying(String hash);

    void changeMaxVolume(String hash, float volume);
    void updatePosition(String hash, double x, double y, double z);
    void updateVolume(String hash);
    void updateVolume();

    void closePlaying(String hash);
    void closeAll(UUID uuid);
    void closeAll();

    int getPosition(String hash);
}
