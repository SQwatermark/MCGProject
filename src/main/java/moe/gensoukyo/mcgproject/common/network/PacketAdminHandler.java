package moe.gensoukyo.mcgproject.common.network;

import moe.gensoukyo.mcgproject.cilent.gui.PackAdmin;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author drzzm32
 * @date 2020/6/7
 */
public class PacketAdminHandler implements IMessageHandler<PackAdminPacket, IMessage> {

    @SideOnly(Side.CLIENT)
    public void showAdmin(PackAdmin.Data data) {
        new PackAdmin(data);
    }

    @Override
    public IMessage onMessage(PackAdminPacket packet, MessageContext context) {
        if (context.side == Side.CLIENT) {
            PackAdmin.Data data = new PackAdmin.Data();
            for (String name : packet.getNames()) {
                data.add(name, packet.getTypes(name).toArray(new String[0]));
            }
            showAdmin(data);
        }

        return null;
    }

}
