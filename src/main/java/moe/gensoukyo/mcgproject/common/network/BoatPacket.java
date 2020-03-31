package moe.gensoukyo.mcgproject.common.network;

import io.netty.buffer.ByteBuf;
import moe.gensoukyo.mcgproject.common.entity.boat.EntityMCGBoat;
import net.minecraft.util.math.Vec3d;
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
    public int dim, id;

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

        dim = boat.dimension;
        id = boat.getEntityId();
    }

    @SideOnly(Side.SERVER)
    public BoatPacket(EntityMCGBoat boat, Vec3d vec) {
        this();

        vel = boat.vel;
        vx = vec.x;
        vy = vec.y;
        vz = vec.z;

        dim = boat.dimension;
        id = boat.getEntityId();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeDouble(vel);
        buf.writeDouble(vx);
        buf.writeDouble(vy);
        buf.writeDouble(vz);
        buf.writeInt(dim);
        buf.writeInt(id);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        vel = buf.readDouble();
        vx = buf.readDouble();
        vy = buf.readDouble();
        vz = buf.readDouble();
        dim = buf.readInt();
        id = buf.readInt();
    }

}
