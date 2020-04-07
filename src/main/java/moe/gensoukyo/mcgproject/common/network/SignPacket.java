package moe.gensoukyo.mcgproject.common.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

/**
 * @author drzzm32
 * @date 2020/4/7
 */
public class SignPacket implements IMessage {

    public int x, y, z;
    public String text;

    public SignPacket() {
        text = "";
    }

    public SignPacket(BlockPos pos, String text) {
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
        this.text = text;
    }

    public BlockPos getPos() {
        return new BlockPos(x, y, z);
    }

    public String getText() {
        return text;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        PacketBuffer buffer = new PacketBuffer(buf);
        buffer.writeInt(x);
        buffer.writeInt(y);
        buffer.writeInt(z);
        buffer.writeInt(text.length());
        buffer.writeString(text);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        PacketBuffer buffer = new PacketBuffer(buf);
        x = buffer.readInt();
        y = buffer.readInt();
        z = buffer.readInt();
        int len = buffer.readInt();
        text = buffer.readString(len);
    }

}
