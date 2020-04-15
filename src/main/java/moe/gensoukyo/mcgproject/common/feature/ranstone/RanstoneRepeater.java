package moe.gensoukyo.mcgproject.common.feature.ranstone;

import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneRepeater;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlockSpecial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.Random;

public class RanstoneRepeater extends BlockRedstoneRepeater {

    public static RanstoneRepeater BLOCK_N ;
    public static RanstoneRepeater BLOCK;
    public static Item ITEM;

    public static void initBlock() {
        BLOCK_N = new RanstoneRepeater(false);
        BLOCK = new RanstoneRepeater(true);

    }

    public static void initItem() {
        ITEM = new ItemBlockSpecial(BLOCK_N).setRegistryName(MCGProject.ID, "ran_repeater").setTranslationKey(MCGProject.ID + "." + "diode").setCreativeTab(CreativeTabs.REDSTONE);
    }

    public RanstoneRepeater(boolean powered) {
        super(powered);
        setRegistryName(MCGProject.ID, (powered ? "powered_" : "unpowered_") + "ran_repeater");
        setHardness(0.0F);
        setSoundType(SoundType.WOOD);
        setTranslationKey(MCGProject.ID + "." + "diode");
        disableStats();
    }

    @Override
    @Nonnull
    public EnumPushReaction getPushReaction(IBlockState state) {
        return EnumPushReaction.NORMAL;
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        return world.getBlockState(pos).getMaterial().isReplaceable();
    }

    @Override
    public boolean canBlockStay(World world, BlockPos pos) {
        return true;
    }

    @Override
    public boolean isSameDiode(IBlockState state) {
        return state.getBlock() instanceof BlockRedstoneRepeater;
    }

    @Override
    public boolean isFacingTowardsRepeater(World world, BlockPos pos, IBlockState state) {
        EnumFacing facing = (state.getValue(FACING)).getOpposite();
        BlockPos off = pos.offset(facing);
        if (isSameDiode(world.getBlockState(off))) {
            return world.getBlockState(off).getValue(FACING) != facing;
        } else {
            return false;
        }
    }

    @Override
    public boolean isAssociatedBlock(Block block) {
        return this.isSameDiode(block.getDefaultState());
    }

    @Override
    public boolean shouldSideBeRendered(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing facing) {
        return true;
    }

    @Override
    @Nonnull
    public String getLocalizedName() {
        return I18n.translateToLocal("item." + MCGProject.ID + "." + "diode.name");
    }

    @Override
    @Nonnull
    public IBlockState getActualState(IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos) {
        return state.withProperty(LOCKED, this.isLocked(world, pos, state));
    }

    @Override
    @Nonnull
    public IBlockState withRotation(IBlockState state, Rotation rotation) {
        return state.withProperty(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    @Nonnull
    public IBlockState withMirror(IBlockState state, Mirror mirror) {
        return state.withRotation(mirror.toRotation(state.getValue(FACING)));
    }

    @Override
    public boolean onBlockActivated(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float x, float y, float z) {
        if (!player.capabilities.allowEdit) {
            return false;
        } else {
            world.setBlockState(pos, state.cycleProperty(DELAY), 3);
            return true;
        }
    }

    @Override
    protected int getDelay(IBlockState state) {
        return state.getValue(DELAY) * 2;
    }

    @Override
    @Nonnull
    protected IBlockState getPoweredState(IBlockState state) {
        Integer delay = state.getValue(DELAY);
        Boolean locked = state.getValue(LOCKED);
        EnumFacing facing = state.getValue(FACING);
        return BLOCK.getDefaultState().withProperty(FACING, facing).withProperty(DELAY, delay).withProperty(LOCKED, locked);
    }

    @Override
    @Nonnull
    protected IBlockState getUnpoweredState(IBlockState state) {
        Integer delay = state.getValue(DELAY);
        Boolean locked = state.getValue(LOCKED);
        EnumFacing lvt41 = state.getValue(FACING);
        return BLOCK_N.getDefaultState().withProperty(FACING, lvt41).withProperty(DELAY, delay).withProperty(LOCKED, locked);
    }

    @Override
    @Nonnull
    public Item getItemDropped(IBlockState state, Random random, int i) {
        return ITEM;
    }

    @Override
    @Nonnull
    public ItemStack getItem(World world, BlockPos pos, IBlockState state) {
        return new ItemStack(ITEM);
    }

    @Override
    public boolean isLocked(@Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        return this.getPowerOnSides(world, pos, state) > 0;
    }

    @Override
    protected boolean isAlternateInput(@Nonnull IBlockState state) {
        return isDiode(state);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(@Nonnull IBlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull Random random) {
        if (this.isRepeaterPowered) {
            EnumFacing facing = (EnumFacing)state.getValue(FACING);
            double x = (double)((float)pos.getX() + 0.5F) + (double)(random.nextFloat() - 0.5F) * 0.2D;
            double y = (double)((float)pos.getY() + 0.4F) + (double)(random.nextFloat() - 0.5F) * 0.2D;
            double z = (double)((float)pos.getZ() + 0.5F) + (double)(random.nextFloat() - 0.5F) * 0.2D;
            float f = -5.0F;
            if (random.nextBoolean()) {
                f = (float)((Integer)state.getValue(DELAY) * 2 - 1);
            }

            f /= 16.0F;
            double ox = (double)(f * (float)facing.getXOffset());
            double oz = (double)(f * (float)facing.getZOffset());
            world.spawnParticle(EnumParticleTypes.REDSTONE, x + ox, y, z + oz, 0.01D, 0.0D, 1.0D);
        }
    }

    @Override
    public void breakBlock(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        super.breakBlock(world, pos, state);
        this.notifyNeighbors(world, pos, state);
    }

    @Override
    protected int calculateInputStrength(World world, BlockPos pos, IBlockState state) {
        EnumFacing facing = state.getValue(FACING);
        BlockPos offset = pos.offset(facing);
        int i = world.getRedstonePower(offset, facing);
        if (i >= 15) {
            return i;
        } else {
            IBlockState offState = world.getBlockState(offset);
            return Math.max(i, (offState.getBlock() == Blocks.REDSTONE_WIRE || offState.getBlock() == RanstoneWire.BLOCK) ? offState.getValue(BlockRedstoneWire.POWER) : 0);
        }
    }

    @Override
    protected int getPowerOnSide(IBlockAccess world, @Nonnull BlockPos pos, @Nonnull EnumFacing facing) {
        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        if (this.isAlternateInput(state)) {
            if (block == Blocks.REDSTONE_BLOCK) {
                return 15;
            } else {
                return (block == Blocks.REDSTONE_WIRE || block == RanstoneWire.BLOCK) ? state.getValue(BlockRedstoneWire.POWER) : world.getStrongPower(pos, facing);
            }
        } else {
            return 0;
        }
    }

    @Override
    @Nonnull
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.byHorizontalIndex(meta)).withProperty(LOCKED, false).withProperty(DELAY, 1 + (meta >> 2));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int meta = 0;
        meta = meta | (state.getValue(FACING)).getHorizontalIndex();
        meta |= state.getValue(DELAY) - 1 << 2;
        return meta;
    }

    @Override
    @Nonnull
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, DELAY, LOCKED);
    }

}
