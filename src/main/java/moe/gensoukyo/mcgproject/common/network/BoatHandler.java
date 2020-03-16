package moe.gensoukyo.mcgproject.common.network;

import moe.gensoukyo.mcgproject.common.entity.EntityMCGBoat;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * @author drzzm32
 * @date 2020/3/15
 */
public class BoatHandler implements IMessageHandler<BoatPacket, IMessage> {
    @Override
    public IMessage onMessage(BoatPacket packet, MessageContext context) {
        EntityPlayerMP player = context.getServerHandler().player;

        if (player.getRidingEntity() instanceof EntityMCGBoat) {
            EntityMCGBoat boat = (EntityMCGBoat) player.getRidingEntity();
            boat.vel = packet.vel;
        }

        return null;
    }
}
