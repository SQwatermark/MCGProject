package moe.gensoukyo.mcgproject.common.network;

import moe.gensoukyo.mcgproject.common.tileentity.TileSticker;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

/**
 * @author drzzm32
 * @date 2020/4/13
 */
public class StickerRefreshHandler implements IMessageHandler<StickerRefreshPacket, IMessage> {
    @Override
    public IMessage onMessage(StickerRefreshPacket packet, MessageContext context) {
        if (context.side == Side.CLIENT) {
            Minecraft mc = Minecraft.getMinecraft();
            mc.addScheduledTask(() -> {
                World world = mc.world;
                if (!packet.isGlobal) {
                    TileEntity te = world.getTileEntity(packet.getPos());
                    if (te instanceof TileSticker)
                        ((TileSticker) te).refresh();
                } else {
                    for (TileEntity te : world.loadedTileEntityList) {
                        if (te instanceof TileSticker)
                            ((TileSticker) te).refresh();
                    }
                }
            });
        }

        return null;
    }
}
