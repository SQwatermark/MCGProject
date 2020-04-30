package moe.gensoukyo.mcgproject.cilent.feature;

import moe.gensoukyo.mcgproject.cilent.gui.GuiCustomMainMenu;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class CustomMainMenu {

    private static CustomMainMenu instance;
    public static CustomMainMenu instance() {
        if(instance == null) instance = new CustomMainMenu();
        return instance;
    }

    @SubscribeEvent
    public void onOpenGUI(GuiOpenEvent event) {
        if(event.getGui() instanceof GuiMainMenu) {
            event.setGui(new GuiCustomMainMenu());
        }
    }
}
