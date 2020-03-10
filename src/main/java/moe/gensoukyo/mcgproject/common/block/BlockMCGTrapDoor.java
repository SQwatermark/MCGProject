package moe.gensoukyo.mcgproject.common.block;

import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockMCGTrapDoor extends BlockTrapDoor {

    public BlockMCGTrapDoor(Material materialIn, String registryName, CreativeTabs tabs) {
        super(materialIn);
        this.setCreativeTab(tabs);
        this.setRegistryName(registryName);
        this.setTranslationKey(MCGProject.ID + "." + registryName);
        this.setSoundType(SoundType.WOOD);
    }

}
