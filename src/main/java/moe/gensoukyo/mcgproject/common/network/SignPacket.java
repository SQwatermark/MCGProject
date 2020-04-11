package moe.gensoukyo.mcgproject.common.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SignPacket implements IMessage {

    private int x, y, z;
    private String text0;
    private String text1;
    private String text2;
    private String text3;

    public SignPacket() {
        text0 = "";
        text1 = "";
        text2 = "";
        text3 = "";
    }

    @SideOnly(Side.CLIENT)
    public SignPacket(BlockPos pos, ITextComponent[] text) {
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
        this.text0 = text[0].getFormattedText();
        this.text1 = text[1].getFormattedText();
        this.text2 = text[2].getFormattedText();
        this.text3 = text[3].getFormattedText();
    }

    public BlockPos getPos() {
        return new BlockPos(x, y, z);
    }

    public String getText0() {
        return text0;
    }

    public String getText1() {
        return text1;
    }

    public String getText2() {
        return text2;
    }

    public String getText3() {
        return text3;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        PacketBuffer buffer = new PacketBuffer(buf);
        buffer.writeInt(x);
        buffer.writeInt(y);
        buffer.writeInt(z);
        buffer.writeString(text0);
        buffer.writeString(text1);
        buffer.writeString(text2);
        buffer.writeString(text3);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        PacketBuffer buffer = new PacketBuffer(buf);
        x = buffer.readInt();
        y = buffer.readInt();
        z = buffer.readInt();
        text0 = buffer.readString(1024);
        text1 = buffer.readString(1024);
        text2 = buffer.readString(1024);
        text3 = buffer.readString(1024);
    }

}
