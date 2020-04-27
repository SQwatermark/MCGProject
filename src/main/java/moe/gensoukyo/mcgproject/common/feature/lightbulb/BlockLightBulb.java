package moe.gensoukyo.mcgproject.common.feature.lightbulb;

import moe.gensoukyo.mcgproject.common.creativetab.MCGTabs;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemCloth;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.IWorldNameable;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * @author drzzm32
 * @date 2020/4/27
 */
public class BlockLightBulb extends BlockColored implements ITileEntityProvider {

    public static BlockLightBulb BLOCK;
    public static Item ITEM;

    public static void initBlock() {
        BLOCK = new BlockLightBulb();
    }
    public static void initItem() {
        ITEM = new ItemCloth(BLOCK) {

            @Override
            @Nonnull
            public EnumActionResult onItemUse(EntityPlayer player, World world, @Nonnull BlockPos pos, @Nonnull EnumHand hand, @Nonnull EnumFacing facing, float hitX, float hitY, float hitZ) {
                if (player.isSneaking()) {
                    if (world.isRemote) return EnumActionResult.SUCCESS;
                    ItemStack stack = player.getHeldItem(hand);
                    NBTTagCompound source = new NBTTagCompound();
                    source.setInteger("x", pos.getX());
                    source.setInteger("y", pos.getY());
                    source.setInteger("z", pos.getZ());
                    stack.setTagInfo("source", source);
                    player.sendMessage(new TextComponentString(TextFormatting.LIGHT_PURPLE + "Bulb bind to " +
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
                            if (tileEntity instanceof TileLightBulb) {
                                NBTTagCompound tag = stack.getSubCompound("source");
                                if (tag != null) {
                                    BlockPos source = new BlockPos(
                                            tag.getInteger("x"),
                                            tag.getInteger("y"),
                                            tag.getInteger("z")
                                    );
                                    ((TileLightBulb) tileEntity).setSource(source);
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
                NBTTagCompound tag = stack.getSubCompound("source");
                if (tag != null) {
                    BlockPos source = new BlockPos(
                            tag.getInteger("x"),
                            tag.getInteger("y"),
                            tag.getInteger("z")
                    );
                    String str = String.format("Source: (%d, %d, %d)", source.getX(), source.getY(), source.getZ());
                    list.add(TextFormatting.LIGHT_PURPLE + str);
                }
            }

        }.setRegistryName(MCGProject.ID, "light_bulb");
    }

    public static final PropertyEnum<EnumFacing> FACING = BlockDirectional.FACING;
    public static final PropertyBool POWERED = PropertyBool.create("powered");

    static LinkedList<AxisAlignedBB> AABBs = new LinkedList<>();
    static AxisAlignedBB getAABB(double x1, double y1, double z1, double x2, double y2, double z2) {
        AxisAlignedBB aabb = new AxisAlignedBB(x1, y1, z1, x2, y2, z2);
        for (AxisAlignedBB i : AABBs) {
            if (i.equals(aabb)) return i;
        }
        AABBs.add(aabb);
        return aabb;
    }

    protected AxisAlignedBB getBoxByXYZ(IBlockState state, double x, double y, double z) {
        double x1 = 0.5 - x / 2, y1 = 1 - y, z1 = 0.5 - z / 2;
        double x2 = 0.5 + x / 2, y2 = 1, z2 = 0.5 + z / 2;
        EnumFacing facing = state.getValue(FACING);

        switch (facing) {
            case NORTH:
                return getAABB(1.0 - x2, z1, 1.0 - y2, 1.0 - x1, z2, 1.0 - y1);
            case EAST:
                return getAABB(y1, z1, 1.0 - x2, y2, z2, 1.0 - x1);
            case SOUTH:
                return getAABB(x1, z1, y1, x2, z2, y2);
            case WEST:
                return getAABB(1.0 - y2, z1, x1, 1.0 - y1, z2, x2);
            case UP:
                return getAABB(x1, y1, z1, x2, y2, z2);
            case DOWN:
                return getAABB(x1, 1.0 - y2, z1, x2, 1.0 - y1, z2);
        }

        return Block.FULL_BLOCK_AABB;
    }

    public BlockLightBulb() {
        super(Material.GLASS);
        this.hasTileEntity = true;
        setRegistryName(MCGProject.ID, "light_bulb");
        setHardness(5.0F);
        setResistance(10.0F);
        setSoundType(SoundType.GLASS);
        setLightOpacity(0);
        setLightLevel(0);
        setTranslationKey(MCGProject.ID + "." + "lightBulb");
        setCreativeTab(MCGTabs.FANTASY);

        setDefaultState(
                this.blockState.getBaseState()
                        .withProperty(COLOR, EnumDyeColor.WHITE)
                        .withProperty(FACING, EnumFacing.UP)
                        .withProperty(POWERED, false)
        );
    }

    @Override
    @Nonnull
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
        state = state.getBlock().getActualState(state, world, pos);
        return getBoxByXYZ(state, 0.25, 0.625, 0.25);
    }

    @Override
    @Nonnull
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, COLOR, FACING, POWERED);
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        state = state.getBlock().getActualState(state, world, pos);
        return state.getValue(POWERED) ? 15 : 0;
    }

    @Override
    @Nonnull
    public IBlockState getActualState(@Nonnull IBlockState state, IBlockAccess world, BlockPos pos) {
        state = super.getActualState(state, world, pos);
        for (EnumFacing facing : EnumFacing.VALUES) {
            if (facing.getAxis() != EnumFacing.Axis.Y)
                continue;
            if (world.getBlockState(pos.offset(facing)).getBlock() == BLOCK)
                continue;
            if (world.isSideSolid(pos.offset(facing), facing.getOpposite(), false)) {
                state = state.withProperty(FACING, facing);
                break;
            }
        }
        for (EnumFacing facing : EnumFacing.HORIZONTALS) {
            if (world.getBlockState(pos.offset(facing)).getBlock() == BLOCK)
                continue;
            if (world.isSideSolid(pos.offset(facing), facing.getOpposite(), false)) {
                state = state.withProperty(FACING, facing);
                break;
            }
        }
        return state;
    }

    @Override
    public boolean isSideSolid(IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, EnumFacing facing) {
        state = state.getBlock().getActualState(state, world, pos);
        return state.getValue(FACING).getAxis() == facing.getAxis();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public float getAmbientOcclusionLightValue(IBlockState state) {
        return 1.0F;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    @Nonnull
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase player) {
        IBlockState state = super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, player);
        return state.withProperty(FACING, facing);
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
        if (!world.isRemote) {
            world.scheduleUpdate(pos, this, 10);
        }
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random random) {
        if (!world.isRemote) {
            state = state.getBlock().getActualState(state, world, pos);

            boolean powered = world.isBlockPowered(pos);
            EnumFacing facing = state.getValue(FACING);
            powered |= world.isBlockPowered(pos.offset(facing));

            TileEntity tileEntity = world.getTileEntity(pos);
            if (tileEntity instanceof TileLightBulb) {
                BlockPos source = ((TileLightBulb) tileEntity).getSource();
                if (source != null && !world.isAirBlock(source))
                    powered |= world.isBlockPowered(source);
            }

            boolean lit = state.getValue(POWERED);
            if (lit && !powered) {
                world.setBlockState(pos, state.withProperty(POWERED, false), 2);
            } else if (!lit && powered) {
                world.setBlockState(pos, state.withProperty(POWERED, true), 2);
            }

            world.scheduleUpdate(pos, this, 10);
        }
    }

    @Override
    @Nonnull
    public Item getItemDropped(IBlockState state, Random random, int meta) {
        return ITEM;
    }

    @Override
    @Nonnull
    public ItemStack getItem(World world, BlockPos pos, @Nonnull IBlockState state) {
        return new ItemStack(ITEM, 1, state.getValue(COLOR).getMetadata());
    }

    @Override
    @Nonnull
    protected ItemStack getSilkTouchDrop(@Nonnull IBlockState state) {
        return new ItemStack(ITEM, 1, state.getValue(COLOR).getMetadata());
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(@Nonnull World world, int meta) {
        return new TileLightBulb();
    }

    @Override
    public void breakBlock(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        super.breakBlock(world, pos, state);
        world.removeTileEntity(pos);
    }

    @Override
    public void harvestBlock(@Nonnull World world, EntityPlayer player, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nullable TileEntity tileEntity, ItemStack stack) {
        if (tileEntity instanceof IWorldNameable && ((IWorldNameable)tileEntity).hasCustomName()) {
            player.addStat(Objects.requireNonNull(StatList.getBlockStats(this)));
            player.addExhaustion(0.005F);
            if (world.isRemote) {
                return;
            }

            int level = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack);
            Item dropped = this.getItemDropped(state, world.rand, level);
            if (dropped == Items.AIR) {
                return;
            }

            ItemStack dropStack = new ItemStack(dropped, this.quantityDropped(world.rand));
            dropStack.setStackDisplayName(((IWorldNameable)tileEntity).getName());
            spawnAsEntity(world, pos, dropStack);
        } else {
            super.harvestBlock(world, player, pos, state, null, stack);
        }

    }

    @Override
    public boolean eventReceived(IBlockState state, World world, BlockPos pos, int i, int j) {
        super.eventReceived(state, world, pos, i, j);
        TileEntity tileEntity = world.getTileEntity(pos);
        return tileEntity != null && tileEntity.receiveClientEvent(i, j);
    }

}
