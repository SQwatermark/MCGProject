package moe.gensoukyo.mcgproject.common.block.ranstone;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.*;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public class RanstoneWire extends Block {

    public static class ItemRanstone extends Item {

        public ItemRanstone() {
            super();
            setRegistryName(MCGProject.ID, "ranstone");
            setTranslationKey(MCGProject.ID + "." + "ranstone");
            this.setCreativeTab(CreativeTabs.REDSTONE);
        }

        @Override
        @Nonnull
        public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float x, float y, float z) {
            boolean flag = world.getBlockState(pos).getBlock().isReplaceable(world, pos);
            BlockPos off = flag ? pos : pos.offset(facing);
            ItemStack stack = player.getHeldItem(hand);
            if (player.canPlayerEdit(off, facing, stack) && world.mayPlace(world.getBlockState(off).getBlock(), off, false, facing, player) && BLOCK.canPlaceBlockAt(world, off)) {
                world.setBlockState(off, BLOCK.getDefaultState());
                if (player instanceof EntityPlayerMP) {
                    CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP)player, off, stack);
                }

                stack.shrink(1);
                return EnumActionResult.SUCCESS;
            } else {
                return EnumActionResult.FAIL;
            }
        }
    }

    public static RanstoneWire BLOCK;
    public static ItemRanstone ITEM;

    public static void initBlock() { BLOCK = new RanstoneWire(); }
    public static void initItem() { ITEM = new ItemRanstone(); }

    public RanstoneWire() {
        super(Material.CIRCUITS);
        this.setDefaultState(this.blockState.getBaseState().withProperty(NORTH, EnumAttachPosition.NONE).withProperty(EAST, EnumAttachPosition.NONE).withProperty(SOUTH, EnumAttachPosition.NONE).withProperty(WEST, EnumAttachPosition.NONE).withProperty(POWER, 0));
        setRegistryName(MCGProject.ID, "ranstone_wire");
        setHardness(0.0F);
        setSoundType(SoundType.STONE);
        setTranslationKey(MCGProject.ID + "." + "rantoneDust");
        disableStats();
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

    public static final PropertyEnum<EnumAttachPosition> NORTH = PropertyEnum.create("north", EnumAttachPosition.class);
    public static final PropertyEnum<EnumAttachPosition> EAST = PropertyEnum.create("east", EnumAttachPosition.class);
    public static final PropertyEnum<EnumAttachPosition> SOUTH = PropertyEnum.create("south", EnumAttachPosition.class);
    public static final PropertyEnum<EnumAttachPosition> WEST = PropertyEnum.create("west", EnumAttachPosition.class);
    public static final PropertyInteger POWER = PropertyInteger.create("power", 0, 15);
    protected static final AxisAlignedBB[] REDSTONE_WIRE_AABB = new AxisAlignedBB[]{new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, 0.8125D, 0.0625D, 0.8125D), new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, 0.8125D, 0.0625D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.1875D, 0.8125D, 0.0625D, 0.8125D), new AxisAlignedBB(0.0D, 0.0D, 0.1875D, 0.8125D, 0.0625D, 1.0D), new AxisAlignedBB(0.1875D, 0.0D, 0.0D, 0.8125D, 0.0625D, 0.8125D), new AxisAlignedBB(0.1875D, 0.0D, 0.0D, 0.8125D, 0.0625D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.8125D, 0.0625D, 0.8125D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.8125D, 0.0625D, 1.0D), new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, 1.0D, 0.0625D, 0.8125D), new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, 1.0D, 0.0625D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.1875D, 1.0D, 0.0625D, 0.8125D), new AxisAlignedBB(0.0D, 0.0D, 0.1875D, 1.0D, 0.0625D, 1.0D), new AxisAlignedBB(0.1875D, 0.0D, 0.0D, 1.0D, 0.0625D, 0.8125D), new AxisAlignedBB(0.1875D, 0.0D, 0.0D, 1.0D, 0.0625D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0625D, 0.8125D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0625D, 1.0D)};
    public boolean canProvidePower = true;
    public final Set<BlockPos> blocksNeedingUpdate = Sets.newHashSet();

    @Override
    @Nonnull
    public AxisAlignedBB getBoundingBox(IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos) {
        return REDSTONE_WIRE_AABB[getAABBIndex(state.getActualState(world, pos))];
    }

    public static int getAABBIndex(IBlockState state) {
        int i = 0;
        boolean flag = state.getValue(NORTH) != EnumAttachPosition.NONE;
        boolean flag1 = state.getValue(EAST) != EnumAttachPosition.NONE;
        boolean flag2 = state.getValue(SOUTH) != EnumAttachPosition.NONE;
        boolean flag3 = state.getValue(WEST) != EnumAttachPosition.NONE;
        if (flag || flag2 && !flag && !flag1 && !flag3) {
            i |= 1 << EnumFacing.NORTH.getHorizontalIndex();
        }

        if (flag1 || flag3 && !flag && !flag1 && !flag2) {
            i |= 1 << EnumFacing.EAST.getHorizontalIndex();
        }

        if (flag2 || flag && !flag1 && !flag2 && !flag3) {
            i |= 1 << EnumFacing.SOUTH.getHorizontalIndex();
        }

        if (flag3 || flag1 && !flag && !flag2 && !flag3) {
            i |= 1 << EnumFacing.WEST.getHorizontalIndex();
        }

        return i;
    }

    @Override
    @Nonnull
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
        state = state.withProperty(WEST, this.getAttachPosition(world, pos, EnumFacing.WEST));
        state = state.withProperty(EAST, this.getAttachPosition(world, pos, EnumFacing.EAST));
        state = state.withProperty(NORTH, this.getAttachPosition(world, pos, EnumFacing.NORTH));
        state = state.withProperty(SOUTH, this.getAttachPosition(world, pos, EnumFacing.SOUTH));
        return state;
    }

    public EnumAttachPosition getAttachPosition(IBlockAccess world, BlockPos pos, EnumFacing facing) {
        BlockPos blockpos = pos.offset(facing);
        IBlockState offset = world.getBlockState(pos.offset(facing));
        if (canConnectTo(world.getBlockState(blockpos), facing, world, blockpos) || !offset.isNormalCube() && canConnectUpwardsTo(world, blockpos.down())) {
            return EnumAttachPosition.SIDE;
        } else {
            IBlockState iblockstate1 = world.getBlockState(pos.up());
            if (!iblockstate1.isNormalCube()) {
                if (canConnectUpwardsTo(world, blockpos.up())) {
                    return EnumAttachPosition.UP;
                }
            }

            return EnumAttachPosition.NONE;
        }
    }

    @Override
    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
        return NULL_AABB;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    public IBlockState updateSurroundingRedstone(World world, BlockPos pos, IBlockState state) {
        state = this.calculateCurrentChanges(world, pos, pos, state);
        List<BlockPos> list = Lists.newArrayList(this.blocksNeedingUpdate);
        this.blocksNeedingUpdate.clear();
        Iterator var5 = list.iterator();

        while(var5.hasNext()) {
            BlockPos blockpos = (BlockPos)var5.next();
            world.notifyNeighborsOfStateChange(blockpos, this, false);
        }

        return state;
    }

    public IBlockState calculateCurrentChanges(World world, BlockPos pos, BlockPos tar, IBlockState state) {
        IBlockState iblockstate = state;
        int i = (Integer)state.getValue(POWER);
        int j = 0;
        j = this.getMaxCurrentStrength(world, pos, j);
        this.canProvidePower = false;
        int k = world.getRedstonePowerFromNeighbors(pos);
        this.canProvidePower = true;
        if (k > 0 && k > j - 1) {
            j = k;
        }

        int l = 0;
        Iterator var10 = EnumFacing.Plane.HORIZONTAL.iterator();

        while(true) {
            while(var10.hasNext()) {
                EnumFacing enumfacing = (EnumFacing)var10.next();
                BlockPos blockpos = pos.offset(enumfacing);
                boolean flag = blockpos.getX() != pos.getX() || blockpos.getZ() != pos.getZ();
                if (flag) {
                    l = this.getMaxCurrentStrength(world, blockpos, l);
                }

                if (world.getBlockState(blockpos).isNormalCube() && !world.getBlockState(pos.up()).isNormalCube()) {
                    if (flag && pos.getY() >= pos.getY()) {
                        l = this.getMaxCurrentStrength(world, blockpos.up(), l);
                    }
                } else if (!world.getBlockState(blockpos).isNormalCube() && flag && pos.getY() <= pos.getY()) {
                    l = this.getMaxCurrentStrength(world, blockpos.down(), l);
                }
            }

            if (l > j) {
                j = l - 1;
            } else if (j > 0) {
                --j;
            } else {
                j = 0;
            }

            if (k > j - 1) {
                j = k;
            }

            if (i != j) {
                state = state.withProperty(POWER, j);
                if (world.getBlockState(pos) == iblockstate) {
                    world.setBlockState(pos, state, 2);
                }

                this.blocksNeedingUpdate.add(pos);
                EnumFacing[] var15 = EnumFacing.values();
                int var16 = var15.length;

                for(int var17 = 0; var17 < var16; ++var17) {
                    EnumFacing enumfacing1 = var15[var17];
                    this.blocksNeedingUpdate.add(pos.offset(enumfacing1));
                }
            }

            return state;
        }
    }

    public void notifyWireNeighborsOfStateChange(World world, BlockPos pos) {
        if (world.getBlockState(pos).getBlock() == this) {
            world.notifyNeighborsOfStateChange(pos, this, false);
            EnumFacing[] var3 = EnumFacing.values();
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                EnumFacing enumfacing = var3[var5];
                world.notifyNeighborsOfStateChange(pos.offset(enumfacing), this, false);
            }
        }

    }

    @Override
    public void onBlockAdded(World world, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        if (!world.isRemote) {
            this.updateSurroundingRedstone(world, pos, state);
            Iterator var4 = EnumFacing.Plane.VERTICAL.iterator();

            EnumFacing enumfacing2;
            while(var4.hasNext()) {
                enumfacing2 = (EnumFacing)var4.next();
                world.notifyNeighborsOfStateChange(pos.offset(enumfacing2), this, false);
            }

            var4 = EnumFacing.Plane.HORIZONTAL.iterator();

            while(var4.hasNext()) {
                enumfacing2 = (EnumFacing)var4.next();
                this.notifyWireNeighborsOfStateChange(world, pos.offset(enumfacing2));
            }

            var4 = EnumFacing.Plane.HORIZONTAL.iterator();

            while(var4.hasNext()) {
                enumfacing2 = (EnumFacing)var4.next();
                BlockPos blockpos = pos.offset(enumfacing2);
                if (world.getBlockState(blockpos).isNormalCube()) {
                    this.notifyWireNeighborsOfStateChange(world, blockpos.up());
                } else {
                    this.notifyWireNeighborsOfStateChange(world, blockpos.down());
                }
            }
        }

    }

    @Override
    public void breakBlock(World world, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        super.breakBlock(world, pos, state);
        if (!world.isRemote) {
            EnumFacing[] var4 = EnumFacing.values();
            int var5 = var4.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                EnumFacing enumfacing = var4[var6];
                world.notifyNeighborsOfStateChange(pos.offset(enumfacing), this, false);
            }

            this.updateSurroundingRedstone(world, pos, state);
            Iterator var8 = EnumFacing.Plane.HORIZONTAL.iterator();

            EnumFacing enumfacing2;
            while(var8.hasNext()) {
                enumfacing2 = (EnumFacing)var8.next();
                this.notifyWireNeighborsOfStateChange(world, pos.offset(enumfacing2));
            }

            var8 = EnumFacing.Plane.HORIZONTAL.iterator();

            while(var8.hasNext()) {
                enumfacing2 = (EnumFacing)var8.next();
                BlockPos blockpos = pos.offset(enumfacing2);
                if (world.getBlockState(blockpos).isNormalCube()) {
                    this.notifyWireNeighborsOfStateChange(world, blockpos.up());
                } else {
                    this.notifyWireNeighborsOfStateChange(world, blockpos.down());
                }
            }
        }

    }

    public int getMaxCurrentStrength(World world, BlockPos pos, int meta) {
        if (world.getBlockState(pos).getBlock() != this) {
            return meta;
        } else {
            int i = world.getBlockState(pos).getValue(POWER);
            return i > meta ? i : meta;
        }
    }

    @Override
    public void neighborChanged(@Nonnull IBlockState state, World world, @Nonnull BlockPos pos, Block block, BlockPos tar) {
        if (!world.isRemote) {
            this.updateSurroundingRedstone(world, pos, state);
        }
    }

    @Override
    @Nonnull
    public Item getItemDropped(IBlockState state, Random random, int i) {
        return ITEM;
    }

    @Override
    public int getStrongPower(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull EnumFacing facing) {
        return !this.canProvidePower ? 0 : state.getWeakPower(world, pos, facing);
    }

    @Override
    public int getWeakPower(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull EnumFacing facing) {
        if (!this.canProvidePower) {
            return 0;
        } else {
            int i = (Integer)state.getValue(POWER);
            if (i == 0) {
                return 0;
            } else if (facing == EnumFacing.UP) {
                return i;
            } else {
                EnumSet<EnumFacing> enumset = EnumSet.noneOf(EnumFacing.class);
                Iterator var7 = EnumFacing.Plane.HORIZONTAL.iterator();

                while(var7.hasNext()) {
                    EnumFacing enumfacing = (EnumFacing)var7.next();
                    if (this.isPowerSourceAt(world, pos, enumfacing)) {
                        enumset.add(enumfacing);
                    }
                }

                if (facing.getAxis().isHorizontal() && enumset.isEmpty()) {
                    return i;
                } else if (enumset.contains(facing) && !enumset.contains(facing.rotateYCCW()) && !enumset.contains(facing.rotateY())) {
                    return i;
                } else {
                    return 0;
                }
            }
        }
    }

    public boolean isPowerSourceAt(IBlockAccess world, BlockPos pos, EnumFacing facing) {
        BlockPos off = pos.offset(facing);
        IBlockState state = world.getBlockState(off);
        Block block = state.getBlock();
        boolean flag = state.isNormalCube();
        boolean flag1 = world.getBlockState(pos.up()).isNormalCube();
        if (!flag1 && flag && canConnectUpwardsTo(world, off.up())) {
            return true;
        } else if (canConnectTo(state, facing, world, pos)) {
            return true;
        } else if ((block == Blocks.POWERED_REPEATER || block == RanstoneRepeater.BLOCK) && state.getValue(BlockRedstoneDiode.FACING) == facing) {
            return true;
        } else {
            return !flag && canConnectUpwardsTo(world, off.down());
        }
    }

    public static boolean canConnectUpwardsTo(IBlockAccess world, @Nonnull BlockPos pos) {
        return canConnectTo(world.getBlockState(pos), null, world, pos);
    }

    public static boolean canConnectTo(IBlockState state, @Nullable EnumFacing facing, @Nonnull IBlockAccess world, @Nonnull BlockPos pos) {
        Block block = state.getBlock();
        if ((block instanceof BlockRedstoneWire) || (block instanceof RanstoneWire)) {
            return true;
        } else if (!RanstoneRepeater.BLOCK.isSameDiode(state)) {
            if (Blocks.OBSERVER == state.getBlock()) {
                return facing == state.getValue(BlockObserver.FACING);
            } else {
                return state.getBlock().canConnectRedstone(state, world, pos, facing);
            }
        } else {
            EnumFacing enumfacing = state.getValue(BlockRedstoneRepeater.FACING);
            return enumfacing == facing || enumfacing.getOpposite() == facing;
        }
    }

    @Override
    public boolean canProvidePower(IBlockState state) {
        return this.canProvidePower;
    }

    @SideOnly(Side.CLIENT)
    public static int colorMultiplier(int meta) {
        float light = (float)meta / 15.0F;
        float R = light * 0.6F + 0.4F;
        if (meta == 0) {
            R = 0.3F;
        }

        float G = light * light * 0.7F - 0.5F;
        float B = light * light * 0.6F - 0.7F;
        if (G < 0.0F) {
            G = 0.0F;
        }

        if (B < 0.0F) {
            B = 0.0F;
        }

        int r = MathHelper.clamp((int)(B * 255.0F), 0, 255);
        int g = MathHelper.clamp((int)(G * 255.0F), 0, 255);
        int b = MathHelper.clamp((int)(R * 255.0F), 0, 255);
        return -16777216 | r << 16 | g << 8 | b;
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull Random random) {
        int i = (Integer)state.getValue(POWER);
        if (i != 0) {
            double x = (double)pos.getX() + 0.5D + ((double)random.nextFloat() - 0.5D) * 0.2D;
            double y = (double)((float)pos.getY() + 0.0625F);
            double z = (double)pos.getZ() + 0.5D + ((double)random.nextFloat() - 0.5D) * 0.2D;
            float light = (float)i / 15.0F;
            float r = light * 0.6F + 0.4F;
            float g = Math.max(0.0F, light * light * 0.7F - 0.5F);
            float b = Math.max(0.0F, light * light * 0.6F - 0.7F);
            world.spawnParticle(EnumParticleTypes.REDSTONE, x, y, z, b <= 0 ? 0.01 : b, g, r);
        }

    }

    @Override
    @Nonnull
    public ItemStack getItem(World world, BlockPos pos, IBlockState state) {
        return new ItemStack(ITEM);
    }

    @Override
    @Nonnull
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(POWER, meta);
    }

    @Override
    @Nonnull
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return (Integer)state.getValue(POWER);
    }

    @Override
    @Nonnull
    public IBlockState withRotation(@Nonnull IBlockState state, Rotation rotation) {
        switch(rotation) {
            case CLOCKWISE_180:
                return state.withProperty(NORTH, state.getValue(SOUTH)).withProperty(EAST, state.getValue(WEST)).withProperty(SOUTH, state.getValue(NORTH)).withProperty(WEST, state.getValue(EAST));
            case COUNTERCLOCKWISE_90:
                return state.withProperty(NORTH, state.getValue(EAST)).withProperty(EAST, state.getValue(SOUTH)).withProperty(SOUTH, state.getValue(WEST)).withProperty(WEST, state.getValue(NORTH));
            case CLOCKWISE_90:
                return state.withProperty(NORTH, state.getValue(WEST)).withProperty(EAST, state.getValue(NORTH)).withProperty(SOUTH, state.getValue(EAST)).withProperty(WEST, state.getValue(SOUTH));
            default:
                return state;
        }
    }

    @Override
    @Nonnull
    public IBlockState withMirror(@Nonnull IBlockState state, Mirror mirror) {
        switch(mirror) {
            case LEFT_RIGHT:
                return state.withProperty(NORTH, state.getValue(SOUTH)).withProperty(SOUTH, state.getValue(NORTH));
            case FRONT_BACK:
                return state.withProperty(EAST, state.getValue(WEST)).withProperty(WEST, state.getValue(EAST));
            default:
                return super.withMirror(state, mirror);
        }
    }

    @Override
    @Nonnull
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, NORTH, EAST, SOUTH, WEST, POWER);
    }

    @Override
    @Nonnull
    public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing facing) {
        return BlockFaceShape.UNDEFINED;
    }

    public enum EnumAttachPosition implements IStringSerializable {
        UP("up"),
        SIDE("side"),
        NONE("none");

        private final String name;

        EnumAttachPosition(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.getName();
        }

        @Override
        @Nonnull
        public String getName() {
            return this.name;
        }
    }
    
}
