package moe.gensoukyo.mcgproject.common.network;

import moe.gensoukyo.mcgproject.cilent.gui.GuiGensoChest;
import moe.gensoukyo.mcgproject.common.feature.backpack.BackpackCore;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author drzzm32
 * @date 2020/3/17
 * @apiNote 幻想箱/背包的GUI显示数据包处理器（客户端）
 */
public class BackpackGuiHandler implements IMessageHandler<BackpackPacket, IMessage> {

    @SideOnly(Side.CLIENT)
    public void showBackpack(int window, BackpackCore.Backpack backpack) {
        Minecraft mc = Minecraft.getMinecraft();
        mc.addScheduledTask(() -> {
            mc.displayGuiScreen(new GuiGensoChest(mc.player.inventory, backpack));
            mc.player.openContainer.windowId = window;
        });
    }

    @Override
    public IMessage onMessage(BackpackPacket packet, MessageContext context) {
        if (context.side == Side.CLIENT) {
            // 客户端显示个空包GUI，然后游戏自己会同步物品
            BackpackCore.Backpack backpack = packet.getBackpack();
            showBackpack(packet.getWindowId(), backpack);
        }

        return null;
    }

}
