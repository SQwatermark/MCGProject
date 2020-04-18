package moe.gensoukyo.mcgproject.common.feature.futuremc;

import moe.gensoukyo.mcgproject.common.creativetab.MCGTabs;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BlockLantern extends Block {

    private static final PropertyBool HANGING = PropertyBool.create("hanging");

    public static final AxisAlignedBB SITTING_AABB = new AxisAlignedBB(0.3, 0.0, 0.3, 0.7, 0.6, 0.7);
    public static final AxisAlignedBB HANGING_AABB = new AxisAlignedBB(0.3, 0.05, 0.3, 0.7, 0.65, 0.7);

    public BlockLantern() {
        super(Material.WOOD);
        this.setRegistryName("minecraftfuture", "lantern");
        this.setCreativeTab(MCGTabs.NORMAL);
        this.setTranslationKey("minecraftfuture" + "." + "lantern");
        this.setSoundType(SoundType.GLASS);
        this.setLightLevel(1F);
        this.setHardness(2F);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, HANGING);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(HANGING) ? 1 : 0;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState()
                .withProperty(HANGING, meta == 1);
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return getDefaultState()
                .withProperty(HANGING, facing == EnumFacing.DOWN);
    }

    @Nonnull
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return state.getValue(HANGING) ?  HANGING_AABB : SITTING_AABB;
    }

    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, @Nonnull IBlockAccess worldIn, @Nonnull BlockPos pos) {
        return blockState.getValue(HANGING) ?  HANGING_AABB : SITTING_AABB;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    @Nonnull
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    @Nonnull
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

}
