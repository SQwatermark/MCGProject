package moe.gensoukyo.mcgproject.common.block.futuremc;

import moe.gensoukyo.mcgproject.common.creativetab.MCGTabs;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockSmoothStone extends Block {

    public BlockSmoothStone() {
        super(Material.ROCK);
        this.setRegistryName("minecraftfuture", "smoothstone");
        this.setCreativeTab(MCGTabs.NORMAL);
        this.setTranslationKey("minecraftfuture" + "." + "smoothstone");
        this.setSoundType(SoundType.STONE);
        this.setHardness(2F);
    }

}