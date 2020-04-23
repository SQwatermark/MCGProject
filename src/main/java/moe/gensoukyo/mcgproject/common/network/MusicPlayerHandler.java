package moe.gensoukyo.mcgproject.common.network;

import moe.gensoukyo.mcgproject.common.feature.musicplayer.EntityMusicPlayer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MusicPlayerHandler implements IMessageHandler<MusicPlayerPacket, IMessage> {

    @Override
    public IMessage onMessage(MusicPlayerPacket message, MessageContext context) {
        Entity entity = context.getServerHandler().player.world.getEntityByID(message.entityID);
        if (entity instanceof EntityMusicPlayer) {
            ((EntityMusicPlayer) entity).receivePacket(message.url, message.isPlaying, message.volume, message.owner);
        }

        return null;
    }

}
