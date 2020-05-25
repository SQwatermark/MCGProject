package moe.gensoukyo.mcgproject.common.feature.elevator;

import moe.gensoukyo.mcgproject.common.creativetab.MCGTabs;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

/**
 * @author drzzm32
 * @date 2020/5/25
 * @apiNote 电梯方块
 */
public class BlockElevator extends BlockContainer {

    public static final int SCAN_LIMIT = 256;

    public static BlockElevator BLOCK;
    public static Item ITEM;

    public static void initBlock() {
        BLOCK = new BlockElevator();
    }
    public static void initItem() {
        ITEM = new ItemBlock(BLOCK) {

            @Override
            @Nonnull
            public ActionResult<ItemStack> onItemRightClick(@Nonnull World world, @Nonnull EntityPlayer player, @Nonnull EnumHand hand) {
                ItemStack stack = player.getHeldItem(hand);
                if (player.isSneaking())
                    return new ActionResult<>(EnumActionResult.PASS, stack);

                if (!world.isRemote) {
                    NBTTagCompound tag = stack.getOrCreateSubCompound("bind");
                    if (!tag.hasKey("isUp"))
                        tag.setBoolean("isUp", true);
                    boolean old = tag.getBoolean("isUp");
                    tag.setBoolean("isUp", !old);
                    stack.setTagInfo("bind", tag);
                    boolean isUp = tag.getBoolean("isUp");
                    String name = stack.getDisplayName();
                    stack.setStackDisplayName(name.split("\\|")[0] + "|" + (isUp ? "UP" : "DOWN"));
                }

                return new ActionResult<>(EnumActionResult.SUCCESS, stack);
            }

            @Override
            @Nonnull
            public EnumActionResult onItemUse(EntityPlayer player, World world, @Nonnull BlockPos pos, @Nonnull EnumHand hand, @Nonnull EnumFacing facing, float hitX, float hitY, float hitZ) {
                if (player.isSneaking()) {
                    if (world.isRemote) return EnumActionResult.SUCCESS;
                    ItemStack stack = player.getHeldItem(hand);

                    NBTTagCompound tag = stack.getOrCreateSubCompound("bind");
                    if (!tag.hasKey("isUp"))
                        tag.setBoolean("isUp", true);
                    boolean isUp = tag.getBoolean("isUp");

                    NBTTagCompound source = new NBTTagCompound();
                    source.setInteger("x", pos.getX());
                    source.setInteger("y", pos.getY());
                    source.setInteger("z", pos.getZ());
                    tag.setTag(isUp ? "upCtl" : "downCtl", source);

                    player.sendMessage(new TextComponentString(TextFormatting.DARK_AQUA + (isUp ? "UpCtl" : "DownCtl") + " bind to " +
                            String.format("(%d, %d, %d)", pos.getX(), pos.getY(), pos.getZ())));
                    return EnumActionResult.SUCCESS;
                } else {
                    IBlockState state = world.getBlockState(pos);
                    Block block = state.getBlock();
                    if (!block.isReplaceable(world, pos)) {
                        pos = pos.offset(facing);
                    }

                    ItemStack stack = player.getHeldItem(hand);
                    if (!stack.isEmpty() && player.canPlayerEdit(pos, facing, stack) && world.mayPlace(this.block, pos, false, facing, player)) {
                        int i = this.getMetadata(stack.getMetadata());
                        state = this.block.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, i, player, hand);
                        if (this.placeBlockAt(stack, player, world, pos, facing, hitX, hitY, hitZ, state)) {
                            TileEntity tileEntity = world.getTileEntity(pos);
                            if (tileEntity instanceof TileElevator) {
                                NBTTagCompound tag = stack.getSubCompound("bind");
                                if (tag != null) {
                                    NBTTagCompound sourceTag;
                                    BlockPos source;
                                    if (tag.hasKey("upCtl")) {
                                        sourceTag = tag.getCompoundTag("upCtl");
                                        source = new BlockPos(
                                                sourceTag.getInteger("x"),
                                                sourceTag.getInteger("y"),
                                                sourceTag.getInteger("z")
                                        );
                                        ((TileElevator) tileEntity).setUpCtl(source);
                                    }
                                    if (tag.hasKey("downCtl")) {
                                        sourceTag = tag.getCompoundTag("downCtl");
                                        source = new BlockPos(
                                                sourceTag.getInteger("x"),
                                                sourceTag.getInteger("y"),
                                                sourceTag.getInteger("z")
                                        );
                                        ((TileElevator) tileEntity).setDownCtl(source);
                                    }
                                    ((TileElevator) tileEntity).refresh();
                                }
                            }

                            state = world.getBlockState(pos);
                            SoundType soundtype = state.getBlock().getSoundType(state, world, pos, player);
                            world.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                            stack.shrink(1);
                        }

                        return EnumActionResult.SUCCESS;
                    } else {
                        return EnumActionResult.FAIL;
                    }
                }
            }

            @Override
            public void addInformation(@Nonnull ItemStack stack, @Nullable World world, @Nonnull List<String> list, @Nonnull ITooltipFlag flag) {
                super.addInformation(stack, world, list, flag);
                NBTTagCompound tag = stack.getSubCompound("bind");
                if (tag != null) {
                    NBTTagCompound sourceTag;
                    BlockPos source;
                    String str;
                    if (tag.hasKey("upCtl")) {
                        sourceTag = tag.getCompoundTag("upCtl");
                        source = new BlockPos(
                                sourceTag.getInteger("x"),
                                sourceTag.getInteger("y"),
                                sourceTag.getInteger("z")
                        );
                        str = String.format("UpCtl: (%d, %d, %d)", source.getX(), source.getY(), source.getZ());
                        list.add(TextFormatting.AQUA + str);
                    }
                    if (tag.hasKey("downCtl")) {
                        sourceTag = tag.getCompoundTag("downCtl");
                        source = new BlockPos(
                                sourceTag.getInteger("x"),
                                sourceTag.getInteger("y"),
                                sourceTag.getInteger("z")
                        );
                        str = String.format("DownCtl: (%d, %d, %d)", source.getX(), source.getY(), source.getZ());
                        list.add(TextFormatting.DARK_AQUA + str);
                    }
                }
            }

        }.setRegistryName(MCGProject.ID, "elevator");
    }

    public static final PropertyBool IS_DIR = PropertyBool.create("dir");
    public static final PropertyEnum<EnumFacing> FACING = BlockHorizontal.FACING;

    public BlockElevator() {
        super(Material.IRON);
        setTranslationKey(MCGProject.ID + "." + "elevator");
        setRegistryName(MCGProject.ID, "elevator");
        setHardness(5.0F);
        setLightLevel(0.5F);
        setSoundType(SoundType.METAL);
        setResistance(10.0F);
        setCreativeTab(MCGTabs.FANTASY);

        setDefaultState(this.blockState.getBaseState()
                .withProperty(IS_DIR, false).withProperty(FACING, EnumFacing.NORTH)
        );
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(TextFormatting.DARK_AQUA + I18n.format("tooltip.mcgproject.elevator"));
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World world, int i) {
        return new TileElevator();
    }

    @Nonnull
    @SideOnly(Side.CLIENT)
    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    @Nonnull
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase player) {
        IBlockState state = super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, player);
        return state.withProperty(FACING, player.getHorizontalFacing());
    }

    @Override
    @Nonnull
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, IS_DIR, FACING);
    }

    @Override
    @Nonnull
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(IS_DIR, (meta & 0x4) != 0).withProperty(FACING, EnumFacing.byHorizontalIndex(meta & 0x3));
    }

    @Override
    public int getMetaFromState(@Nonnull IBlockState state) {
        return state.getValue(FACING).getHorizontalIndex() & 0x3 | (state.getValue(IS_DIR) ? 0x4 : 0x0);
    }

    @Override
    public boolean onBlockActivated(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state,
                                    @Nonnull EntityPlayer player, @Nonnull EnumHand hand, @Nonnull EnumFacing facing, float x, float y, float z) {

        Item item = player.getHeldItem(hand).getItem();
        if (item.equals(ITEM)) {
            if (!world.isRemote) {
                state = world.getBlockState(pos);
                boolean old = state.getValue(IS_DIR);
                world.setBlockState(pos, state.withProperty(IS_DIR, !old), 2);
                world.playSound(player, pos, SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
            }

            return true;
        }

        return false;
    }

    @Override
    public void onBlockAdded(World world, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        if (!world.isRemote) {
            world.scheduleUpdate(pos, this, 1);
        }
    }

    @Override
    public void updateTick(World world, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull Random random) {
        if (!world.isRemote) {
            TileEntity tileEntity = world.getTileEntity(pos);
            if (tileEntity instanceof TileElevator) {
                TileElevator elevator = (TileElevator) tileEntity;

                state = world.getBlockState(pos);
                boolean isDir = state.getValue(IS_DIR);
                float angle = state.getValue(FACING).getHorizontalAngle();

                EnumFacing targetFacing = null;
                if (elevator.hasUpCtl() || elevator.hasDownCtl()) {
                    if (elevator.upCrl()) targetFacing = EnumFacing.UP;
                    if (elevator.downCrl()) targetFacing = EnumFacing.DOWN;
                    if (targetFacing != null) {
                        BlockPos target = findElevator(world, pos, targetFacing);
                        if (target != null) {
                            Vec3d offset = new Vec3d(target.subtract(pos));
                            List<Entity> entityList = world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(pos.up()).expand(0, 1, 0));
                            for (Entity e : entityList) {
                                Vec3d vec = e.getPositionVector().add(offset);
                                if (isDir)
                                    e.setLocationAndAngles(vec.x, vec.y, vec.z, angle, 0);
                                e.setPositionAndUpdate(vec.x, vec.y, vec.z);
                                e.motionY = 0;
                            }
                            world.playSound(null, pos, SoundEvents.ENTITY_SHULKER_TELEPORT, SoundCategory.BLOCKS, 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
                            world.playSound(null, target, SoundEvents.ENTITY_SHULKER_TELEPORT, SoundCategory.BLOCKS, 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
                            world.scheduleUpdate(pos, this, 20);
                            return;
                        }
                    }
                } else {
                    List<EntityLivingBase> entityList = world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(pos.up()).expand(0, 1, 0));
                    for (EntityLivingBase e : entityList) {
                        targetFacing = getTargetFromEntityLivingBase(e);
                        if (targetFacing != null) {
                            BlockPos target = findElevator(world, pos, targetFacing);
                            if (target != null) {
                                Vec3d offset = new Vec3d(target.subtract(pos));
                                Vec3d vec = e.getPositionVector().add(offset);
                                if (isDir)
                                    e.setLocationAndAngles(vec.x, vec.y, vec.z, angle, 0);
                                e.setPositionAndUpdate(vec.x, vec.y, vec.z);
                                e.motionY = 0;
                                world.playSound(null, pos, SoundEvents.ENTITY_SHULKER_TELEPORT, SoundCategory.BLOCKS, 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
                                world.playSound(null, target, SoundEvents.ENTITY_SHULKER_TELEPORT, SoundCategory.BLOCKS, 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
                            }
                        }
                    }
                }
            }

            world.scheduleUpdate(pos, this, 1);
        }
    }

    public EnumFacing getTargetFromEntityLivingBase(EntityLivingBase e) {
        boolean isJumping = e.motionY > 0.2 && (e.motionX * e.motionX + e.motionZ * e.motionZ) < 0.001;
        if (isJumping) return EnumFacing.UP;
        if (e.isSneaking()) return EnumFacing.DOWN;
        return null;
    }

    public BlockPos findElevator(World world, BlockPos pos, EnumFacing facing) {
        BlockPos result = null;
        for (int i = 1; i <= SCAN_LIMIT; i++) {
            BlockPos now = pos.offset(facing, i);
            if (!world.isBlockLoaded(now))
                continue;
            if (world.getBlockState(now).getBlock() instanceof BlockElevator) {
                result = now;
                break;
            }
        }
        return result;
    }

}
