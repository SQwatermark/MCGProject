package moe.gensoukyo.mcgproject.common.feature.musicplayer;

import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.UUID;

public class MusicManager implements IMusicManager {
    protected final HashMap<String, IMusic> map = new HashMap<>();
    protected final HashMap<UUID, String> entityMap = new HashMap<>();

    @Override
    public String playNew(UUID uuid, String fullPath, World world, double x, double y, double z) {
        return playNew(uuid,fullPath,world,x,y,z,0);
    }

    @Override
    public String playNew(UUID uuid, String fullPath, World world, double x, double y, double z, int start) {
        if (entityMap.containsKey(uuid)) {
            map.remove(entityMap.remove(uuid));
        }
        IMusic music = new Music(fullPath, start, world, x ,y, z);
        String mUUID = UUID.randomUUID().toString().replace("-", "");
        map.put(mUUID, music);
        entityMap.put(uuid, mUUID);
        return mUUID;
    }

    @Override
    public boolean isExist(@Nullable String hash) {
        return hash != null && map.containsKey(hash);
    }

    @Override
    public boolean isPlaying(String hash) {
        return isExist(hash);
    }

    @Override
    public void changeMaxVolume(String hash, float volume) {
        if (map.containsKey(hash)) map.get(hash).setMaxVolume(volume);
    }

    @Override
    public void updatePosition(String hash, World world, double x, double y, double z) {
        if (map.containsKey(hash)) map.get(hash).update(world, x, y, z);
    }

    @Override
    public void updateVolume(String hash) {

    }

    @Override
    public void closePlaying(String hash) {
        entityMap.values().removeIf(v-> v.equals(hash));
        map.keySet().removeIf(v-> v.equals(hash));
    }

    @Override
    public void closeAll(UUID uuid) {
        if (entityMap.containsKey(uuid)) map.remove(entityMap.remove(uuid));
    }

    @Override
    public void closeAll() {
        entityMap.clear();
        map.clear();
    }

    @Override
    public int getPosition(String hash) {
        if (map.containsKey(hash)) return map.get(hash).getStart();
        return 0;
    }
}
