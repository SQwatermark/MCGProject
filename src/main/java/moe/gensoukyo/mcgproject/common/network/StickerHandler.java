package moe.gensoukyo.mcgproject.common.network;

import moe.gensoukyo.mcgproject.common.feature.sticker.BlockSticker;
import moe.gensoukyo.mcgproject.common.feature.sticker.TileSticker;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * @author drzzm32
 * @date 2020/4/13
 */
public class StickerHandler implements IMessageHandler<StickerPacket, IMessage> {
    @Override
    public IMessage onMessage(StickerPacket packet, MessageContext context) {
        EntityPlayerMP player = context.getServerHandler().player;
        if (!BlockSticker.heldItem(player.getHeldItemMainhand()) && !BlockSticker.heldItem(player.getHeldItemOffhand())) {
            player.sendMessage(new TextComponentString("Please held verified item!"));
            return null;
        }

        WorldServer world = player.getServerWorld();
        world.addScheduledTask(() -> {
            BlockPos pos = packet.getPos();
            if (pos != null && packet.getTag() != null) {
                TileEntity te = world.getTileEntity(pos);
                if (te instanceof TileSticker) {
                    ((TileSticker) te).fromNBT(packet.getTag());
                    te.markDirty();
                    ((TileSticker) te).refresh();
                    NetworkWrapper.INSTANCE.sendToAll(
                            new StickerRefreshPacket((TileSticker) te)
                    );
                }
            }
        });

        return null;
    }
}
