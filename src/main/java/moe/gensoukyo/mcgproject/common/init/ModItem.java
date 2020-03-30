package moe.gensoukyo.mcgproject.common.init;

import moe.gensoukyo.mcgproject.common.item.*;
import moe.gensoukyo.mcgproject.common.item.ItemGenBigOak;
import moe.gensoukyo.mcgproject.common.item.ItemMCGBoat;
import moe.gensoukyo.mcgproject.common.item.ItemMetaChanger;
import moe.gensoukyo.mcgproject.common.item.ItemRACBoat;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author SQwatermark
 * @date 2020/2/14
 */
public class ModItem {

    private static ModItem instance;
    public static ModItem instance()
    {
        if(instance == null) instance = new ModItem();
        return instance;
    }

    public static ItemMCGBoat ITEM_MCG_BOAT;
    public static ItemRACBoat ITEM_RAC_BOAT;
    public static ItemMetaChanger ITEM_META_CHANGER;
    public static ItemGenBigOak ITEM_GEN_BIG_OAK;
    public static ItemKaginawa ITEM_KAGINAWA;
    public static ItemBlockInfo ITEM_BLOCK_INFO;

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
        ITEM_RAC_BOAT = new ItemRACBoat();
        ITEM_META_CHANGER = new ItemMetaChanger();
        ITEM_GEN_BIG_OAK = new ItemGenBigOak();
        ITEM_KAGINAWA = new ItemKaginawa();
        ITEM_BLOCK_INFO = new ItemBlockInfo();
        event.getRegistry().register(ITEM_MCG_BOAT);
        event.getRegistry().register(ITEM_RAC_BOAT);
        event.getRegistry().register(ITEM_META_CHANGER);
        event.getRegistry().register(ITEM_GEN_BIG_OAK);
        event.getRegistry().register(ITEM_KAGINAWA);
        event.getRegistry().register(ITEM_BLOCK_INFO);
    }
}
