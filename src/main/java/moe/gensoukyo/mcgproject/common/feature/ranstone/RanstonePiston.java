package moe.gensoukyo.mcgproject.common.feature.ranstone;

import com.google.common.collect.Lists;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.block.*;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.state.BlockPistonStructureHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPiston;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class RanstonePiston {

    public static class Base extends BlockPistonBase {

        public static Base BLOCK;
        public static Item ITEM;

        public static void initBlock() { BLOCK = new Base(); }
        public static void initItem() { ITEM = new ItemPiston(BLOCK).setRegistryName(MCGProject.ID, "ranstone_piston"); }

        public Base() {
            super(false);
            setRegistryName(MCGProject.ID, "ranstone_piston");
            setTranslationKey(MCGProject.ID + "." + "ranstonePiston");
        }

        @Override
        @Nonnull
        public EnumPushReaction getPushReaction(IBlockState state) {
            return EnumPushReaction.NORMAL;
        }

        public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase player, ItemStack stack) {
            world.setBlockState(pos, state.withProperty(FACING, EnumFacing.getDirectionFromEntityLiving(pos, player)), 2);
            if (!world.isRemote) {
                this.checkForMove(world, pos, state);
            }
        }

        public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos tar) {

        }

        @Override
        public int tickRate(World world) {
            return 20;
        }

        @Override
        public void updateTick(World world, BlockPos pos, IBlockState state, Random random) {
            if (!world.isRemote && world.getTileEntity(pos) == null) {
                this.checkForMove(world, pos, state);
            }
        }

        public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
            if (!world.isRemote && world.getTileEntity(pos) == null) {
                this.checkForMove(world, pos, state);
            }
        }

        public void extendPiston(World world, BlockPos pos, EnumFacing facing) {
            if ((new BlockPistonStructureHelper(world, pos, facing, true)).canMove()) {
                world.addBlockEvent(pos, this, 0, facing.getIndex());
            }
        }

        public void contractPiston(World world, BlockPos pos, EnumFacing facing) {
            world.addBlockEvent(pos, this, 1, facing.getIndex());
        }

        public void checkForMove(World world, BlockPos pos, IBlockState state) {
            EnumFacing facing = state.getValue(FACING);
            boolean powered = this.sideHasPowered(world, pos, facing);
            boolean hasBlock = this.headHasBlock(state, world, pos, facing, powered);

            if (!powered) {
                if (hasBlock && !state.getValue(EXTENDED))
                    extendPiston(world, pos, facing);
                else if (!hasBlock && state.getValue(EXTENDED))
                    contractPiston(world, pos, facing);
            } else {
                boolean nearHas = this.headHasBlock(state, world, pos, facing, false);
                if (!nearHas && !state.getValue(EXTENDED))
                    extendPiston(world, pos, facing);
                else if (hasBlock && state.getValue(EXTENDED))
                    contractPiston(world, pos, facing);
            }

            world.scheduleUpdate(new BlockPos(pos), this, this.tickRate(world));
        }

        public boolean sideHasPowered(World world, BlockPos pos, EnumFacing facing) {
            EnumFacing[] var4 = EnumFacing.values();
            int var5 = var4.length;

            int var6;
            for(var6 = 0; var6 < var5; ++var6) {
                EnumFacing enumfacing = var4[var6];
                if (enumfacing != facing && world.isSidePowered(pos.offset(enumfacing), enumfacing)) {
                    return true;
                }
            }

            if (world.isSidePowered(pos, EnumFacing.DOWN)) {
                return true;
            } else {
                BlockPos blockpos = pos.up();
                EnumFacing[] var10 = EnumFacing.values();
                var6 = var10.length;

                for(int var11 = 0; var11 < var6; ++var11) {
                    EnumFacing enumfacing1 = var10[var11];
                    if (enumfacing1 != EnumFacing.DOWN && world.isSidePowered(blockpos.offset(enumfacing1), enumfacing1)) {
                        return true;
                    }
                }

                return false;
            }
        }

        public boolean headHasBlock(IBlockState state, World world, BlockPos pos, EnumFacing facing, boolean extended) {
            BlockPos target = pos.offset(facing, extended ? 2 : 1);
            IBlockState tarState = world.getBlockState(target);
            Block block = tarState.getBlock();
            if (world.isAirBlock(target))
                return false;
            if (block == Extension.BLOCK || block == Moving.BLOCK)
                return false;
            return tarState.getPushReaction() == EnumPushReaction.NORMAL || block == BLOCK;
        }

        public boolean eventReceived(IBlockState state, World world, BlockPos pos, int i, int j) {
            EnumFacing facing = state.getValue(FACING);
            boolean powered = this.sideHasPowered(world, pos, facing);
            boolean hasBlock = this.headHasBlock(state, world, pos, facing, powered);
            if (!world.isRemote) {
                if (!powered) {
                    if (hasBlock && i == 1) { // 有方块但是收回
                        world.setBlockState(pos, state.withProperty(EXTENDED, true), 2);
                        return false;
                    }
                    if (!hasBlock && i == 0) { // 无方块但是伸出
                        world.setBlockState(pos, state.withProperty(EXTENDED, false), 2);
                        return false;
                    }
                } else {
                    boolean nearHas = this.headHasBlock(state, world, pos, facing, false);
                    if (!nearHas && !hasBlock && i == 1) { // 无方块但是收回
                        world.setBlockState(pos, state.withProperty(EXTENDED, true), 2);
                        return false;
                    }
                    if (nearHas && i == 0) { // 有方块但是伸出
                        world.setBlockState(pos, state.withProperty(EXTENDED, false), 2);
                        return false;
                    }
                }
            }

            if (i == 0) {
                if (!this.doMove(world, pos, facing, true)) {
                    return false;
                }

                world.setBlockState(pos, state.withProperty(EXTENDED, true), 3);
                world.playSound((EntityPlayer)null, pos, SoundEvents.BLOCK_PISTON_EXTEND, SoundCategory.BLOCKS, 0.5F, world.rand.nextFloat() * 0.25F + 0.6F);
            } else if (i == 1) {
                TileEntity tileentity1 = world.getTileEntity(pos.offset(facing));
                if (tileentity1 instanceof TilePiston) {
                    ((TilePiston)tileentity1).clearPistonTileEntity();
                }

                world.setBlockState(pos, Moving.BLOCK.getDefaultState().withProperty(Moving.FACING, facing).withProperty(Moving.TYPE, Extension.EnumPistonType.DEFAULT), 3);
                world.setTileEntity(pos, Moving.createTilePiston(this.getStateFromMeta(j), facing, false, true));
                if (powered && hasBlock) {
                    BlockPos blockpos = pos.add(facing.getXOffset() * 2, facing.getYOffset() * 2, facing.getZOffset() * 2);
                    IBlockState iblockstate = world.getBlockState(blockpos);
                    Block block = iblockstate.getBlock();
                    boolean flag1 = false;
                    if (block == Moving.BLOCK) {
                        TileEntity tileentity = world.getTileEntity(blockpos);
                        if (tileentity instanceof TilePiston) {
                            TilePiston piston = (TilePiston) tileentity;
                            if (piston.getFacing() == facing && piston.isExtending()) {
                                piston.clearPistonTileEntity();
                                flag1 = true;
                            }
                        }
                    }

                    if (!flag1 && !iblockstate.getBlock().isAir(iblockstate, world, blockpos) && canPush(iblockstate, world, blockpos, facing.getOpposite(), false, facing) && (iblockstate.getPushReaction() == EnumPushReaction.NORMAL || block == Base.BLOCK)) {
                        this.doMove(world, pos, facing, false);
                    }
                } else {
                    world.setBlockToAir(pos.offset(facing));
                }

                world.playSound((EntityPlayer)null, pos, SoundEvents.BLOCK_PISTON_CONTRACT, SoundCategory.BLOCKS, 0.5F, world.rand.nextFloat() * 0.15F + 0.6F);
            }

            return true;
        }

        public boolean doMove(World world, BlockPos pos, EnumFacing facing, boolean val) {
            if (!val) {
                world.setBlockToAir(pos.offset(facing));
            }

            BlockPistonStructureHelper helper = new BlockPistonStructureHelper(world, pos, facing, val);
            if (!helper.canMove()) {
                return false;
            } else {
                List<BlockPos> list = helper.getBlocksToMove();
                List<IBlockState> list1 = Lists.newArrayList();

                for(int i = 0; i < list.size(); ++i) {
                    BlockPos blockpos = (BlockPos)list.get(i);
                    list1.add(world.getBlockState(blockpos).getActualState(world, blockpos));
                }

                List<BlockPos> list2 = helper.getBlocksToDestroy();
                int k = list.size() + list2.size();
                IBlockState[] aiblockstate = new IBlockState[k];
                EnumFacing enumfacing = val ? facing : facing.getOpposite();

                int l;
                BlockPos blockpos3;
                IBlockState iblockstate3;
                for(l = list2.size() - 1; l >= 0; --l) {
                    blockpos3 = (BlockPos)list2.get(l);
                    iblockstate3 = world.getBlockState(blockpos3);
                    float chance = iblockstate3.getBlock() instanceof BlockSnow ? -1.0F : 1.0F;
                    iblockstate3.getBlock().dropBlockAsItemWithChance(world, blockpos3, iblockstate3, chance, 0);
                    world.setBlockState(blockpos3, Blocks.AIR.getDefaultState(), 4);
                    --k;
                    aiblockstate[k] = iblockstate3;
                }

                for(l = list.size() - 1; l >= 0; --l) {
                    blockpos3 = (BlockPos)list.get(l);
                    iblockstate3 = world.getBlockState(blockpos3);
                    world.setBlockState(blockpos3, Blocks.AIR.getDefaultState(), 2);
                    blockpos3 = blockpos3.offset(enumfacing);
                    world.setBlockState(blockpos3, Moving.BLOCK.getDefaultState().withProperty(FACING, facing), 4);
                    world.setTileEntity(blockpos3, Moving.createTilePiston((IBlockState)list1.get(l), facing, val, false));
                    --k;
                    aiblockstate[k] = iblockstate3;
                }

                BlockPos blockpos2 = pos.offset(facing);
                if (val) {
                    Extension.EnumPistonType TilePistonextension$enumpistontype = Extension.EnumPistonType.DEFAULT;
                    iblockstate3 = Extension.BLOCK.getDefaultState().withProperty(Extension.FACING, facing).withProperty(Extension.TYPE, TilePistonextension$enumpistontype);
                    IBlockState iblockstate1 = Moving.BLOCK.getDefaultState().withProperty(Moving.FACING, facing).withProperty(Moving.TYPE, Extension.EnumPistonType.DEFAULT);
                    world.setBlockState(blockpos2, iblockstate1, 4);
                    world.setTileEntity(blockpos2, Moving.createTilePiston(iblockstate3, facing, true, true));
                }

                int j1;
                for(j1 = list2.size() - 1; j1 >= 0; --j1) {
                    world.notifyNeighborsOfStateChange((BlockPos)list2.get(j1), aiblockstate[k++].getBlock(), false);
                }

                for(j1 = list.size() - 1; j1 >= 0; --j1) {
                    world.notifyNeighborsOfStateChange((BlockPos)list.get(j1), aiblockstate[k++].getBlock(), false);
                }

                if (val) {
                    world.notifyNeighborsOfStateChange(blockpos2, Extension.BLOCK, false);
                }

                return true;
            }
        }

    }

    public static class Extension extends BlockPistonExtension {

        public static Extension BLOCK;

        public static void initBlock() { BLOCK = new Extension(); }

        public Extension() {
            super();
            setRegistryName(MCGProject.ID, "ranstone_piston_head");
            setTranslationKey(MCGProject.ID + "." + "ranstonePistonBase");
        }

        public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
            if (player.capabilities.isCreativeMode) {
                BlockPos base = pos.offset(((EnumFacing)state.getValue(FACING)).getOpposite());
                Block block = world.getBlockState(base).getBlock();
                if (block == Base.BLOCK) {
                    world.setBlockToAir(base);
                }
            }
        }

        public void breakBlock(World world, BlockPos pos, IBlockState state) {
            EnumFacing facing = ((EnumFacing)state.getValue(FACING)).getOpposite();
            pos = pos.offset(facing);
            IBlockState base = world.getBlockState(pos);
            if ((base.getBlock() == Base.BLOCK) && (Boolean)base.getValue(Base.EXTENDED)) {
                base.getBlock().dropBlockAsItem(world, pos, base, 0);
                world.setBlockToAir(pos);
            }
        }

        public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos tar) {
            EnumFacing facing = (EnumFacing)state.getValue(FACING);
            BlockPos base = pos.offset(facing.getOpposite());
            IBlockState baseState = world.getBlockState(base);
            if (baseState.getBlock() != Base.BLOCK) {
                world.setBlockToAir(pos);
            } else {
                baseState.neighborChanged(world, base, block, tar);
            }
        }

        public ItemStack getItem(World world, BlockPos pos, IBlockState state) {
            return new ItemStack(Base.ITEM);
        }

    }

    public static class Moving extends BlockPistonMoving {
        
        public static Moving BLOCK;

        public static void initBlock() { BLOCK = new Moving(); }

        public Moving() {
            super();
            setRegistryName(MCGProject.ID, "ranstone_piston_extension");
        }

        public static TileEntity createTilePiston(IBlockState p_createTilePiston_0_, EnumFacing p_createTilePiston_1_, boolean p_createTilePiston_2_, boolean p_createTilePiston_3_) {
            return new TilePiston(p_createTilePiston_0_, p_createTilePiston_1_, p_createTilePiston_2_, p_createTilePiston_3_);
        }

        public void breakBlock(World p_breakBlock_1_, BlockPos p_breakBlock_2_, IBlockState p_breakBlock_3_) {
            TileEntity tileentity = p_breakBlock_1_.getTileEntity(p_breakBlock_2_);
            if (tileentity instanceof TilePiston) {
                ((TilePiston)tileentity).clearPistonTileEntity();
            } else {
                super.breakBlock(p_breakBlock_1_, p_breakBlock_2_, p_breakBlock_3_);
            }

        }

        @Nullable
        public AxisAlignedBB getCollisionBoundingBox(IBlockState p_getCollisionBoundingBox_1_, IBlockAccess p_getCollisionBoundingBox_2_, BlockPos p_getCollisionBoundingBox_3_) {
            TilePiston piston = this.getTilePistonAt(p_getCollisionBoundingBox_2_, p_getCollisionBoundingBox_3_);
            return piston == null ? null : piston.getAABB(p_getCollisionBoundingBox_2_, p_getCollisionBoundingBox_3_);
        }

        public void addCollisionBoxToList(IBlockState p_addCollisionBoxToList_1_, World p_addCollisionBoxToList_2_, BlockPos p_addCollisionBoxToList_3_, AxisAlignedBB p_addCollisionBoxToList_4_, List<AxisAlignedBB> p_addCollisionBoxToList_5_, @Nullable Entity p_addCollisionBoxToList_6_, boolean p_addCollisionBoxToList_7_) {
            TilePiston piston = this.getTilePistonAt(p_addCollisionBoxToList_2_, p_addCollisionBoxToList_3_);
            if (piston != null) {
                piston.addCollissionAABBs(p_addCollisionBoxToList_2_, p_addCollisionBoxToList_3_, p_addCollisionBoxToList_4_, p_addCollisionBoxToList_5_, p_addCollisionBoxToList_6_);
            }

        }

        public AxisAlignedBB getBoundingBox(IBlockState p_getBoundingBox_1_, IBlockAccess p_getBoundingBox_2_, BlockPos p_getBoundingBox_3_) {
            TilePiston piston = this.getTilePistonAt(p_getBoundingBox_2_, p_getBoundingBox_3_);
            return piston != null ? piston.getAABB(p_getBoundingBox_2_, p_getBoundingBox_3_) : FULL_BLOCK_AABB;
        }

        @Nullable
        public TilePiston getTilePistonAt(IBlockAccess p_getTilePistonAt_1_, BlockPos p_getTilePistonAt_2_) {
            TileEntity tileentity = p_getTilePistonAt_1_.getTileEntity(p_getTilePistonAt_2_);
            return tileentity instanceof TilePiston ? (TilePiston)tileentity : null;
        }

        public void getDrops(NonNullList<ItemStack> p_getDrops_1_, IBlockAccess p_getDrops_2_, BlockPos p_getDrops_3_, IBlockState p_getDrops_4_, int p_getDrops_5_) {
            TilePiston piston = this.getTilePistonAt(p_getDrops_2_, p_getDrops_3_);
            if (piston != null) {
                IBlockState pushed = piston.getPistonState();
                p_getDrops_1_.addAll(pushed.getBlock().getDrops(p_getDrops_2_, p_getDrops_3_, pushed, p_getDrops_5_));
            }

        }

    }

    public static class TilePiston extends TileEntity implements ITickable {

        private IBlockState pistonState;
        private EnumFacing pistonFacing;
        private boolean extending;
        private boolean shouldHeadBeRendered;
        private static final ThreadLocal<EnumFacing> MOVING_ENTITY = new ThreadLocal<EnumFacing>() {
            protected EnumFacing initialValue() {
                return null;
            }
        };
        private float progress;
        private float lastProgress;

        public TilePiston() {
        }

        public TilePiston(IBlockState p_i45665_1_, EnumFacing p_i45665_2_, boolean p_i45665_3_, boolean p_i45665_4_) {
            this.pistonState = p_i45665_1_;
            this.pistonFacing = p_i45665_2_;
            this.extending = p_i45665_3_;
            this.shouldHeadBeRendered = p_i45665_4_;
        }

        public IBlockState getPistonState() {
            return this.pistonState;
        }

        public NBTTagCompound getUpdateTag() {
            return this.writeToNBT(new NBTTagCompound());
        }

        public int getBlockMetadata() {
            return 0;
        }

        public boolean isExtending() {
            return this.extending;
        }

        public EnumFacing getFacing() {
            return this.pistonFacing;
        }

        public boolean shouldPistonHeadBeRendered() {
            return this.shouldHeadBeRendered;
        }

        @SideOnly(Side.CLIENT)
        public float getProgress(float p_getProgress_1_) {
            if (p_getProgress_1_ > 1.0F) {
                p_getProgress_1_ = 1.0F;
            }

            return this.lastProgress + (this.progress - this.lastProgress) * p_getProgress_1_;
        }

        @SideOnly(Side.CLIENT)
        public float getOffsetX(float p_getOffsetX_1_) {
            return (float)this.pistonFacing.getXOffset() * this.getExtendedProgress(this.getProgress(p_getOffsetX_1_));
        }

        @SideOnly(Side.CLIENT)
        public float getOffsetY(float p_getOffsetY_1_) {
            return (float)this.pistonFacing.getYOffset() * this.getExtendedProgress(this.getProgress(p_getOffsetY_1_));
        }

        @SideOnly(Side.CLIENT)
        public float getOffsetZ(float p_getOffsetZ_1_) {
            return (float)this.pistonFacing.getZOffset() * this.getExtendedProgress(this.getProgress(p_getOffsetZ_1_));
        }

        private float getExtendedProgress(float p_getExtendedProgress_1_) {
            return this.extending ? p_getExtendedProgress_1_ - 1.0F : 1.0F - p_getExtendedProgress_1_;
        }

        public AxisAlignedBB getAABB(IBlockAccess p_getAABB_1_, BlockPos p_getAABB_2_) {
            return this.getAABB(p_getAABB_1_, p_getAABB_2_, this.progress).union(this.getAABB(p_getAABB_1_, p_getAABB_2_, this.lastProgress));
        }

        public AxisAlignedBB getAABB(IBlockAccess p_getAABB_1_, BlockPos p_getAABB_2_, float p_getAABB_3_) {
            p_getAABB_3_ = this.getExtendedProgress(p_getAABB_3_);
            IBlockState iblockstate = this.getCollisionRelatedBlockState();
            return iblockstate.getBoundingBox(p_getAABB_1_, p_getAABB_2_).offset((double)(p_getAABB_3_ * (float)this.pistonFacing.getXOffset()), (double)(p_getAABB_3_ * (float)this.pistonFacing.getYOffset()), (double)(p_getAABB_3_ * (float)this.pistonFacing.getZOffset()));
        }

        private IBlockState getCollisionRelatedBlockState() {
            return !this.isExtending() && this.shouldPistonHeadBeRendered() ? Extension.BLOCK.getDefaultState().withProperty(Extension.TYPE, Extension.EnumPistonType.DEFAULT).withProperty(Extension.FACING, this.pistonState.getValue(Base.FACING)) : this.pistonState;
        }

        private void moveCollidedEntities(float p_moveCollidedEntities_1_) {
            EnumFacing enumfacing = this.extending ? this.pistonFacing : this.pistonFacing.getOpposite();
            double d0 = (double)(p_moveCollidedEntities_1_ - this.progress);
            List<AxisAlignedBB> list = Lists.newArrayList();
            this.getCollisionRelatedBlockState().addCollisionBoxToList(this.world, BlockPos.ORIGIN, new AxisAlignedBB(BlockPos.ORIGIN), list, (Entity)null, true);
            if (!list.isEmpty()) {
                AxisAlignedBB axisalignedbb = this.moveByPositionAndProgress(this.getMinMaxPiecesAABB(list));
                List<Entity> list1 = this.world.getEntitiesWithinAABBExcludingEntity((Entity)null, this.getMovementArea(axisalignedbb, enumfacing, d0).union(axisalignedbb));
                if (!list1.isEmpty()) {
                    boolean flag = this.pistonState.getBlock().isStickyBlock(this.pistonState);

                    for(int i = 0; i < list1.size(); ++i) {
                        Entity entity = (Entity)list1.get(i);
                        if (entity.getPushReaction() != EnumPushReaction.IGNORE) {
                            if (flag) {
                                switch(enumfacing.getAxis()) {
                                    case X:
                                        entity.motionX = (double)enumfacing.getXOffset();
                                        break;
                                    case Y:
                                        entity.motionY = (double)enumfacing.getYOffset();
                                        break;
                                    case Z:
                                        entity.motionZ = (double)enumfacing.getZOffset();
                                }
                            }

                            double d1 = 0.0D;

                            for(int j = 0; j < list.size(); ++j) {
                                AxisAlignedBB axisalignedbb1 = this.getMovementArea(this.moveByPositionAndProgress((AxisAlignedBB)list.get(j)), enumfacing, d0);
                                AxisAlignedBB axisalignedbb2 = entity.getEntityBoundingBox();
                                if (axisalignedbb1.intersects(axisalignedbb2)) {
                                    d1 = Math.max(d1, this.getMovement(axisalignedbb1, enumfacing, axisalignedbb2));
                                    if (d1 >= d0) {
                                        break;
                                    }
                                }
                            }

                            if (d1 > 0.0D) {
                                d1 = Math.min(d1, d0) + 0.01D;
                                MOVING_ENTITY.set(enumfacing);
                                entity.move(MoverType.PISTON, d1 * (double)enumfacing.getXOffset(), d1 * (double)enumfacing.getYOffset(), d1 * (double)enumfacing.getZOffset());
                                MOVING_ENTITY.set(null);
                                if (!this.extending && this.shouldHeadBeRendered) {
                                    this.fixEntityWithinPistonBase(entity, enumfacing, d0);
                                }
                            }
                        }
                    }
                }
            }

        }

        private AxisAlignedBB getMinMaxPiecesAABB(List<AxisAlignedBB> p_getMinMaxPiecesAABB_1_) {
            double d0 = 0.0D;
            double d1 = 0.0D;
            double d2 = 0.0D;
            double d3 = 1.0D;
            double d4 = 1.0D;
            double d5 = 1.0D;

            AxisAlignedBB axisalignedbb;
            for(Iterator var14 = p_getMinMaxPiecesAABB_1_.iterator(); var14.hasNext(); d5 = Math.max(axisalignedbb.maxZ, d5)) {
                axisalignedbb = (AxisAlignedBB)var14.next();
                d0 = Math.min(axisalignedbb.minX, d0);
                d1 = Math.min(axisalignedbb.minY, d1);
                d2 = Math.min(axisalignedbb.minZ, d2);
                d3 = Math.max(axisalignedbb.maxX, d3);
                d4 = Math.max(axisalignedbb.maxY, d4);
            }

            return new AxisAlignedBB(d0, d1, d2, d3, d4, d5);
        }

        private double getMovement(AxisAlignedBB p_getMovement_1_, EnumFacing p_getMovement_2_, AxisAlignedBB p_getMovement_3_) {
            switch(p_getMovement_2_.getAxis()) {
                case X:
                    return getDeltaX(p_getMovement_1_, p_getMovement_2_, p_getMovement_3_);
                case Y:
                default:
                    return getDeltaY(p_getMovement_1_, p_getMovement_2_, p_getMovement_3_);
                case Z:
                    return getDeltaZ(p_getMovement_1_, p_getMovement_2_, p_getMovement_3_);
            }
        }

        private AxisAlignedBB moveByPositionAndProgress(AxisAlignedBB p_moveByPositionAndProgress_1_) {
            double d0 = (double)this.getExtendedProgress(this.progress);
            return p_moveByPositionAndProgress_1_.offset((double)this.pos.getX() + d0 * (double)this.pistonFacing.getXOffset(), (double)this.pos.getY() + d0 * (double)this.pistonFacing.getYOffset(), (double)this.pos.getZ() + d0 * (double)this.pistonFacing.getZOffset());
        }

        private AxisAlignedBB getMovementArea(AxisAlignedBB p_getMovementArea_1_, EnumFacing p_getMovementArea_2_, double p_getMovementArea_3_) {
            double d0 = p_getMovementArea_3_ * (double)p_getMovementArea_2_.getAxisDirection().getOffset();
            double d1 = Math.min(d0, 0.0D);
            double d2 = Math.max(d0, 0.0D);
            switch(p_getMovementArea_2_) {
                case WEST:
                    return new AxisAlignedBB(p_getMovementArea_1_.minX + d1, p_getMovementArea_1_.minY, p_getMovementArea_1_.minZ, p_getMovementArea_1_.minX + d2, p_getMovementArea_1_.maxY, p_getMovementArea_1_.maxZ);
                case EAST:
                    return new AxisAlignedBB(p_getMovementArea_1_.maxX + d1, p_getMovementArea_1_.minY, p_getMovementArea_1_.minZ, p_getMovementArea_1_.maxX + d2, p_getMovementArea_1_.maxY, p_getMovementArea_1_.maxZ);
                case DOWN:
                    return new AxisAlignedBB(p_getMovementArea_1_.minX, p_getMovementArea_1_.minY + d1, p_getMovementArea_1_.minZ, p_getMovementArea_1_.maxX, p_getMovementArea_1_.minY + d2, p_getMovementArea_1_.maxZ);
                case UP:
                default:
                    return new AxisAlignedBB(p_getMovementArea_1_.minX, p_getMovementArea_1_.maxY + d1, p_getMovementArea_1_.minZ, p_getMovementArea_1_.maxX, p_getMovementArea_1_.maxY + d2, p_getMovementArea_1_.maxZ);
                case NORTH:
                    return new AxisAlignedBB(p_getMovementArea_1_.minX, p_getMovementArea_1_.minY, p_getMovementArea_1_.minZ + d1, p_getMovementArea_1_.maxX, p_getMovementArea_1_.maxY, p_getMovementArea_1_.minZ + d2);
                case SOUTH:
                    return new AxisAlignedBB(p_getMovementArea_1_.minX, p_getMovementArea_1_.minY, p_getMovementArea_1_.maxZ + d1, p_getMovementArea_1_.maxX, p_getMovementArea_1_.maxY, p_getMovementArea_1_.maxZ + d2);
            }
        }

        private void fixEntityWithinPistonBase(Entity p_fixEntityWithinPistonBase_1_, EnumFacing p_fixEntityWithinPistonBase_2_, double p_fixEntityWithinPistonBase_3_) {
            AxisAlignedBB axisalignedbb = p_fixEntityWithinPistonBase_1_.getEntityBoundingBox();
            AxisAlignedBB axisalignedbb1 = Block.FULL_BLOCK_AABB.offset(this.pos);
            if (axisalignedbb.intersects(axisalignedbb1)) {
                EnumFacing enumfacing = p_fixEntityWithinPistonBase_2_.getOpposite();
                double d0 = this.getMovement(axisalignedbb1, enumfacing, axisalignedbb) + 0.01D;
                double d1 = this.getMovement(axisalignedbb1, enumfacing, axisalignedbb.intersect(axisalignedbb1)) + 0.01D;
                if (Math.abs(d0 - d1) < 0.01D) {
                    d0 = Math.min(d0, p_fixEntityWithinPistonBase_3_) + 0.01D;
                    MOVING_ENTITY.set(p_fixEntityWithinPistonBase_2_);
                    p_fixEntityWithinPistonBase_1_.move(MoverType.PISTON, d0 * (double)enumfacing.getXOffset(), d0 * (double)enumfacing.getYOffset(), d0 * (double)enumfacing.getZOffset());
                    MOVING_ENTITY.set(null);
                }
            }

        }

        private static double getDeltaX(AxisAlignedBB p_getDeltaX_0_, EnumFacing p_getDeltaX_1_, AxisAlignedBB p_getDeltaX_2_) {
            return p_getDeltaX_1_.getAxisDirection() == EnumFacing.AxisDirection.POSITIVE ? p_getDeltaX_0_.maxX - p_getDeltaX_2_.minX : p_getDeltaX_2_.maxX - p_getDeltaX_0_.minX;
        }

        private static double getDeltaY(AxisAlignedBB p_getDeltaY_0_, EnumFacing p_getDeltaY_1_, AxisAlignedBB p_getDeltaY_2_) {
            return p_getDeltaY_1_.getAxisDirection() == EnumFacing.AxisDirection.POSITIVE ? p_getDeltaY_0_.maxY - p_getDeltaY_2_.minY : p_getDeltaY_2_.maxY - p_getDeltaY_0_.minY;
        }

        private static double getDeltaZ(AxisAlignedBB p_getDeltaZ_0_, EnumFacing p_getDeltaZ_1_, AxisAlignedBB p_getDeltaZ_2_) {
            return p_getDeltaZ_1_.getAxisDirection() == EnumFacing.AxisDirection.POSITIVE ? p_getDeltaZ_0_.maxZ - p_getDeltaZ_2_.minZ : p_getDeltaZ_2_.maxZ - p_getDeltaZ_0_.minZ;
        }

        public void clearPistonTileEntity() {
            if (this.lastProgress < 1.0F && this.world != null) {
                this.progress = 1.0F;
                this.lastProgress = this.progress;
                this.world.removeTileEntity(this.pos);
                this.invalidate();
                if (this.world.getBlockState(this.pos).getBlock() == Moving.BLOCK) {
                    this.world.setBlockState(this.pos, this.pistonState, 3);
                    this.world.neighborChanged(this.pos, this.pistonState.getBlock(), this.pos);
                }
            }

        }

        public void update() {
            this.lastProgress = this.progress;
            if (this.lastProgress >= 1.0F) {
                this.world.removeTileEntity(this.pos);
                this.invalidate();
                if (this.world.getBlockState(this.pos).getBlock() == Moving.BLOCK) {
                    this.world.setBlockState(this.pos, this.pistonState, 3);
                    this.world.neighborChanged(this.pos, this.pistonState.getBlock(), this.pos);
                }
            } else {
                float f = this.progress + 0.5F;
                this.moveCollidedEntities(f);
                this.progress = f;
                if (this.progress >= 1.0F) {
                    this.progress = 1.0F;
                }
            }

        }

        public static void registerFixesPiston(DataFixer p_registerFixesPiston_0_) {
        }

        public void readFromNBT(NBTTagCompound p_readFromNBT_1_) {
            super.readFromNBT(p_readFromNBT_1_);
            this.pistonState = Block.getBlockById(p_readFromNBT_1_.getInteger("blockId")).getStateFromMeta(p_readFromNBT_1_.getInteger("blockData"));
            this.pistonFacing = EnumFacing.byIndex(p_readFromNBT_1_.getInteger("facing"));
            this.progress = p_readFromNBT_1_.getFloat("progress");
            this.lastProgress = this.progress;
            this.extending = p_readFromNBT_1_.getBoolean("extending");
            this.shouldHeadBeRendered = p_readFromNBT_1_.getBoolean("source");
        }

        public NBTTagCompound writeToNBT(NBTTagCompound p_writeToNBT_1_) {
            super.writeToNBT(p_writeToNBT_1_);
            p_writeToNBT_1_.setInteger("blockId", Block.getIdFromBlock(this.pistonState.getBlock()));
            p_writeToNBT_1_.setInteger("blockData", this.pistonState.getBlock().getMetaFromState(this.pistonState));
            p_writeToNBT_1_.setInteger("facing", this.pistonFacing.getIndex());
            p_writeToNBT_1_.setFloat("progress", this.lastProgress);
            p_writeToNBT_1_.setBoolean("extending", this.extending);
            p_writeToNBT_1_.setBoolean("source", this.shouldHeadBeRendered);
            return p_writeToNBT_1_;
        }

        public void addCollissionAABBs(World p_addCollissionAABBs_1_, BlockPos p_addCollissionAABBs_2_, AxisAlignedBB p_addCollissionAABBs_3_, List<AxisAlignedBB> p_addCollissionAABBs_4_, @Nullable Entity p_addCollissionAABBs_5_) {
            if (!this.extending && this.shouldHeadBeRendered) {
                this.pistonState.withProperty(Base.EXTENDED, true).addCollisionBoxToList(p_addCollissionAABBs_1_, p_addCollissionAABBs_2_, p_addCollissionAABBs_3_, p_addCollissionAABBs_4_, p_addCollissionAABBs_5_, false);
            }

            EnumFacing enumfacing = (EnumFacing)MOVING_ENTITY.get();
            if ((double)this.progress >= 1.0D || enumfacing != (this.extending ? this.pistonFacing : this.pistonFacing.getOpposite())) {
                int i = p_addCollissionAABBs_4_.size();
                IBlockState iblockstate;
                if (this.shouldPistonHeadBeRendered()) {
                    iblockstate = Extension.BLOCK.getDefaultState().withProperty(Extension.FACING, this.pistonFacing).withProperty(Extension.SHORT, this.extending != 1.0F - this.progress < 0.25F);
                } else {
                    iblockstate = this.pistonState;
                }

                float f = this.getExtendedProgress(this.progress);
                double d0 = (double)((float)this.pistonFacing.getXOffset() * f);
                double d1 = (double)((float)this.pistonFacing.getYOffset() * f);
                double d2 = (double)((float)this.pistonFacing.getZOffset() * f);
                iblockstate.addCollisionBoxToList(p_addCollissionAABBs_1_, p_addCollissionAABBs_2_, p_addCollissionAABBs_3_.offset(-d0, -d1, -d2), p_addCollissionAABBs_4_, p_addCollissionAABBs_5_, true);

                for(int j = i; j < p_addCollissionAABBs_4_.size(); ++j) {
                    p_addCollissionAABBs_4_.set(j, ((AxisAlignedBB)p_addCollissionAABBs_4_.get(j)).offset(d0, d1, d2));
                }
            }

        }

    }

}
