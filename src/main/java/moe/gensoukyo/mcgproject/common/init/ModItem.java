package moe.gensoukyo.mcgproject.common.init;

import moe.gensoukyo.mcgproject.common.feature.kaginawa.ItemKaginawa;
import moe.gensoukyo.mcgproject.common.feature.farm.stone.ItemLittleRock;
import moe.gensoukyo.mcgproject.common.feature.musicplayer.ItemMusicPlayer;
import moe.gensoukyo.mcgproject.common.item.*;
import moe.gensoukyo.mcgproject.common.item.cart.*;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

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
    public static ItemMCGHoe ITEM_MCG_HOE;

    public static ItemGRM3A ITEM_GRM_3A;
    public static ItemGRM3AF ITEM_GRM_3AF;
    public static ItemGRM3B ITEM_GRM_3B;
    public static ItemGRM3BF ITEM_GRM_3BF;
    public static ItemGRW4 ITEM_GRW_4;
    public static ItemGRW4M ITEM_GRW_4M;
    public static ItemGRC2 ITEM_GRC_2;
    public static ItemGRC2M ITEM_GRC_2M;
    public static ItemGRH2 ITEM_GRH_2;
    public static ItemGRH2M ITEM_GRH_2M;

    public static List<ItemMCGWeapon> ITEM_MCG_WEAPONS;

    /**
     * 八卦炉
     */
    public static ItemSepId HAKKERO;
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
        ITEM_MCG_HOE = new ItemMCGHoe();

        ITEM_GRM_3A = new ItemGRM3A();
        ITEM_GRM_3AF = new ItemGRM3AF();
        ITEM_GRM_3B = new ItemGRM3B();
        ITEM_GRM_3BF = new ItemGRM3BF();
        ITEM_GRW_4 = new ItemGRW4();
        ITEM_GRW_4M = new ItemGRW4M();
        ITEM_GRC_2 = new ItemGRC2();
        ITEM_GRC_2M = new ItemGRC2M();
        ITEM_GRH_2 = new ItemGRH2();
        ITEM_GRH_2M = new ItemGRH2M();

        event.getRegistry().registerAll(ITEM_MCG_BOAT, ITEM_RAC_BOAT);

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

        event.getRegistry().registerAll(ITEM_GRM_3A, ITEM_GRM_3AF, ITEM_GRM_3B, ITEM_GRM_3BF);
        event.getRegistry().registerAll(ITEM_GRW_4, ITEM_GRW_4M);
        event.getRegistry().registerAll(ITEM_GRC_2, ITEM_GRC_2M, ITEM_GRH_2, ITEM_GRH_2M);

        event.getRegistry().register(ITEM_MCG_HOE);

        event.getRegistry().registerAll(
                (ITEM_MCG_WEAPONS = ItemMCGWeapon.create(101)).toArray(new Item[0]));
        event.getRegistry().register(HAKKERO = new ItemSepId("hakkero"));
    }
}
