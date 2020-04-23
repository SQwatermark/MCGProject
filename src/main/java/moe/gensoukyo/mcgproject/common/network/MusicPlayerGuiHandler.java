package moe.gensoukyo.mcgproject.common.network;

import moe.gensoukyo.mcgproject.common.feature.musicplayer.DisplayGuiScreenTask;
import moe.gensoukyo.mcgproject.common.feature.musicplayer.EntityMusicPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MusicPlayerGuiHandler implements IMessageHandler<MusicPlayerGuiPacket, IMessage>{
    @Override
    public IMessage onMessage(MusicPlayerGuiPacket message, MessageContext ctx) {
        Entity entity = Minecraft.getMinecraft().world.getEntityByID(message.musicPlayerId);
        if (entity instanceof EntityMusicPlayer) {
            new DisplayGuiScreenTask((EntityMusicPlayer) entity);
        }
        return null;
    }
}