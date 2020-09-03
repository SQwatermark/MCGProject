package moe.gensoukyo.mcgproject.common.network;

import io.netty.buffer.ByteBuf;
import moe.gensoukyo.mcgproject.common.feature.backpack.BackpackCore;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author drzzm32
 * @date 2020/3/17
 * @apiNote 幻想箱/背包的数据包类
 */
public class BackpackPacket implements IMessage {

    private int windowId;
    private String playerId;
    private String packType;
    private BackpackCore.Backpack backpack;
    private int packSize;

    public BackpackPacket() { }

    public BackpackPacket(int window, String player, String packType, BackpackCore.Backpack backpack) {
        this.windowId = window;
        this.playerId = player;
        this.packType = packType;
        this.backpack = backpack;
        this.packSize = backpack.getSizeInventory();
    }

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        PacketBuffer buffer = new PacketBuffer(byteBuf);
        this.windowId = buffer.readUnsignedByte();
        int len = buffer.readInt();
        this.playerId = buffer.readString(len);
        len = buffer.readInt();
        this.packType = buffer.readString(len);
        this.packSize = buffer.readInt();
        this.backpack = new BackpackCore.Backpack(playerId, packType, packSize);
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        PacketBuffer buffer = new PacketBuffer(byteBuf);
        buffer.writeByte(this.windowId);
        buffer.writeInt(this.playerId.length());
        buffer.writeString(this.playerId);
        buffer.writeInt(this.packType.length());
        buffer.writeString(this.packType);
        buffer.writeInt(this.packSize);
    }

    @SideOnly(Side.CLIENT)
    public int getWindowId() {
        return this.windowId;
    }

    public BackpackCore.Backpack getBackpack() {
        return backpack;
    }

}
