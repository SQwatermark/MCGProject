package moe.gensoukyo.mcgproject.common.block;

import moe.gensoukyo.mcgproject.common.block.BlockMCG;
import moe.gensoukyo.mcgproject.common.creativetab.MCGTabs;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
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

/**
 * @author SQwatermark
 * 定义了榻榻米方块
 */
public class BlockTatami extends BlockMCG
{

    public static final PropertyBool WITHSIDE = PropertyBool.create("withside");
    public static final PropertyBool EASTWEST = PropertyBool.create("eastwest");

    public BlockTatami()
    {
        super(Material.WOOD, "tatami", MCGTabs.TOUHOU);
        this.setSoundType(SoundType.SAND);
        this.setDefaultState(this.blockState.getBaseState().withProperty(WITHSIDE, true).withProperty(EASTWEST, true));
        this.setHardness(0.5F);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, EASTWEST, WITHSIDE);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        int i = 0;
        if(!state.getValue(WITHSIDE)){i += 1;}
        if(state.getValue(EASTWEST)){i += 2;}
        return i;
    }

    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState()
                .withProperty(WITHSIDE, (meta & 1) == 0)
                .withProperty(EASTWEST, (meta & 2) != 0);
    }

    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return getDefaultState()
                .withProperty(EASTWEST, placer.getHorizontalFacing().getAxis() == EnumFacing.Axis.X)
                .withProperty(WITHSIDE, placer.getHeldItemMainhand().getMetadata() == 0);
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items)
    {
        items.add(new ItemStack(this, 1, 0));
        items.add(new ItemStack(this, 1, 1));
    }

    @Override
    public ItemStack getPickBlock(@NotNull IBlockState state, RayTraceResult target, @NotNull World world, @NotNull BlockPos pos, EntityPlayer player)
    {
        return new ItemStack(Item.getItemFromBlock(this), 1, this.getMetaFromState(state) & 1);
    }

}