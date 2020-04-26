package moe.gensoukyo.mcgproject.common.feature.musicplayer;

import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;
import moe.gensoukyo.mcgproject.core.MCGProject;

import java.net.URL;

/**
 * @author MrMks
 */
public class MusicPlayer extends PlaybackListener implements Runnable {
	private String streamURL;
	private AdvancedPlayer player;
	private boolean isPlaying;
	private boolean request;

	public MusicPlayer(String mp3url) {
		try {
			streamURL = mp3url;
			request = false;
			new Thread(this).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			isPlaying = true;
			player = new AdvancedPlayer(new URL(streamURL).openStream());
			player.setPlayBackListener(this);
			player.play();
			isPlaying = false;
		} catch (Exception ignored) {}
	}

	public void stop() {
		if ((player != null) && (isPlaying())) {
			player.stop();
		}
	}

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

	public void setVolume(float f)
	{
		if (player != null)
		{
			player.setVolume(f * MCGProject.proxy.getJukeboxVolume());
		}
	}

	public float getVolume() {
		if (player != null) {
			return player.getVolume();
		}
		else {
			return 0.0f;
		}
	}
}
