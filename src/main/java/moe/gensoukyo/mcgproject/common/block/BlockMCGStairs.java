package moe.gensoukyo.mcgproject.common.block;

import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;

/**
 * @author SQwatermark
 * @date 2020/2/16
 */
public class BlockMCGStairs extends BlockStairs {

    public BlockMCGStairs(IBlockState blockState, String registryName, CreativeTabs tab, SoundType soundType)
    {
        super(blockState);
        this.setTranslationKey(MCGProject.ID + "." + registryName);
        this.setRegistryName(registryName);
        this.setHardness(2F);
        this.setCreativeTab(tab);
        this.setSoundType(soundType);
        this.useNeighborBrightness = true;
    }

}
