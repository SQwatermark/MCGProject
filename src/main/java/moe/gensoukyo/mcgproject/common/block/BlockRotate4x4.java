package moe.gensoukyo.mcgproject.common.block;

import moe.gensoukyo.mcgproject.common.block.BlockMCG;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class BlockRotate4x4 extends BlockMCG {

    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static PropertyInteger TYPE = PropertyInteger.create("type", 0, 3);

    public BlockRotate4x4(Material materialIn, String registryName, CreativeTabs tab, SoundType soundType)
    {
        super(materialIn, registryName, tab, soundType);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING, TYPE);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        int i = 0;
        i += state.getValue(TYPE);
        i += 4 * state.getValue(FACING).getHorizontalIndex();
        return i;
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState()
                .withProperty(TYPE, meta & 3)
                .withProperty(FACING, EnumFacing.byHorizontalIndex(meta >> 2));
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return getDefaultState()
                .withProperty(FACING, placer.getHorizontalFacing())
                .withProperty(TYPE, placer.getHeldItemMainhand().getMetadata() & 3);
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items)
    {
        items.add(new ItemStack(this, 1, 0));
        items.add(new ItemStack(this, 1, 1));
        items.add(new ItemStack(this, 1, 2));
        items.add(new ItemStack(this, 1, 3));
    }

    @Override
    public ItemStack getPickBlock(@NotNull IBlockState state, RayTraceResult target, @NotNull World world, @NotNull BlockPos pos, EntityPlayer player)
    {
        return new ItemStack(Item.getItemFromBlock(this), 1, this.getMetaFromState(state) & 3);
    }

}
