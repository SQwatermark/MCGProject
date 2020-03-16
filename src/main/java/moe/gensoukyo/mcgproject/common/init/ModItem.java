package moe.gensoukyo.mcgproject.common.init;

import moe.gensoukyo.mcgproject.common.item.ItemMCGBoat;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author SQwatermark
 * @date 2020/2/14
 */
public final class ModItem {

    /**
     * 单例模式
     */
    private static ModItem instance;
    public static ModItem instance()
    {
        if(instance == null) instance = new ModItem();
        return instance;
    }

    public static ItemMCGBoat ITEM_MCG_BOAT;

    /**
     * 注册所有物品
     * @param event Item注册事件
     */
    @SuppressWarnings("unused")
    @SubscribeEvent
    public void register(RegistryEvent.Register<Item> event)
    {
        MCGProject.logger.info("MCGProject: registering items");

        ITEM_MCG_BOAT = new ItemMCGBoat();
        event.getRegistry().register(ITEM_MCG_BOAT);
    }

}
