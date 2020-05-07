package moe.gensoukyo.mcgproject.common.feature.chatfilter;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ChatFilterHandler {

    private static ChatFilterHandler instance;
    public static ChatFilterHandler instance() {
        if (instance == null)
            instance = new ChatFilterHandler();
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
