package moe.gensoukyo.mcgproject.common.item;

import moe.gensoukyo.mcgproject.common.creativetab.MCGTabs;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenBigTree;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class ItemGenBigOak extends Item {

    public ItemGenBigOak() {
        this.setMaxStackSize(1);
        this.setCreativeTab(MCGTabs.FANTASY);
        this.setRegistryName(MCGProject.ID, "generator_big_oak");
        this.setTranslationKey(MCGProject.ID + "." + "generator_big_oak");
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        Random random = new Random();
        generateTree(worldIn, pos.add(0, 1, 0), random);
        return EnumActionResult.SUCCESS;
    }

    public void generateTree(World worldIn, BlockPos pos, Random rand)
    {
        if (!net.minecraftforge.event.terraingen.TerrainGen.saplingGrowTree(worldIn, rand, pos)) return;
        WorldGenerator worldgenerator = new WorldGenBigTree(true);
        worldgenerator.generate(worldIn, rand, pos);
    }

}
