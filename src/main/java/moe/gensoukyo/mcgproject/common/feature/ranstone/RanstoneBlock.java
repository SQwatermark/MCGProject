package moe.gensoukyo.mcgproject.common.feature.ranstone;

import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.block.BlockCompressedPowered;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class RanstoneBlock extends BlockCompressedPowered {

    public static RanstoneBlock BLOCK;
    public static Item ITEM;

    public static void initBlock() { BLOCK = new RanstoneBlock(); }
    public static void initItem() { ITEM = new ItemBlock(BLOCK).setRegistryName(MCGProject.ID, "ranstone_block"); }

    public RanstoneBlock() {
        super(Material.IRON, MapColor.TNT);
        setRegistryName(MCGProject.ID, "ranstone_block");
        setHardness(5.0F);
        setResistance(10.0F);
        setSoundType(SoundType.METAL);
        setTranslationKey(MCGProject.ID + "." + "blockRanstone");
        setCreativeTab(CreativeTabs.REDSTONE);
    }

}
