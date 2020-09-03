package moe.gensoukyo.mcgproject.common.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;

public class BlockWindow extends BlockInteger2 {

    public BlockWindow(Material materialIn, String registryName, CreativeTabs tab, SoundType soundType) {
        super(materialIn, registryName, tab, soundType);
        this.setLightOpacity(1);
    }

    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

}
