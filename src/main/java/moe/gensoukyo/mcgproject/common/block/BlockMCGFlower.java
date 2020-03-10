package moe.gensoukyo.mcgproject.common.block;

import moe.gensoukyo.mcgproject.common.block.enums.EnumFlowerVariant;
import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyEnum;
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

public class BlockMCGFlower extends BlockMCGBush {

    public BlockMCGFlower(String registryName) {
        super(registryName);
    }

    public static final PropertyEnum<EnumFlowerVariant> VARIANT = PropertyEnum.create("variant", EnumFlowerVariant.class);

    @Override
    protected BlockStateContainer createBlockState() {return new BlockStateContainer(this, VARIANT);}

    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return getDefaultState().withProperty(VARIANT, EnumFlowerVariant.values()[meta]);
    }

    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return getDefaultState().withProperty(VARIANT, EnumFlowerVariant.values()[placer.getHeldItemMainhand().getMetadata()]);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(VARIANT).ordinal();
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items)
    {
        for (int meta = 0; meta < EnumFlowerVariant.values().length; meta++) {
            items.add(new ItemStack(this, 1, meta));
        }
    }

    @NotNull
    @Override
    public ItemStack getPickBlock(@NotNull IBlockState state, RayTraceResult target, @NotNull World world, @NotNull BlockPos pos, EntityPlayer player)
    {
        return new ItemStack(Item.getItemFromBlock(this), 1, this.getMetaFromState(state));
    }

    public Block.EnumOffsetType getOffsetType()
    {
        return Block.EnumOffsetType.XZ;
    }

}
