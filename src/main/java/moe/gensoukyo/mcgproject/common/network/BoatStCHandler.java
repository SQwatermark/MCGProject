package moe.gensoukyo.mcgproject.common.network;

import moe.gensoukyo.mcgproject.common.entity.boat.EntityMCGBoat;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

/**
 * @author drzzm32
 * @date 2020/3/30
 * @apiNote 碰撞在服务端计算，用于将速度同步到客户端
 */
public class BoatStCHandler implements IMessageHandler<BoatPacket, IMessage> {
    @Override
    public IMessage onMessage(BoatPacket packet, MessageContext context) {
        if (context.side == Side.CLIENT) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                Entity entity = Minecraft.getMinecraft().world.getEntityByID(packet.id);

                if (entity instanceof EntityMCGBoat) {
                    EntityMCGBoat boat = (EntityMCGBoat) entity;
                    boat.setVelocity(packet.vx, packet.vy, packet.vz);
                }
            });
        }

        return null;
    }
}
