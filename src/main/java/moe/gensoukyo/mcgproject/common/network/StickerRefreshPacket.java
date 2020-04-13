package moe.gensoukyo.mcgproject.common.network;

import io.netty.buffer.ByteBuf;
import moe.gensoukyo.mcgproject.common.tileentity.TileSticker;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

/**
 * @author drzzm32
 * @date 2020/4/13
 */
public class StickerRefreshPacket implements IMessage {

    public NBTTagCompound tag;
    public boolean isGlobal;

    public StickerRefreshPacket() {
        tag = null;
        isGlobal = false;
    }

    public StickerRefreshPacket(boolean isGlobal) {
        tag = null;
        this.isGlobal = isGlobal;
    }

    public StickerRefreshPacket(TileSticker sticker) {
        this.tag = new NBTTagCompound();
        BlockPos pos = sticker.getPos();
        tag.setInteger("x", pos.getX());
        tag.setInteger("y", pos.getY());
        tag.setInteger("z", pos.getZ());
        this.isGlobal = false;
    }

    public BlockPos getPos() {
        if (tag == null) return null;
        return new BlockPos(
                tag.getInteger("x"),
                tag.getInteger("y"),
                tag.getInteger("z")
        );
    }

    @Override
    public void toBytes(ByteBuf buf) {
        try {
            (new PacketBuffer(buf)).writeCompoundTag(tag);
        } catch (Exception ignored) { }
        buf.writeBoolean(isGlobal);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        try {
            tag = (new PacketBuffer(buf)).readCompoundTag();
        } catch (Exception ignored) { }
        isGlobal = buf.readBoolean();
    }

}
