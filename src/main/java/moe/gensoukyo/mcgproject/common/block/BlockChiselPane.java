package moe.gensoukyo.mcgproject.common.block;

import moe.gensoukyo.mcgproject.common.creativetab.MCGTabs;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.block.BlockPane;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

public class BlockChiselPane extends BlockPane {

    public BlockChiselPane(String registryName) {
        super(Material.GLASS, false);
        this.setDefaultState(this.blockState.getBaseState().withProperty(NORTH, Boolean.FALSE).withProperty(EAST, Boolean.TRUE).withProperty(SOUTH, Boolean.FALSE).withProperty(WEST, Boolean.TRUE).withProperty(META, 0));
        this.setCreativeTab(MCGTabs.OLD);
        this.setSoundType(SoundType.GLASS);
        this.setRegistryName(registryName);
        this.setTranslationKey(MCGProject.ID + "." + registryName);
    }

    public static PropertyInteger META = PropertyInteger.create("meta", 0, 9);

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, NORTH, EAST, WEST, SOUTH, META);
    }

    @Override
    public int getMetaFromState(@NotNull IBlockState state)
    {
        return state.getValue(META);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        if(meta <= 9 && meta >= 0) return this.getDefaultState()
                .withProperty(META, meta);
        else return this.getDefaultState()
                .withProperty(META, 0);
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getStateFromMeta(placer.getHeldItemMainhand().getMetadata());
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items)
    {
        for (int i :  META.getAllowedValues()) {
            items.add(new ItemStack(this, 1, i));
        }
    }

    @Override
    public ItemStack getPickBlock(@NotNull IBlockState state, RayTraceResult target, @NotNull World world, @NotNull BlockPos pos, EntityPlayer player)
    {
        return new ItemStack(Item.getItemFromBlock(this), 1, this.getMetaFromState(state));
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }

}
