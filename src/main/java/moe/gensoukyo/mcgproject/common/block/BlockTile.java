package moe.gensoukyo.mcgproject.common.block;

import moe.gensoukyo.mcgproject.common.block.BlockMCG;
import moe.gensoukyo.mcgproject.common.block.enums.EnumTileColor;
import moe.gensoukyo.mcgproject.common.creativetab.MCGTabs;
import net.minecraft.block.material.Material;
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

import javax.annotation.Nonnull;

/**
 * @author SQwatermark
 * @date 2020/2/16
 */
public class BlockTile extends BlockMCG {

    public BlockTile() {
        super(Material.ROCK, "tile", MCGTabs.TOUHOU);
    }

    public static final PropertyEnum<EnumTileColor> COLOR = PropertyEnum.create("color", EnumTileColor.class);

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState() {return new BlockStateContainer(this, COLOR);}

    @Nonnull
    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return getDefaultState().withProperty(COLOR, EnumTileColor.values()[meta]);
    }

    @Nonnull
    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return getDefaultState().withProperty(COLOR, EnumTileColor.values()[placer.getHeldItemMainhand().getMetadata()]);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(COLOR).ordinal();
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items)
    {
        for (int meta = 0; meta < EnumTileColor.values().length; meta++) {
            items.add(new ItemStack(this, 1, meta));
        }
    }

    @Nonnull
    @Override
    public ItemStack getPickBlock(@NotNull IBlockState state, RayTraceResult target, @NotNull World world, @NotNull BlockPos pos, EntityPlayer player)
    {
        return new ItemStack(Item.getItemFromBlock(this), 1, this.getMetaFromState(state));
    }


}
