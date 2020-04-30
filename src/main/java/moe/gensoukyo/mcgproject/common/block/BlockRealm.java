package moe.gensoukyo.mcgproject.common.block;

import moe.gensoukyo.mcgproject.common.creativetab.MCGTabs;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.block.BlockPane;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class BlockRealm extends BlockPane {


    public BlockRealm() {
        super(Material.GLASS, false);
        this.setCreativeTab(MCGTabs.FANTASY);
        this.setDefaultState(this.blockState.getBaseState().withProperty(NORTH, Boolean.FALSE).withProperty(EAST, Boolean.TRUE)
                .withProperty(SOUTH, Boolean.FALSE).withProperty(WEST, Boolean.TRUE));
        this.setSoundType(SoundType.GLASS);
        this.setRegistryName("realm");
        this.setTranslationKey(MCGProject.ID + "." + "realm");
        this.setLightLevel(1F);
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState)
    {
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return FULL_BLOCK_AABB;
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return NULL_AABB;
    }

    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        if (!canBeConnectedTo(worldIn, pos, EnumFacing.NORTH) && !canBeConnectedTo(worldIn, pos, EnumFacing.SOUTH)
                && !canBeConnectedTo(worldIn, pos, EnumFacing.WEST) && !canBeConnectedTo(worldIn, pos, EnumFacing.EAST))
            return state.withProperty(NORTH, true)
                    .withProperty(SOUTH, true)
                    .withProperty(WEST,  true)
                    .withProperty(EAST,  true);
        return state.withProperty(NORTH, canPaneConnectTo(worldIn, pos, EnumFacing.NORTH))
                .withProperty(SOUTH, canPaneConnectTo(worldIn, pos, EnumFacing.SOUTH))
                .withProperty(WEST,  canPaneConnectTo(worldIn, pos, EnumFacing.WEST))
                .withProperty(EAST,  canPaneConnectTo(worldIn, pos, EnumFacing.EAST));
    }

}
