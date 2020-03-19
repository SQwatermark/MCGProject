package moe.gensoukyo.mcgproject.common.block;

import moe.gensoukyo.mcgproject.common.creativetab.MCGTabs;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.block.BlockLog;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;

import javax.annotation.Nonnull;

public class BlockMCGLog extends BlockLog {

    public BlockMCGLog(String registryName)
    {
        super();
        this.setRegistryName(registryName);
        this.setTranslationKey(MCGProject.ID + "." + registryName);
        this.setCreativeTab(MCGTabs.NATURE);
        this.blockState.getBaseState().withProperty(LOG_AXIS, BlockLog.EnumAxis.Y);
    }

    public BlockMCGLog(String registryName, CreativeTabs tab, SoundType soundType)
    {
        super();
        this.setRegistryName(registryName);
        this.setTranslationKey(MCGProject.ID + "." + registryName);
        this.setCreativeTab(tab);
        this.blockState.getBaseState().withProperty(LOG_AXIS, BlockLog.EnumAxis.Y);
        this.setSoundType(soundType);
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, LOG_AXIS);
    }

    @Nonnull
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        IBlockState iblockstate = this.getDefaultState();

        switch (meta & 3)
        {
            case 0:
                iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.Y);
                break;
            case 1:
                iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.X);
                break;
            case 2:
                iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.Z);
                break;
            default:
                iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.NONE);
        }

        return iblockstate;
    }

    public int getMetaFromState(IBlockState state)
    {
        int i = 0;

        switch (state.getValue(LOG_AXIS))
        {
            case X:
                i |= 1;
                break;
            case Z:
                i |= 2;
                break;
            case NONE:
                i |= 3;
        }

        return i;
    }

}