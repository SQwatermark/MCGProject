package moe.gensoukyo.mcgproject.common.block.ranstone;

import com.google.common.base.Predicate;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneComparator;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlockSpecial;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityComparator;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class RanstoneComparator extends BlockRedstoneComparator {

    public static RanstoneComparator BLOCK_N;
    public static RanstoneComparator BLOCK;
    public static Item ITEM;

    public static void initBlock() {
        BLOCK_N = new RanstoneComparator(false);
        BLOCK = new RanstoneComparator(true);

    }

    public static void initItem() {
        ITEM = new ItemBlockSpecial(BLOCK_N).setRegistryName(MCGProject.ID, "ran_comparator").setTranslationKey(MCGProject.ID + "." + "comparator").setCreativeTab(CreativeTabs.REDSTONE);
    }

    public RanstoneComparator(boolean powered) {
        super(powered);
        setRegistryName(MCGProject.ID, (powered ? "powered_" : "unpowered_") + "ran_comparator");
        setHardness(0.0F);
        if (powered) setLightLevel(0.625F);
        setSoundType(SoundType.WOOD);
        setTranslationKey(MCGProject.ID + "." + "comparator");
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
        return state.getBlock() instanceof BlockRedstoneComparator;
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
        return I18n.translateToLocal("item." + MCGProject.ID + "." + "comparator.name");
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
    protected int getDelay(IBlockState state) {
        return 2;
    }

    @Override
    @Nonnull
    protected IBlockState getPoweredState(IBlockState state) {
        Boolean powered = state.getValue(POWERED);
        Mode mode = state.getValue(MODE);
        EnumFacing facing = state.getValue(FACING);
        return BLOCK.getDefaultState().withProperty(FACING, facing).withProperty(POWERED, powered).withProperty(MODE, mode);
    }

    @Override
    @Nonnull
    protected IBlockState getUnpoweredState(IBlockState state) {
        Boolean powered = state.getValue(POWERED);
        Mode mode = state.getValue(MODE);
        EnumFacing facing = state.getValue(FACING);
        return BLOCK_N.getDefaultState().withProperty(FACING, facing).withProperty(POWERED, powered).withProperty(MODE, mode);
    }

    @Override
    protected boolean isPowered(@Nonnull IBlockState state) {
        return this.isRepeaterPowered || state.getValue(POWERED);
    }

    @Override
    protected int getActiveSignal(IBlockAccess world, @Nonnull BlockPos pos, IBlockState state) {
        TileEntity te = world.getTileEntity(pos);
        return te instanceof TileEntityComparator ? ((TileEntityComparator)te).getOutputSignal() : 0;
    }

    public int calculateOutput(World world, BlockPos pos, IBlockState state) {
        return state.getValue(MODE) == Mode.SUBTRACT ? Math.max(this.calculateInputStrength(world, pos, state) - this.getPowerOnSides(world, pos, state), 0) : this.calculateInputStrength(world, pos, state);
    }

    @Override
    protected boolean shouldBePowered(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        int i = this.calculateInputStrength(world, pos, state);
        if (i >= 15) {
            return true;
        } else if (i == 0) {
            return false;
        } else {
            int j = this.getPowerOnSides(world, pos, state);
            if (j == 0) {
                return true;
            } else {
                return i >= j;
            }
        }
    }

    public static int calculateInputStrengthCore(World world, BlockPos pos, IBlockState state) {
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
    protected int calculateInputStrength(World world, BlockPos pos, IBlockState state) {
        int i = calculateInputStrengthCore(world, pos, state);
        EnumFacing facing = state.getValue(FACING);
        BlockPos tar = pos.offset(facing);
        IBlockState tarState = world.getBlockState(tar);
        if (tarState.hasComparatorInputOverride()) {
            i = tarState.getComparatorInputOverride(world, tar);
        } else if (i < 15 && tarState.isNormalCube()) {
            tar = tar.offset(facing);
            tarState = world.getBlockState(tar);
            if (tarState.hasComparatorInputOverride()) {
                i = tarState.getComparatorInputOverride(world, tar);
            } else if (tarState.getMaterial() == Material.AIR) {
                EntityItemFrame entityitemframe = this.findItemFrame(world, facing, tar);
                if (entityitemframe != null) {
                    i = entityitemframe.getAnalogOutput();
                }
            }
        }

        return i;
    }

    @Nullable
    public EntityItemFrame findItemFrame(World world, final EnumFacing facing, BlockPos pos) {
        List<EntityItemFrame> list = world.getEntitiesWithinAABB(EntityItemFrame.class, new AxisAlignedBB((double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), (double)(pos.getX() + 1), (double)(pos.getY() + 1), (double)(pos.getZ() + 1)), new Predicate<Entity>() {
            public boolean apply(@Nullable Entity p_apply_1_) {
                return p_apply_1_ != null && p_apply_1_.getHorizontalFacing() == facing;
            }
        });
        return list.size() == 1 ? list.get(0) : null;
    }

    @Override
    public boolean onBlockActivated(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float x, float y, float z) {
        if (!player.capabilities.allowEdit) {
            return false;
        } else {
            state = state.cycleProperty(MODE);
            float f = state.getValue(MODE) == Mode.SUBTRACT ? 0.55F : 0.5F;
            world.playSound(player, pos, SoundEvents.BLOCK_COMPARATOR_CLICK, SoundCategory.BLOCKS, 0.3F, f);
            world.setBlockState(pos, state, 2);
            this.onStateChange(world, pos, state);
            return true;
        }
    }

    @Override
    protected void updateState(World world, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        if (!world.isBlockTickPending(pos, this)) {
            int i = this.calculateOutput(world, pos, state);
            TileEntity tileEntity = world.getTileEntity(pos);
            int j = tileEntity instanceof TileEntityComparator ? ((TileEntityComparator)tileEntity).getOutputSignal() : 0;
            if (i != j || this.isPowered(state) != this.shouldBePowered(world, pos, state)) {
                if (this.isFacingTowardsRepeater(world, pos, state)) {
                    world.updateBlockTick(pos, this, 2, -1);
                } else {
                    world.updateBlockTick(pos, this, 2, 0);
                }
            }
        }

    }

    public void onStateChange(World world, BlockPos pos, IBlockState state) {
        int i = this.calculateOutput(world, pos, state);
        TileEntity te = world.getTileEntity(pos);
        int j = 0;
        if (te instanceof TileEntityComparator) {
            TileEntityComparator comparator = (TileEntityComparator)te;
            j = comparator.getOutputSignal();
            comparator.setOutputSignal(i);
        }

        if (j != i || state.getValue(MODE) == Mode.COMPARE) {
            boolean flag1 = this.shouldBePowered(world, pos, state);
            boolean flag = this.isPowered(state);
            if (flag && !flag1) {
                world.setBlockState(pos, state.withProperty(POWERED, false), 2);
            } else if (!flag && flag1) {
                world.setBlockState(pos, state.withProperty(POWERED, true), 2);
            }

            this.notifyNeighbors(world, pos, state);
        }

    }

    @Override
    public void updateTick(World world, @Nonnull BlockPos pos, IBlockState state, Random random) {
        if (this.isRepeaterPowered) {
            world.setBlockState(pos, this.getUnpoweredState(state).withProperty(POWERED, true), 4);
        }

        this.onStateChange(world, pos, state);
    }

    @Override
    public void onBlockAdded(World world, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        super.onBlockAdded(world, pos, state);
        world.setTileEntity(pos, this.createNewTileEntity(world, 0));
    }

    @Override
    public void breakBlock(World world, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        super.breakBlock(world, pos, state);
        world.removeTileEntity(pos);
        this.notifyNeighbors(world, pos, state);
    }

    @Override
    public boolean eventReceived(@Nonnull IBlockState state, World world, @Nonnull BlockPos pos, int i, int j) {
        super.eventReceived(state, world, pos, i, j);
        TileEntity te = world.getTileEntity(pos);
        return te != null && te.receiveClientEvent(i, j);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int i) {
        return new TileEntityComparator();
    }

    @Override
    @Nonnull
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.byHorizontalIndex(meta)).withProperty(POWERED, (meta & 8) > 0).withProperty(MODE, (meta & 4) > 0 ? Mode.SUBTRACT : Mode.COMPARE);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int i = 0;
        i = i | (state.getValue(FACING)).getHorizontalIndex();
        if (state.getValue(POWERED)) {
            i |= 8;
        }

        if (state.getValue(MODE) == Mode.SUBTRACT) {
            i |= 4;
        }

        return i;
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
    @Nonnull
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, MODE, POWERED);
    }

    @Override
    @Nonnull
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float x, float y, float z, int i, EntityLivingBase player) {
        return this.getDefaultState().withProperty(FACING, player.getHorizontalFacing().getOpposite()).withProperty(POWERED, false).withProperty(MODE, Mode.COMPARE);
    }

    @Override
    public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos tar) {
        if (pos.getY() == tar.getY() && world instanceof World && !((World)world).isRemote) {
            this.neighborChanged(world.getBlockState(pos), (World)world, pos, world.getBlockState(tar).getBlock(), tar);
        }

    }

    @Override
    public boolean getWeakChanges(IBlockAccess world, BlockPos pos) {
        return true;
    }
    
}
