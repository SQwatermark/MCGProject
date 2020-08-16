package moe.gensoukyo.mcgproject.common.block.futuremc;

import moe.gensoukyo.mcgproject.common.creativetab.MCGTabs;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockBarrel extends Block {

    public static final PropertyDirection FACING = PropertyDirection.create("facing");

    public BlockBarrel() {
        super(Material.WOOD);
        this.setRegistryName("minecraftfuture", "barrel");
        this.setCreativeTab(MCGTabs.NORMAL);
        this.setTranslationKey("minecraftfuture" + "." + "barrel");
        this.setSoundType(SoundType.WOOD);
        this.setHardness(2F);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(FACING).getIndex();
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState()
                .withProperty(FACING, EnumFacing.byIndex(meta));
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return getDefaultState()
                .withProperty(FACING, facing);
    }

}
