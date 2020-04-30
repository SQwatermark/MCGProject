package moe.gensoukyo.mcgproject.common.feature.littlestone;

import moe.gensoukyo.mcgproject.common.creativetab.MCGTabs;
import moe.gensoukyo.mcgproject.common.entity.EntityItemMCG;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

import java.util.List;
import java.util.Random;

public class BlockStoneSpawner extends Block {

    public BlockStoneSpawner() {
        super(Material.ROCK);
        this.setTickRandomly(true);
        this.setCreativeTab(MCGTabs.PROP);
        this.setRegistryName("stone_spawner");
        this.setTranslationKey(MCGProject.ID + "." + "stone_spawner");
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (ForgeHooks.onCropsGrowPre(worldIn, pos, state, rand.nextInt(5) == 0)) {
            List<Entity> entities = worldIn.getEntitiesWithinAABB(EntityItemMCG.class, new AxisAlignedBB(pos.up(15).east(15).north(15), pos.down(15).west(15).south(15)));
            int i = 0;
            for (Entity entity : entities) {
                if (entity instanceof EntityItemMCG) i++;
            }
            if (i > 5) return;
            EntityItemMCG stone = new EntityItemMCG(worldIn, pos.getX() + rand.nextInt(30) - 15, pos.getY(), pos.getZ() + rand.nextInt(30) - 15, new ItemStack(Blocks.STONE));
            worldIn.spawnEntity(stone);
            ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
        }
    }
}
