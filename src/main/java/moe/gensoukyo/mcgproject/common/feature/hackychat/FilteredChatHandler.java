package moe.gensoukyo.mcgproject.common.feature.hackychat;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * 替换原版聊天窗口
 * @author drzzm32
 * @date 2020/5/7
 */
public class FilteredChatHandler {

    private static FilteredChatHandler instance;
    public static FilteredChatHandler instance() {
        if (instance == null)
            instance = new FilteredChatHandler();
        return instance;
    }

    @SubscribeEvent
    public void tick(TickEvent.ClientTickEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.ingameGUI != null) {
            if (!(mc.ingameGUI.getChatGUI() instanceof GuiFilteredChat))
                GuiFilteredChat.replaceChatGui(mc);
        }
    }

}
