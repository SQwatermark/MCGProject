package moe.gensoukyo.mcgproject.common.network;

import io.netty.buffer.ByteBuf;
import moe.gensoukyo.mcgproject.common.entity.boat.EntityMCGBoat;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author drzzm32
 * @date 2020/3/15
 */
public class BoatPacket implements IMessage {

    public double vel;
    public double vx, vy, vz;

    public BoatPacket() {
        vel = 0;
        vx = 0; vy = 0; vz = 0;
    }

    @SideOnly(Side.CLIENT)
    public BoatPacket(EntityMCGBoat boat) {
        this();

        vel = boat.vel;
        vx = boat.motionX;
        vy = boat.motionY;
        vz = boat.motionZ;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeDouble(vel);
        buf.writeDouble(vx);
        buf.writeDouble(vy);
        buf.writeDouble(vz);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        vel = buf.readDouble();
        vx = buf.readDouble();
        vy = buf.readDouble();
        vz = buf.readDouble();
    }

}
