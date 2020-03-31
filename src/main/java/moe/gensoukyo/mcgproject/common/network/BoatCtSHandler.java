package moe.gensoukyo.mcgproject.common.network;

import moe.gensoukyo.mcgproject.common.entity.boat.EntityMCGBoat;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * @author drzzm32
 * @date 2020/3/15
 * @apiNote 碰撞在服务端计算，把速度数据上传到服务端
 */
public class BoatCtSHandler implements IMessageHandler<BoatPacket, IMessage> {
    @Override
    public IMessage onMessage(BoatPacket packet, MessageContext context) {
        context.getServerHandler().player.getServerWorld().addScheduledTask(() -> {
            World world = DimensionManager.getWorld(packet.dim);
            Entity entity = world.getEntityByID(packet.id);

            if (entity instanceof EntityMCGBoat) {
                EntityMCGBoat boat = (EntityMCGBoat) entity;
                boat.vel = packet.vel;
                boat.vx = packet.vx;
                boat.vy = packet.vy;
                boat.vz = packet.vz;
            }
        });

        return null;
    }
}
