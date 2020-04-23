package moe.gensoukyo.mcgproject.common.network;

import io.netty.buffer.ByteBuf;
import moe.gensoukyo.mcgproject.common.feature.musicplayer.EntityMusicPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MusicPlayerGuiPacket implements IMessage {

    int musicPlayerId;

    public MusicPlayerGuiPacket() {
    }

    public MusicPlayerGuiPacket(EntityMusicPlayer musicPlayer) {
        this.musicPlayerId = musicPlayer.getEntityId();
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        musicPlayerId = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(musicPlayerId);
    }
}
