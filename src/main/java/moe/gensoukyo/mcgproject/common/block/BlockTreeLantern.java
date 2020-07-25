package moe.gensoukyo.mcgproject.common.block;

import moe.gensoukyo.mcgproject.common.creativetab.MCGTabs;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.jetbrains.annotations.NotNull;

public class BlockTreeLantern extends BlockMCGCrossed {

    private static final PropertyBool STEM = PropertyBool.create("stem");

    public BlockTreeLantern() {
        super(Material.PLANTS, "tree_lantern", MCGTabs.NATURE, SoundType.PLANT);
        this.setLightLevel(0.5F);
        this.setDefaultState(this.blockState.getBaseState().withProperty(STEM, false));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, STEM);
    }

    @Override
    public int getMetaFromState(@NotNull IBlockState state) {
        return 0;
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return state.withProperty(STEM, (worldIn.getBlockState(pos.down()).getBlock() == this));
    }

}
