package moe.gensoukyo.mcgproject.common.init;

import moe.gensoukyo.mcgproject.common.block.futuremc.*;
import moe.gensoukyo.mcgproject.common.item.ItemMCGBlock;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Objects;

public class FMBlock {

    private static FMBlock instance;
    public static FMBlock instance() {
        if(instance == null) instance = new FMBlock();
        return instance;
    }

    private FMBlock() {
        fmBlocks.add(new BlockFMLog("stripped_acacia_log"));
        fmBlocks.add(new BlockFMLog("stripped_oak_log"));
        fmBlocks.add(new BlockFMLog("stripped_jungle_log"));
        fmBlocks.add(new BlockFMLog("stripped_dark_oak_log"));
        fmBlocks.add(new BlockFMLog("stripped_spruce_log"));
        fmBlocks.add(new BlockFMLog("stripped_birch_log"));
        fmBlocks.add(new BlockBarrel());
        fmBlocks.add(new BlockCampfire());
        fmBlocks.add(new BlockComposter());
        fmBlocks.add(new BlockSmoothStone());
        fmBlocks.add(new BlockStoneCutter());
        fmBlocks.add(new BlockBamboo());
        fmBlocks.add(new BlockLantern());
    }

    @SubscribeEvent
    public void registerBlocks(RegistryEvent.Register<Block> event) {
        MCGProject.logger.info("MCGProjectFM: registering blocks");
        event.getRegistry().registerAll(fmBlocks.toArray(new Block[0]));
    }

    @SubscribeEvent
    public void registerItemBlocks(RegistryEvent.Register<Item> event) {
        MCGProject.logger.info("MCGProjectFM: registering ItemBlocks");
        for (Block b : fmBlocks) {fmItemBlocks.put(b, new ItemMCGBlock(b));}
        event.getRegistry().registerAll(fmItemBlocks.values().toArray(new Item[0]));
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void registerItemBlockModels(ModelRegistryEvent event) {
        MCGProject.logger.info("MCGProjectFM: registering ItemBlock Models");
        for (Item i : fmItemBlocks.values()) {
            setLocation(i);
        }
    }

    private void setLocation(Item i) {
        ModelLoader.setCustomModelResourceLocation(i, 0,
                new ModelResourceLocation(Objects.requireNonNull(i.getRegistryName()), "inventory")
        );
    }

    private static LinkedList<Block> fmBlocks = new LinkedList<>();
    private static LinkedHashMap<Block, Item> fmItemBlocks = new LinkedHashMap<>();

}
