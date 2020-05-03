package moe.gensoukyo.mcgproject.cilent.feature.musicPlayer;

import moe.gensoukyo.mcgproject.common.feature.musicplayer.IMusic;
import moe.gensoukyo.mcgproject.common.feature.musicplayer.MusicManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.UUID;

@SideOnly(Side.CLIENT)
public class ClientMusicManager extends MusicManager {
    private final HashMap<String, MusicPlayer> playerMap = new HashMap<>();
    private final MusicThread thread = new MusicThread(new IPlayerCallback() {
        private float volume;
        @Override
        public void onMusicStopped(){
            Minecraft.getMinecraft().addScheduledTask(()->{
                Minecraft.getMinecraft().gameSettings.setSoundLevel(SoundCategory.MUSIC, volume);
                Minecraft.getMinecraft().gameSettings.saveOptions();
            });
        }

        @Override
        public void onMusicStopped(String hash) {
            playerMap.remove(hash);
        }

        @Override
        public void onMusicStarted(){
            Minecraft.getMinecraft().addScheduledTask(()->{
                volume = Minecraft.getMinecraft().gameSettings.getSoundLevel(SoundCategory.MUSIC);
                Minecraft.getMinecraft().gameSettings.setSoundLevel(SoundCategory.MUSIC, 0);
                Minecraft.getMinecraft().gameSettings.saveOptions();
            });
        }

        @Override
        public void onMusicStarted(String hash) {
            playerMap.remove(hash);
        }

    });

    @Override
    public String playNew(UUID uuid, String fullPath, World world, double x, double y, double z, int start) {
        if (entityMap.containsKey(uuid)) playerMap.remove(entityMap.get(uuid)).requestStop();

        String hashcode = super.playNew(uuid, fullPath, world, x, y, z, start);
        IMusic music = map.get(hashcode);
        MusicPlayer player = new MusicPlayer(hashcode, music);
        thread.add(player);
        playerMap.put(hashcode, player);
        return hashcode;
    }

    @Override
    public void updateVolume(String hash) {
        IMusic music = map.get(hash);
        MusicPlayer player = playerMap.get(hash);
        EntityPlayerSP playerSP = Minecraft.getMinecraft().player;
        if (player != null) {
            if (playerSP.world.equals(music.getWorld())) player.updateVolume(playerSP.world, playerSP.posX, playerSP.posY, playerSP.posZ);
            else closePlaying(hash);
        } else closePlaying(hash);
    }

    @Override
    public void closePlaying(String hash) {
        if (playerMap.containsKey(hash)) playerMap.remove(hash).requestStop();
        super.closePlaying(hash);
    }

    @Override
    public void closeAll(UUID uuid) {
        if (entityMap.containsKey(uuid)) playerMap.remove(entityMap.get(uuid)).requestStop();
        super.closeAll(uuid);
    }

    @Override
    public void closeAll() {
        playerMap.values().forEach(MusicPlayer::requestStop);
        playerMap.clear();
        super.closeAll();
    }

    @Override
    public int getPosition(String hash) {
        if (playerMap.containsKey(hash)) return playerMap.get(hash).getPosition();
        return 0;
    }
}
