package moe.gensoukyo.mcgproject.cilent.feature;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiButtonImage;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Iterator;
import java.util.List;

/**
 * 玩家物品栏界面不显示合成书
 */
@SideOnly(Side.CLIENT)
public class NoRecipeBook {

    private static NoRecipeBook instance;
    public static NoRecipeBook instance() {
        if(instance == null) instance = new NoRecipeBook();
        return instance;
    }

    @SubscribeEvent
    public void InitGui(GuiScreenEvent.InitGuiEvent.Post event) {
        removeRecipeGuide(event.getGui(), event.getButtonList());
    }

    private void removeRecipeGuide(GuiScreen gui, List<GuiButton> button_list) {
        Iterator<GuiButton> var2 = button_list.iterator();
        if (gui instanceof GuiInventory || gui instanceof GuiCrafting) {
            while (true) {
                GuiButton button;
                int button_id;
                do {
                    do {
                        if (!var2.hasNext()) {
                            return;
                        }
                        button = var2.next();
                    } while (!(button instanceof GuiButtonImage));
                    button_id = button.id;
                } while (button_id != 10);
                button.visible = false;
            }
        }
    }
}
