package moe.gensoukyo.mcgproject.common.block;

import moe.gensoukyo.mcgproject.common.block.BlockMCG;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
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

public class BlockRotate2x8 extends BlockMCG {

    public static final PropertyBool EASTWEST = PropertyBool.create("eastwest");
    public static PropertyInteger TYPE = PropertyInteger.create("type", 0, 7);

    public BlockRotate2x8(Material materialIn, String registryName, CreativeTabs tab, SoundType soundType)
    {
        super(materialIn, registryName, tab, soundType);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, EASTWEST, TYPE);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        int i = 0;
        i += state.getValue(TYPE);
        i += 8 * (state.getValue(EASTWEST)?0:1);
        return i;
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState()
                .withProperty(TYPE, meta & 7)
                .withProperty(EASTWEST, ((meta & 8) == 0));
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return getDefaultState()
                .withProperty(EASTWEST, placer.getHorizontalFacing().getAxis() != EnumFacing.Axis.X)
                .withProperty(TYPE, placer.getHeldItemMainhand().getMetadata() & 7);
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items)
    {
        for (int i = 0; i < 8; i++) {
            items.add(new ItemStack(this, 1, i));
        }
    }

    @Override
    public ItemStack getPickBlock(@NotNull IBlockState state, RayTraceResult target, @NotNull World world, @NotNull BlockPos pos, EntityPlayer player)
    {
        return new ItemStack(Item.getItemFromBlock(this), 1, this.getMetaFromState(state) & 7);
    }

}