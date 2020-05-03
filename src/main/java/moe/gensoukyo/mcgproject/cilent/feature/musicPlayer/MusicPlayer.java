package moe.gensoukyo.mcgproject.cilent.feature.musicPlayer;

import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;
import moe.gensoukyo.mcgproject.common.feature.musicplayer.IMusic;
import net.minecraft.client.Minecraft;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

/**
 * @author MrMks
 */
class MusicPlayer extends PlaybackListener implements Runnable {
	private String streamURL;
	private AdvancedPlayer player;
	private VolumeAudioDevice device;
	private boolean isPlaying;
	private boolean request;
	private int start = 0;

	public MusicPlayer(String url, int start){
		try {
			streamURL = url;
			this.start = start;
			request = false;
			isPlaying = true;
			new Thread(this).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String name;
	private IMusic music;
	public MusicPlayer(String name, IMusic music){
		try {
			this.name = name;
			this.music = music;
			request = false;
			isPlaying = true;
			new Thread(this).start();
		} catch (Throwable tr) {
			tr.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			device = new VolumeAudioDevice();
			player = new AdvancedPlayer(music.openStream(), device);
			player.setPlayBackListener(this);
			// 播放时，线程会卡在这里，直到停止播放
			player.play(start, Integer.MAX_VALUE);
			isPlaying = false;
		} catch (Exception ignored) {}
	}

	public void stop() {
		if ((player != null) && (isPlaying())) {
			player.stop();
		}
	}

	/**
	 * 这个方法用于请求停止这个播放器，请求后会由MusicThread来不断尝试关闭这个播放器直到成功将其关闭。
	 */
	public void requestStop(){
		request = true;
	}

	public boolean isRequestStop(){
		return request;
	}

	@Override
	public void playbackStarted(PlaybackEvent evt) {}

	@Override
	public void playbackFinished(PlaybackEvent evt) {}

	public boolean isPlaying() {
		return isPlaying;
	}

	public void updateVolume(World world, double x, double y, double z){
		setVolume(music.getVolume(world, x, y, z));
	}

	public void setVolume(float f)
	{
		if (device != null)
		{
			device.setVolume(f * Minecraft.getMinecraft().gameSettings.getSoundLevel(SoundCategory.RECORDS)
					* Minecraft.getMinecraft().gameSettings.getSoundLevel(SoundCategory.MASTER));
		}
	}

	public float getVolume() {
		if (player != null) {
			return device.getVolume();
		}
		else {
			return 0.0f;
		}
	}

	public String getName(){
		return name;
	}

	public int getPosition(){
		return device.getPosition();
	}
}
