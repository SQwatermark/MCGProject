package moe.gensoukyo.mcgproject.common.network;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * @author drzzm32
 * @date 2020/4/7
 */
public class SignHandler implements IMessageHandler<SignPacket, IMessage> {
    @Override
    public IMessage onMessage(SignPacket packet, MessageContext context) {
        EntityPlayerMP player = context.getServerHandler().player;
        WorldServer world = player.getServerWorld();
        world.addScheduledTask(() -> {
            TileEntity te = world.getTileEntity(packet.getPos());

            if (te instanceof TileEntitySign) {
                TileEntitySign sign = (TileEntitySign) te;
                sign.signText[0] = new TextComponentString(packet.getText());
            }
        });

        return null;
    }
}
