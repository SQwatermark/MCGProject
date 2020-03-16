package moe.gensoukyo.mcgproject.common.network;

import io.netty.buffer.ByteBuf;
import moe.gensoukyo.mcgproject.common.entity.EntityMCGBoat;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author drzzm32
 * @date 2020/3/15
 */
public class BoatPacket implements IMessage {

    public double vel;

    public BoatPacket() {
        vel = 0;
    }

    @SideOnly(Side.CLIENT)
    public BoatPacket(EntityMCGBoat boat) {
        this();

        vel = boat.vel;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeDouble(vel);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        vel = buf.readDouble();
    }

}
