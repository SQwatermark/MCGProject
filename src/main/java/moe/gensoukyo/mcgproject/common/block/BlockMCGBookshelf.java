package moe.gensoukyo.mcgproject.common.block;

import moe.gensoukyo.mcgproject.common.creativetab.MCGTabs;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockMCGBookshelf extends BlockMCG {

    public BlockMCGBookshelf(String registryName) {
        super(Material.WOOD, registryName, MCGTabs.FANTASY, SoundType.WOOD);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        return false;
    }
}
