package moe.gensoukyo.mcgproject.common.ranstone;

import com.google.common.collect.Lists;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneTorch;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.WeakHashMap;

public class RanstoneTorch extends BlockRedstoneTorch {

    public static RanstoneTorch BLOCK_N = new RanstoneTorch(false);
    public static RanstoneTorch BLOCK = new RanstoneTorch(true);
    public static Item ITEM = new ItemBlock(BLOCK);

    public static void initBlock() {
        BLOCK_N = new RanstoneTorch(false);
        BLOCK = new RanstoneTorch(true);
    }

    public static void initItem() { ITEM = new ItemBlock(BLOCK).setRegistryName(MCGProject.ID, "ranstone_torch"); }

    public RanstoneTorch(boolean lit) {
        super(lit);

        this.isOn = lit;
        this.setTickRandomly(true);
        this.setCreativeTab(null);

        setRegistryName(MCGProject.ID, (lit ? "" : "unlit_") + "ranstone_torch");
        setHardness(0.0F);
        setSoundType(SoundType.WOOD);
        setTranslationKey(MCGProject.ID + "." + "notGate");
        if (lit) {
            setLightLevel(0.5F);
            setCreativeTab(CreativeTabs.REDSTONE);
        }
    }

    @Override
    @Nonnull
    public EnumPushReaction getPushReaction(IBlockState state) {
        return EnumPushReaction.NORMAL;
    }

    @Override
    public boolean canPlaceBlockAt(World world, @Nonnull BlockPos pos) {
        return world.getBlockState(pos).getMaterial().isReplaceable();
    }

    public static final Map<World, List<Toggle>> toggles = new WeakHashMap<>();
    public final boolean isOn;

    public boolean isBurnedOut(World world, BlockPos pos, boolean val) {
        if (!toggles.containsKey(world)) {
            toggles.put(world, Lists.newArrayList());
        }

        List<Toggle> list = toggles.get(world);
        if (val) {
            list.add(new Toggle(pos, world.getTotalWorldTime()));
        }

        int i = 0;

        for(int j = 0; j < list.size(); ++j) {
            Toggle toggle = list.get(j);
            if (toggle.pos.equals(pos)) {
                ++i;
                if (i >= 8) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public int tickRate(World world) {
        return 2;
    }

    @Override
    public void onBlockAdded(@Nonnull World world, @Nonnull BlockPos pos, IBlockState state) {
        if (this.isOn) {
            EnumFacing[] var4 = EnumFacing.values();
            int var5 = var4.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                EnumFacing enumfacing = var4[var6];
                world.notifyNeighborsOfStateChange(pos.offset(enumfacing), this, false);
            }
        }
    }

    @Override
    public void breakBlock(@Nonnull World world, @Nonnull BlockPos pos, IBlockState state) {
        if (this.isOn) {
            EnumFacing[] var4 = EnumFacing.values();
            int var5 = var4.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                EnumFacing enumfacing = var4[var6];
                world.notifyNeighborsOfStateChange(pos.offset(enumfacing), this, false);
            }
        }

    }

    @Override
    public int getWeakPower(@Nonnull IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing facing) {
        return this.isOn && state.getValue(FACING) != facing ? 15 : 0;
    }

    public boolean shouldBeOff(World world, BlockPos pos, IBlockState state) {
        EnumFacing enumfacing = ((EnumFacing)state.getValue(FACING)).getOpposite();
        return world.isSidePowered(pos.offset(enumfacing), enumfacing);
    }

    @Override
    public void randomTick(World world, BlockPos pos, IBlockState state, Random random) {
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, @Nonnull Random random) {
        boolean flag = this.shouldBeOff(world, pos, state);
        List<Toggle> list = toggles.get(world);

        while(list != null && !list.isEmpty() && world.getTotalWorldTime() - ((Toggle)list.get(0)).time > 60L) {
            list.remove(0);
        }

        if (this.isOn) {
            if (flag) {
                world.setBlockState(pos, BLOCK_N.getDefaultState().withProperty(FACING, state.getValue(FACING)), 3);
                if (this.isBurnedOut(world, pos, true)) {
                    world.playSound((EntityPlayer)null, pos, SoundEvents.BLOCK_REDSTONE_TORCH_BURNOUT, SoundCategory.BLOCKS, 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);

                    for(int i = 0; i < 5; ++i) {
                        double d0 = (double)pos.getX() + random.nextDouble() * 0.6D + 0.2D;
                        double d1 = (double)pos.getY() + random.nextDouble() * 0.6D + 0.2D;
                        double d2 = (double)pos.getZ() + random.nextDouble() * 0.6D + 0.2D;
                        world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, 0.0D, 0.0D, 0.0D);
                    }

                    world.scheduleUpdate(pos, world.getBlockState(pos).getBlock(), 160);
                }
            }
        } else if (!flag && !this.isBurnedOut(world, pos, false)) {
            world.setBlockState(pos, BLOCK.getDefaultState().withProperty(FACING, state.getValue(FACING)), 3);
        }

    }

    @Override
    public void neighborChanged(@Nonnull IBlockState state, @Nonnull World world, @Nonnull BlockPos pos, Block block, BlockPos tar) {
        if (!this.onNeighborChangeInternal(world, pos, state) && this.isOn == this.shouldBeOff(world, pos, state)) {
            world.scheduleUpdate(pos, this, this.tickRate(world));
        }
    }

    @Override
    protected boolean onNeighborChangeInternal(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        return false;
    }

    @Override
    public int getStrongPower(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull EnumFacing facing) {
        return facing == EnumFacing.DOWN ? state.getWeakPower(world, pos, facing) : 0;
    }

    @Override
    @Nonnull
    public Item getItemDropped(IBlockState state, Random random, int i) {
        return Item.getItemFromBlock(BLOCK);
    }

    @Override
    public boolean canProvidePower(IBlockState state) {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(@Nonnull IBlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull Random random) {
        if (this.isOn) {
            double d0 = (double)pos.getX() + 0.5D + (random.nextDouble() - 0.5D) * 0.2D;
            double d1 = (double)pos.getY() + 0.7D + (random.nextDouble() - 0.5D) * 0.2D;
            double d2 = (double)pos.getZ() + 0.5D + (random.nextDouble() - 0.5D) * 0.2D;
            EnumFacing enumfacing = state.getValue(FACING);
            if (enumfacing.getAxis().isHorizontal()) {
                EnumFacing facing = enumfacing.getOpposite();
                double d3 = 0.27D;
                d0 += 0.27D * (double)facing.getXOffset();
                d1 += 0.22D;
                d2 += 0.27D * (double)facing.getZOffset();
            }

            world.spawnParticle(EnumParticleTypes.REDSTONE, d0, d1, d2, 0.01D, 0.0D, 1.0D);
        }

    }

    @Override
    @Nonnull
    public ItemStack getItem(World world, BlockPos pos, IBlockState state) {
        return new ItemStack(BLOCK);
    }

    @Override
    public boolean isAssociatedBlock(Block block) {
        return block instanceof BlockRedstoneTorch;
    }

    static class Toggle {
        BlockPos pos;
        long time;

        public Toggle(BlockPos p_i45688_1_, long p_i45688_2_) {
            this.pos = p_i45688_1_;
            this.time = p_i45688_2_;
        }
    }
}
