package moe.gensoukyo.mcgproject.common.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockSpecialLeaves extends BlockMCGLeaves {

    public BlockSpecialLeaves(String registryName) {
        super(registryName);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        int par2 = pos.getX();
        int par3 = pos.getY();
        int par4 = pos.getZ();
        float dis = 1.0F;
        double var7 = (float)par2 + dis / (float)worldIn.rand.nextInt(11);
        double var9 = (float)par3 + dis / (float)worldIn.rand.nextInt(11);
        double var11 = (float)par4 + dis / (float)worldIn.rand.nextInt(11);
        //暂时只有火焰树叶
        worldIn.spawnParticle(EnumParticleTypes.FLAME, var7, var9, var11, 0.0D, 0.0D, 0.0D);
        worldIn.spawnParticle(EnumParticleTypes.FLAME, var7, var9, var11, 0.0D, 0.0D, 0.0D);
    }
}