package moe.gensoukyo.mcgproject.common.network;

import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SignHandler implements IMessageHandler<SignPacket, IMessage> {
    @Override
    public IMessage onMessage(SignPacket packet, MessageContext context) {
        MCGProject.logger.info("onMessage开始执行");
        EntityPlayerMP player = context.getServerHandler().player;
        WorldServer world = player.getServerWorld();
        world.addScheduledTask(() -> {
            TileEntity te = world.getTileEntity(packet.getPos());

            if (te instanceof TileEntitySign) {
                TileEntitySign sign = (TileEntitySign) te;
                sign.signText[0] = new TextComponentString(packet.getText0());
                sign.signText[1] = new TextComponentString(packet.getText1());
                sign.signText[2] = new TextComponentString(packet.getText2());
                sign.signText[3] = new TextComponentString(packet.getText3());
                sign.markDirty();
            }
        });

        return null;
    }
}
