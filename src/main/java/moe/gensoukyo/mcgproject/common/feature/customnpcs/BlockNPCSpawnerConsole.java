package moe.gensoukyo.mcgproject.common.feature.customnpcs;

import moe.gensoukyo.mcgproject.common.block.BlockMCG;
import moe.gensoukyo.mcgproject.common.creativetab.MCGTabs;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockNPCSpawnerConsole extends BlockMCG {

    //TODO
    public BlockNPCSpawnerConsole() {
        super(Material.ROCK, "npc_spawner_console", MCGTabs.FANTASY, SoundType.STONE);
    }

}
