package moe.gensoukyo.mcgproject.common.network;

import io.netty.buffer.ByteBuf;
import moe.gensoukyo.mcgproject.common.feature.sticker.TileSticker;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

/**
 * @author drzzm32
 * @date 2020/4/13
 */
public class StickerPacket implements IMessage {

    public NBTTagCompound tag;

    public StickerPacket() {
        tag = null;
    }

    public StickerPacket(TileSticker sticker) {
        this.tag = new NBTTagCompound();
        BlockPos pos = sticker.getPos();
        tag.setInteger("x", pos.getX());
        tag.setInteger("y", pos.getY());
        tag.setInteger("z", pos.getZ());
        NBTTagCompound tagCompound = new NBTTagCompound();
        sticker.toNBT(tagCompound);
        tag.setTag("tag", tagCompound);
    }

    public BlockPos getPos() {
        if (tag == null) return null;
        return new BlockPos(
                tag.getInteger("x"),
                tag.getInteger("y"),
                tag.getInteger("z")
        );
    }

    public NBTTagCompound getTag() {
        if (tag == null) return null;
        if (!tag.hasKey("tag")) return null;
        return tag.getCompoundTag("tag");
    }

    @Override
    public void toBytes(ByteBuf buf) {
        try {
            (new PacketBuffer(buf)).writeCompoundTag(tag);
        } catch (Exception ignored) { }
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        try {
            tag = (new PacketBuffer(buf)).readCompoundTag();
        } catch (Exception ignored) { }
    }

}
