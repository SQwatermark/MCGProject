package moe.gensoukyo.mcgproject.common.block;

import moe.gensoukyo.mcgproject.common.block.BlockInteger16;
import moe.gensoukyo.mcgproject.common.creativetab.MCGTabs;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockWoolGlow extends BlockInteger16 {

    public BlockWoolGlow() {
        super(Material.CLOTH, "wool_glowing", MCGTabs.NORMAL, SoundType.CLOTH);
        this.setLightLevel(15.0F);
    }
}
