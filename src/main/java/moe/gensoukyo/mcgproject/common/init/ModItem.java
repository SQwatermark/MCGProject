package moe.gensoukyo.mcgproject.common.init;

import moe.gensoukyo.mcgproject.common.feature.kaginawa.ItemKaginawa;
import moe.gensoukyo.mcgproject.common.feature.farm.stone.ItemLittleRock;
import moe.gensoukyo.mcgproject.common.feature.musicplayer.ItemMusicPlayer;
import moe.gensoukyo.mcgproject.common.item.*;
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
    public static ModItem instance() {
        if(instance == null) instance = new ModItem();
        return instance;
    }

    public static ItemMCGBoat ITEM_MCG_BOAT;
    public static ItemRACBoat ITEM_RAC_BOAT;
    public static ItemMetaChanger ITEM_META_CHANGER;
    public static ItemGenBigOak ITEM_GEN_BIG_OAK;
    public static ItemKaginawa ITEM_KAGINAWA;
    public static ItemBlockInfo ITEM_BLOCK_INFO;
    public static ItemMCGFood ITEM_MCG_FOOD;
    public static ItemMCGDrink ITEM_MCG_DRINK;
    public static ItemMCGProp ITEM_MCG_PROP;
    public static ItemMusicPlayer ITEM_MUSIC_PLAYER;
    public static ItemMCGBannerPattern ITEM_MCG_BANNER_PATTERN;
    public static ItemLittleRock ITEM_LITTLE_STONE;
    public static ItemRouKanKen ITEM_ROU_KAN_KEN;

    /**
     * 注册所有物品
     * @param event Item注册事件
     */
    @SubscribeEvent
    public void register(RegistryEvent.Register<Item> event) {
        MCGProject.logger.info("MCGProject: registering items");

        ITEM_MCG_BOAT = new ItemMCGBoat();
        ITEM_RAC_BOAT = new ItemRACBoat();
        ITEM_META_CHANGER = new ItemMetaChanger();
        ITEM_GEN_BIG_OAK = new ItemGenBigOak();
        ITEM_KAGINAWA = new ItemKaginawa();
        ITEM_BLOCK_INFO = new ItemBlockInfo();
        ITEM_MCG_FOOD = new ItemMCGFood();
        ITEM_MCG_DRINK = new ItemMCGDrink();
        ITEM_MUSIC_PLAYER = new ItemMusicPlayer();
        ITEM_MCG_PROP = new ItemMCGProp();
        ITEM_MCG_BANNER_PATTERN = new ItemMCGBannerPattern();
        ITEM_LITTLE_STONE = new ItemLittleRock();
        ITEM_ROU_KAN_KEN = new ItemRouKanKen();

        event.getRegistry().register(ITEM_MCG_BOAT);
        event.getRegistry().register(ITEM_RAC_BOAT);
        event.getRegistry().register(ITEM_META_CHANGER);
        event.getRegistry().register(ITEM_GEN_BIG_OAK);
        event.getRegistry().register(ITEM_KAGINAWA);
        event.getRegistry().register(ITEM_BLOCK_INFO);
        event.getRegistry().register(ITEM_MCG_FOOD);
        event.getRegistry().register(ITEM_MCG_DRINK);
        event.getRegistry().register(ITEM_MUSIC_PLAYER);
        event.getRegistry().register(ITEM_MCG_PROP);
        event.getRegistry().register(ITEM_MCG_BANNER_PATTERN);
        event.getRegistry().register(ITEM_LITTLE_STONE);
        event.getRegistry().register(ITEM_ROU_KAN_KEN);
    }
}
