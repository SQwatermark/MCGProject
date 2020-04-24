package moe.gensoukyo.mcgproject.common.network;

import io.netty.buffer.ByteBuf;
import moe.gensoukyo.mcgproject.common.feature.musicplayer.EntityMusicPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

/**
 * Sent to the server to set a Jukebox' music stream URL.<p>
 * 
 * Note:<br>
 * The sent packet is handled so that the server directly applies the data.<br>
 * This might be considered bad, as the client's data remains unchecked for validity and such.<br>
 * Please fix this eventually, if possible.
 */
public class MusicPlayerPacket implements IMessage {

	int entityID;
	boolean isPlaying;
	String url;
	float volume;
	String owner;
	boolean immersive;

	public MusicPlayerPacket() {}

	public MusicPlayerPacket(EntityMusicPlayer musicPlayer) {
		this.entityID = musicPlayer.getEntityId();
		this.url = musicPlayer.streamURL;
		this.isPlaying = musicPlayer.isPlaying;
		this.volume = musicPlayer.volume;
		this.owner = musicPlayer.owner;
		this.immersive = musicPlayer.immersive;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.entityID = buf.readInt();
		this.isPlaying = buf.readBoolean();
		this.volume = buf.readFloat();
		this.immersive = buf.readBoolean();
		this.url = ByteBufUtils.readUTF8String(buf);
		this.owner = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.entityID);
		buf.writeBoolean(this.isPlaying);
		buf.writeFloat(this.volume);
		buf.writeBoolean(this.immersive);
		ByteBufUtils.writeUTF8String(buf, this.url);
		ByteBufUtils.writeUTF8String(buf, this.owner);
	}
}