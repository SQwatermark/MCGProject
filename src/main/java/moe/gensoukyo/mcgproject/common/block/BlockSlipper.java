package moe.gensoukyo.mcgproject.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockSlipper extends BlockDecoration4x4 {
    public BlockSlipper(Material materialIn, String registryName, CreativeTabs tab, SoundType soundType) {
        super(materialIn, registryName, tab, soundType);
    }
    public Block.EnumOffsetType getOffsetType()
    {
        return EnumOffsetType.XZ;
    }
}
