package moe.gensoukyo.mcgproject.common.feature.customnpcs;

import moe.gensoukyo.mcgproject.common.block.BlockMCG;
import moe.gensoukyo.mcgproject.common.creativetab.MCGTabs;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockNPCSpawnerConsole extends BlockMCG {

    public BlockNPCSpawnerConsole() {
        super(Material.ROCK, "npc_spawner_console", MCGTabs.FANTASY, SoundType.STONE);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        //Minecraft.getMinecraft().displayGuiScreen(new GuiAdminNPCSpawner());
        return worldIn.isRemote;
    }

}
