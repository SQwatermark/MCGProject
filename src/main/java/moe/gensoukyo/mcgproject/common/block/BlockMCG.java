package moe.gensoukyo.mcgproject.common.block;

import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockMCG extends Block {

    public BlockMCG(Material materialIn, String registryName, CreativeTabs tab)
    {
        super(materialIn);
        this.setRegistryName(registryName);
        this.setCreativeTab(tab);
        this.setTranslationKey(MCGProject.ID + "." + registryName);
        this.setHardness(2F);
    }

    public BlockMCG(Material materialIn, String registryName, CreativeTabs tab, SoundType soundType)
    {
        super(materialIn);
        this.setRegistryName(registryName);
        this.setCreativeTab(tab);
        this.setTranslationKey(MCGProject.ID + "." + registryName);
        this.setHardness(2F);
        this.setSoundType(soundType);
    }

}