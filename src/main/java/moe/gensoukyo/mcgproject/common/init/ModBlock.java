package moe.gensoukyo.mcgproject.common.init;

import moe.gensoukyo.mcgproject.common.block.*;
import moe.gensoukyo.mcgproject.common.block.enums.EnumTileColor;
import moe.gensoukyo.mcgproject.common.creativetab.MCGTabs;
import moe.gensoukyo.mcgproject.common.feature.farm.apple.BlockMCGApple;
import moe.gensoukyo.mcgproject.common.feature.backpack.GensoChest;
import moe.gensoukyo.mcgproject.common.feature.farm.stone.BlockRockSpawner;
import moe.gensoukyo.mcgproject.common.feature.lightbulb.BlockLightBulb;
import moe.gensoukyo.mcgproject.common.feature.ranstone.*;
import moe.gensoukyo.mcgproject.common.feature.sticker.BlockSticker;
import moe.gensoukyo.mcgproject.common.item.ItemBlockWithMeta;
import moe.gensoukyo.mcgproject.common.item.ItemMCGBlock;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.*;

public class ModBlock {

    private static ModBlock instance;
    public static ModBlock instance() {
        if(instance == null) instance = new ModBlock();
        return instance;
    }

    public static Block TILE = new BlockTile();
    public static Block NAMAKO = new BlockInteger2(Material.CLAY, "namako", MCGTabs.TOUHOU, SoundType.STONE);
    public static Block FLOWER = new BlockMCGFlower("flower");
    public static Block GAP = new BlockMCG(Material.WOOD, "gap", MCGTabs.FANTASY);
    public static Block BLOCK_CHIREIDEN = new BlockInteger8(Material.GLASS, "glass_chireiden", MCGTabs.EUROPEAN, SoundType.GLASS).setLightLevel(0.67F);
    public static Block MARBLE = new BlockInteger2(Material.ROCK, "marble", MCGTabs.EUROPEAN, SoundType.STONE);
    public static Block WOOL_GLOWING = new BlockInteger16(Material.CLOTH, "wool_glowing", MCGTabs.NORMAL, SoundType.CLOTH).setLightLevel(1.0F);
    public static Block TEST1 = new BlockRotate4(Material.ROCK, "test_block_1", MCGTabs.OLD, SoundType.STONE);
    public static Block TEST2 = new BlockRotate4(Material.ROCK, "test_block_2", MCGTabs.OLD, SoundType.STONE);
    public static Block BLOCK_APPLE = new BlockMCGApple();
    public static Block BLOCK_CHISEL_STONE = new BlockInteger16(Material.ROCK, "chisel_stone", MCGTabs.OLD, SoundType.STONE);
    /**
     * 实例化方块，并将实例化的方块分配到相应链表
     */
    private ModBlock() {

        MCGProject.logger.info("MCGProject: loading blocks");

        //东方
        blocks2.add(NAMAKO);
        blocks2.add(new BlockTatami());
        blocks4.add(new BlockInteger4(Material.CLAY, "plaster", MCGTabs.TOUHOU, SoundType.STONE));
        blocks16.add(new BlockInteger16(Material.WOOD, "plank", MCGTabs.TOUHOU, SoundType.WOOD));
        blocks4.add(new BlockInteger4(Material.WOOD, "blocks_wood", MCGTabs.TOUHOU, SoundType.WOOD));
        blocks5.add(TILE);
        blocks2.add(new BlockWindow(Material.WOOD, "window", MCGTabs.TOUHOU, SoundType.WOOD));
        blocks2.add(new BlockInteger2(Material.WOOD, "lantern", MCGTabs.TOUHOU, SoundType.WOOD).setLightLevel(1.0F));
        blocks8.add(new BlockDecoration2x8(Material.CLOTH, "door_curtain_1", MCGTabs.TOUHOU, SoundType.CLOTH));
        blocks8.add(new BlockDecoration2x8(Material.CLOTH, "door_curtain_2", MCGTabs.TOUHOU, SoundType.CLOTH));
        blocks3.add(new BlockWoodPane("pane_wood"));
        blocks4.add(new BlockDecoration4x4(Material.WOOD, "slipper", MCGTabs.TOUHOU, SoundType.WOOD));
        Block MCGDIRT = new BlockInteger4(Material.GROUND, "dirt", MCGTabs.NATURE, SoundType.GROUND);
        blocks4.add(MCGDIRT);
        blocks1.add(new BlockMCGStairs(MCGDIRT.getDefaultState(), "stairs_dirt_stone", MCGTabs.NATURE, SoundType.GROUND));
        blocks1.add(new BlockMCGSlab(MCGDIRT, 2, "slab_dirt_stone", MCGTabs.NATURE));
        blocks3.add(new BlockInteger3(Material.SAND, "karesansui", MCGTabs.TOUHOU, SoundType.SAND));

        for (EnumTileColor color : EnumTileColor.values()) {
            blocks1.add(new BlockMCGStairs(TILE.getDefaultState(), String.format("stairs_tile_%s", color.getName()), MCGTabs.TOUHOU, SoundType.STONE));
            blocks1.add(new BlockMCGSlab(TILE, color.getMeta(), String.format("slab_tile_%s", color.getName())));
        }

        //自然与农业
        blocks1.add(new BlockMCGLog("log_sakura"));
        blocks5.add(FLOWER);
        blocks2.add(new BlockMCGMushroom("mushroom"));
        blocks1.add(new BlockTranslucent(Material.CLOTH, "cloud", MCGTabs.NATURE, SoundType.CLOTH));
        blocks16.add(new BlockMCGLeaves16("leaves_sakura_glowing").setLightLevel(0.3F));
        blocks8.add(new BlockMCGLeaves8("leaves_mcg"));
        blocks3.add(new BlockBambooOld("bamboo_old"));
        blocks2.add(new BlockInteger2(Material.CAKE, "mooncake", MCGTabs.NATURE, SoundType.SNOW));
        blocks1.add(new BlockMCG(Material.GROUND, "shit", MCGTabs.NATURE, SoundType.GROUND));
        blocks1.add(new BlockMCGLog("straw", MCGTabs.NATURE, SoundType.PLANT));
        blocks1.add(new BlockMCG(Material.LEAVES, "leaves_vine", MCGTabs.NATURE, SoundType.PLANT));
        blocks1.add(new BlockSpecialLeaves("leaves_special"));
        blocks1.add(BLOCK_APPLE);

        //幻想
        blocks1.add(GAP);
        blocks1.add(new GensoChest());
        blocks1.add(new BlockRealm());

        //西方
        blocks8.add(BLOCK_CHIREIDEN);
        blocks16.add(new BlockInteger16(Material.GLASS, "glass_chireiden_a", MCGTabs.EUROPEAN, SoundType.GLASS).setLightLevel(0.67F));
        blocks16.add(new BlockInteger16(Material.GLASS, "glass_chireiden_b", MCGTabs.EUROPEAN, SoundType.GLASS).setLightLevel(0.67F));
        blocks4.add(new BlockRotate4x4(Material.CLOTH, "carpet_red_rotation", MCGTabs.EUROPEAN, SoundType.CLOTH));
        blocks4.add(new BlockRotate4x4(Material.CLOTH, "carpet_blue_rotation", MCGTabs.EUROPEAN, SoundType.CLOTH));
        blocks2.add(new BlockInteger2(Material.CLOTH, "carpet_blank", MCGTabs.EUROPEAN, SoundType.CLOTH));
        blocks4.add(new BlockInteger4(Material.ROCK, "brick_chireiden", MCGTabs.EUROPEAN, SoundType.STONE));
        blocks2.add(new BlockInteger2(Material.CLAY, "wall_marisa", MCGTabs.EUROPEAN, SoundType.STONE));

        //现代
        blocks2.add(MARBLE);
        blocks1.add(new BlockMCGStairs(MARBLE.getDefaultState(), "stairs_marble_black", MCGTabs.EUROPEAN, SoundType.STONE));
        blocks1.add(new BlockMCGStairs(MARBLE.getDefaultState(), "stairs_marble_white", MCGTabs.EUROPEAN, SoundType.STONE));
        blocks1.add(new BlockMCGSlab(MARBLE, 0, "slab_marble_black"));
        blocks1.add(new BlockMCGSlab(MARBLE, 1, "slab_marble_white"));
        blocks1.add(new BlockMCG(Material.ROCK, "road_block", MCGTabs.EUROPEAN, SoundType.STONE));
        blocks1.add(new BlockMCG(Material.CLOTH, "newspaper", MCGTabs.EUROPEAN, SoundType.CLOTH));

        //原版拓展
        blocks16.add(WOOL_GLOWING);
        blocks16.add(new BlockWoolPane("pane_wool"));
        blocks1.add(new BlockTransparentStairs(Blocks.GLASS.getDefaultState(), "stairs_glass", MCGTabs.NORMAL, SoundType.GLASS));
        blocks1.add(new BlockTransparentSlab(Blocks.GLASS, 0, "slab_glass", MCGTabs.NORMAL));
        blocks1.add(new BlockMCGTrapDoor(Material.WOOD, "trapdoor_spruce", MCGTabs.NORMAL));
        blocks1.add(new BlockMCGTrapDoor(Material.WOOD, "trapdoor_dark_oak", MCGTabs.NORMAL));
        blocks1.add(new BlockMCGTrapDoor(Material.WOOD, "trapdoor_jungle", MCGTabs.NORMAL));
        blocks1.add(new BlockMCGTrapDoor(Material.WOOD, "trapdoor_birch", MCGTabs.NORMAL));
        blocks1.add(new BlockMCGTrapDoor(Material.WOOD, "trapdoor_acacia", MCGTabs.NORMAL));
        blocks1.add(new BlockMCG(Material.GROUND, "grass_path_full", MCGTabs.NORMAL, SoundType.GROUND));
        blocks16.add(new BlockTranslucent16(Material.GLASS, "old_glass", MCGTabs.NORMAL, SoundType.GLASS));
        blocks16.add(new BlockTranslucent16(Material.GLASS, "old_glass_bright", MCGTabs.NORMAL, SoundType.GLASS).setLightLevel(1.0F));
        for (EnumDyeColor color : EnumDyeColor.values()) {
            blocks1.add(new BlockMCGStairs(Blocks.WOOL.getDefaultState(), String.format("stairs_wool_%s", color.getName()), MCGTabs.NORMAL, SoundType.CLOTH));
            blocks1.add(new BlockMCGSlab(Blocks.WOOL, color.getMetadata(), String.format("slab_wool_%s", color.getName()), MCGTabs.NORMAL));
        }

        //old
        blocks16.add(new BlockInteger16(Material.WOOD, "fn_log", MCGTabs.OLD, SoundType.WOOD));
        blocks16.add(new BlockInteger16(Material.WOOD, "fn_plank", MCGTabs.OLD, SoundType.WOOD));
        blocks6.add(new BlockInteger6(Material.WOOD, "fn_plank2", MCGTabs.OLD, SoundType.WOOD));
        blocks8.add(new BlockInteger8(Material.ROCK, "fn_bricks", MCGTabs.OLD, SoundType.STONE));
        blocks9.add(new BlockFNFlower("fn_flower").setCreativeTab(MCGTabs.OLD));
        blocks16.add(new BlockMCGLeaves16("fn_leaves").setCreativeTab(MCGTabs.OLD));
        blocks16.add(new BlockMCGLeaves16("fn_leaves2").setCreativeTab(MCGTabs.OLD));
        blocks16.add(new BlockTransparent16(Material.GLASS, "fn_glass", MCGTabs.OLD, SoundType.GLASS));
        blocks12.add(new BlockInteger12(Material.ROCK, "old_stone", MCGTabs.OLD, SoundType.STONE));
        blocks1.add(new BlockMCG(Material.ROCK, "old_brick_marble", MCGTabs.OLD, SoundType.STONE));
        blocks1.add(new BlockMCGLog("old_rubber_wood", MCGTabs.OLD, SoundType.WOOD));
        blocks1.add(new BlockRotate4(Material.ROCK, "old_furnace_on", MCGTabs.OLD, SoundType.STONE));
        blocks1.add(new BlockRotate4(Material.ROCK, "old_furnace_off", MCGTabs.OLD, SoundType.STONE));
        blocks4.add(TEST1);
        blocks4.add(TEST2);
        blocks1.add(new BlockMCGSlab(TEST1, 0, "test_slab_1", MCGTabs.OLD));
        blocks1.add(new BlockMCGSlab(TEST2, 0, "test_slab_2", MCGTabs.OLD));
        blocks1.add(new BlockMCGStairs(TEST1.getDefaultState(), "test_stairs_1", MCGTabs.OLD, SoundType.STONE));
        blocks1.add(new BlockMCGStairs(TEST2.getDefaultState(), "test_stairs_2", MCGTabs.OLD, SoundType.STONE));
        blocks16.add(BLOCK_CHISEL_STONE);
        blocks10.add(new BlockChiselPane("chisel_pane"));
        blocks4.add(new BlockTranslucentChiselGlass(Material.GLASS, "old_glass_2", MCGTabs.OLD, SoundType.GLASS));
        blocks6.add(new BlockInteger6(Material.WOOD, "chisel_wood", MCGTabs.OLD, SoundType.WOOD));
        blocks1.add(new BlockMCG(Material.GLASS, "chisel_glowstone", MCGTabs.OLD, SoundType.GLASS).setLightLevel(1F));
        blocks1.add(new BlockMCGSlab(BLOCK_CHISEL_STONE, 0, "chisel_slab_1", MCGTabs.OLD));
        blocks1.add(new BlockMCGSlab(BLOCK_CHISEL_STONE, 0, "chisel_slab_2", MCGTabs.OLD));
        blocks4.add(new BlockMCGLeaves4("fn_leaves3").setCreativeTab(MCGTabs.OLD));
        blocks2.add(new BlockInteger2(Material.ROCK, "chisel_futura", MCGTabs.OLD, SoundType.STONE).setLightLevel(1F));

        blocks1.add(new BlockRockSpawner());

        //将所有链表的引用合并到ArrayList
        addArrayList();

    }

    /**
     * 注册方块
     */
    @SubscribeEvent
    public void registerBlocks(RegistryEvent.Register<Block> event) {
        MCGProject.logger.info("MCGProject: registering blocks");
        for (LinkedList<Block> i : BLOCKS) {
            event.getRegistry().registerAll(i.toArray(new Block[0]));
        }

        RanstoneLamp.initBlock();
        RanstoneBlock.initBlock();
        RanstoneComparator.initBlock();
        RanstoneRepeater.initBlock();
        RanstoneTorch.initBlock();
        RanstoneWire.initBlock();
        RanstonePiston.Base.initBlock();
        RanstonePiston.Extension.initBlock();
        RanstonePiston.Moving.initBlock();
        event.getRegistry().registerAll(
                RanstoneLamp.BLOCK, RanstoneLamp.BLOCK_LIT, RanstoneLamp.BLOCK_ALWAYS,
                RanstoneBlock.BLOCK,
                RanstoneComparator.BLOCK, RanstoneComparator.BLOCK_N,
                RanstoneRepeater.BLOCK, RanstoneRepeater.BLOCK_N,
                RanstoneTorch.BLOCK, RanstoneTorch.BLOCK_N,
                RanstoneWire.BLOCK,
                RanstonePiston.Base.BLOCK, RanstonePiston.Extension.BLOCK, RanstonePiston.Moving.BLOCK);

        BlockSticker.initBlock();
        event.getRegistry().registerAll(BlockSticker.BLOCK, BlockSticker.BLOCK_LIT);
        BlockLightBulb.initBlock();
        event.getRegistry().registerAll(BlockLightBulb.BLOCK, BlockLightBulb.BLOCK_LIT);
    }

    /**
     * 注册ItemBlock
     */
    @SubscribeEvent
    public void registerItemBlocks(RegistryEvent.Register<Item> event) {
        MCGProject.logger.info("MCGProject: registering ItemBlocks");
        //遍历所有存储Block的链表，实例化ItemBlock并存入LinkedHashMap
        for (int maxMeta = 0; maxMeta < 16; maxMeta++) {
            if (maxMeta == 0) for (Block b : blocks1) {itemBlocks1.put(b, new ItemMCGBlock(b));}
            else for (Block b : BLOCKS.get(maxMeta)) {
                ITEM_BLOCKS.get(maxMeta).put(b, new ItemBlockWithMeta(b));}
        }
        //注册ItemBlock
        for (LinkedHashMap<Block, Item> i : ITEM_BLOCKS) {
            event.getRegistry().registerAll(i.values().toArray(new Item[0]));
        }

        RanstoneLamp.initItem();
        RanstoneBlock.initItem();
        RanstoneComparator.initItem();
        RanstoneRepeater.initItem();
        RanstoneTorch.initItem();
        RanstoneWire.initItem();
        RanstonePiston.Base.initItem();
        event.getRegistry().registerAll(
                RanstoneLamp.ITEM, RanstoneLamp.ITEM_ALWAYS,
                RanstoneBlock.ITEM, RanstoneComparator.ITEM,
                RanstoneRepeater.ITEM, RanstoneTorch.ITEM,
                RanstoneWire.ITEM, RanstonePiston.Base.ITEM);

        BlockSticker.initItem();
        event.getRegistry().registerAll(BlockSticker.ITEM, BlockSticker.ITEM_LIT);
        BlockLightBulb.initItem();
        event.getRegistry().register(BlockLightBulb.ITEM);
    }

    @SubscribeEvent
    public void registerFluids(RegistryEvent.Register<Block> event)
    {
        ModFluid.registerFluids();
        ModFluid.FLUIDS.forEach(fluid ->
        {
            BlockFluidClassic blockFluid = (BlockFluidClassic)
                    new BlockFluidClassic(fluid, Material.WATER)
                            .setRegistryName(MCGProject.ID, fluid.getName());
            event.getRegistry().register(blockFluid);
            CS4_FLUIDS.add(blockFluid);
        });
    }

    /**
     * 注册ItemBlock对应的模型
     */
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void registerItemBlockModels(ModelRegistryEvent event) {
        MCGProject.logger.info("MCGProject: registering ItemBlock Models");
        //遍历所有LinkedHashMap, 注册ItemBlock对应模型
        for (int maxMeta = 0; maxMeta < 16; maxMeta++) {
            for (Item i : ITEM_BLOCKS.get(maxMeta).values()) {
                setLocation(i, maxMeta);
            }
        }
    }

    //模型注册的执行
    private void setLocation(Item i, int maxMeta) {
        if(maxMeta == 0) {
            ModelLoader.setCustomModelResourceLocation(i, 0,
                    new ModelResourceLocation(Objects.requireNonNull(i.getRegistryName()), "inventory")
            );
        }
        else for (int r = 0; r < maxMeta+1; r++) {
                String blockState = Block.getBlockFromItem(i).getStateFromMeta(r).toString();
                String variantIn = blockState.substring(blockState.indexOf("[")+1, blockState.indexOf("]"));
                ModelLoader.setCustomModelResourceLocation(i, r,
                        new ModelResourceLocation(Objects.requireNonNull(i.getRegistryName()), variantIn));
        }
    }

    private static LinkedList<Block> blocks1 = new LinkedList<>();
    private static LinkedList<Block> blocks2 = new LinkedList<>();
    private static LinkedList<Block> blocks3 = new LinkedList<>();
    private static LinkedList<Block> blocks4 = new LinkedList<>();
    private static LinkedList<Block> blocks5 = new LinkedList<>();
    private static LinkedList<Block> blocks6 = new LinkedList<>();
    private static LinkedList<Block> blocks7 = new LinkedList<>();
    private static LinkedList<Block> blocks8 = new LinkedList<>();
    private static LinkedList<Block> blocks9 = new LinkedList<>();
    private static LinkedList<Block> blocks10 = new LinkedList<>();
    private static LinkedList<Block> blocks11 = new LinkedList<>();
    private static LinkedList<Block> blocks12 = new LinkedList<>();
    private static LinkedList<Block> blocks13 = new LinkedList<>();
    private static LinkedList<Block> blocks14 = new LinkedList<>();
    private static LinkedList<Block> blocks15 = new LinkedList<>();
    private static LinkedList<Block> blocks16 = new LinkedList<>();
    private static LinkedHashMap<Block, Item> itemBlocks1 = new LinkedHashMap<>();
    private static LinkedHashMap<Block, Item> itemBlocks2 = new LinkedHashMap<>();
    private static LinkedHashMap<Block, Item> itemBlocks3 = new LinkedHashMap<>();
    private static LinkedHashMap<Block, Item> itemBlocks4 = new LinkedHashMap<>();
    private static LinkedHashMap<Block, Item> itemBlocks5 = new LinkedHashMap<>();
    private static LinkedHashMap<Block, Item> itemBlocks6 = new LinkedHashMap<>();
    private static LinkedHashMap<Block, Item> itemBlocks7 = new LinkedHashMap<>();
    private static LinkedHashMap<Block, Item> itemBlocks8 = new LinkedHashMap<>();
    private static LinkedHashMap<Block, Item> itemBlocks9 = new LinkedHashMap<>();
    private static LinkedHashMap<Block, Item> itemBlocks10 = new LinkedHashMap<>();
    private static LinkedHashMap<Block, Item> itemBlocks11 = new LinkedHashMap<>();
    private static LinkedHashMap<Block, Item> itemBlocks12 = new LinkedHashMap<>();
    private static LinkedHashMap<Block, Item> itemBlocks13 = new LinkedHashMap<>();
    private static LinkedHashMap<Block, Item> itemBlocks14 = new LinkedHashMap<>();
    private static LinkedHashMap<Block, Item> itemBlocks15 = new LinkedHashMap<>();
    private static LinkedHashMap<Block, Item> itemBlocks16 = new LinkedHashMap<>();
    public static final List<BlockFluidClassic> CS4_FLUIDS = new LinkedList<>();
    private static ArrayList<LinkedList<Block>> BLOCKS = new ArrayList<>();
    private static ArrayList<LinkedHashMap<Block, Item>> ITEM_BLOCKS = new ArrayList<>();
    private void addArrayList() {
        BLOCKS.add(blocks1);
        BLOCKS.add(blocks2);
        BLOCKS.add(blocks3);
        BLOCKS.add(blocks4);
        BLOCKS.add(blocks5);
        BLOCKS.add(blocks6);
        BLOCKS.add(blocks7);
        BLOCKS.add(blocks8);
        BLOCKS.add(blocks9);
        BLOCKS.add(blocks10);
        BLOCKS.add(blocks11);
        BLOCKS.add(blocks12);
        BLOCKS.add(blocks13);
        BLOCKS.add(blocks14);
        BLOCKS.add(blocks15);
        BLOCKS.add(blocks16);
        ITEM_BLOCKS.add(itemBlocks1);
        ITEM_BLOCKS.add(itemBlocks2);
        ITEM_BLOCKS.add(itemBlocks3);
        ITEM_BLOCKS.add(itemBlocks4);
        ITEM_BLOCKS.add(itemBlocks5);
        ITEM_BLOCKS.add(itemBlocks6);
        ITEM_BLOCKS.add(itemBlocks7);
        ITEM_BLOCKS.add(itemBlocks8);
        ITEM_BLOCKS.add(itemBlocks9);
        ITEM_BLOCKS.add(itemBlocks10);
        ITEM_BLOCKS.add(itemBlocks11);
        ITEM_BLOCKS.add(itemBlocks12);
        ITEM_BLOCKS.add(itemBlocks13);
        ITEM_BLOCKS.add(itemBlocks14);
        ITEM_BLOCKS.add(itemBlocks15);
        ITEM_BLOCKS.add(itemBlocks16);
    }

}