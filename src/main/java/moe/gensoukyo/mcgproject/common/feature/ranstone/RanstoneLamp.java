package moe.gensoukyo.mcgproject.common.feature.ranstone;

import moe.gensoukyo.mcgproject.common.tileentity.AbstractTileEntity;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemCloth;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldNameable;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Random;

public class RanstoneLamp extends BlockColored implements ITileEntityProvider {

    public static RanstoneLamp BLOCK;
    public static RanstoneLamp BLOCK_LIT;
    public static RanstoneLamp BLOCK_ALWAYS;
    public static Item ITEM;
    public static Item ITEM_ALWAYS;

    public static void initBlock() {
        BLOCK = new RanstoneLamp(false, false);
        BLOCK_LIT = new RanstoneLamp(true, false);
        BLOCK_ALWAYS = new RanstoneLamp(true, true);

        BLOCK_LIT.setCreativeTab(null);
    }
    public static void initItem() {
        ITEM = new ItemCloth(BLOCK).setRegistryName(MCGProject.ID, "ranstone_lamp");
        ITEM_ALWAYS = new ItemCloth(BLOCK_ALWAYS).setRegistryName(MCGProject.ID, "ranstone_lamp_always");
    }

    public final boolean isLit;
    public final boolean alwaysLit;

    public RanstoneLamp(boolean lit, boolean alwaysLit) {
        super(Material.GLASS);
        this.hasTileEntity = true;
        this.isLit = lit;
        this.alwaysLit = alwaysLit;
        setRegistryName(MCGProject.ID, "ranstone_lamp" + (alwaysLit ? "_always" : (lit ? "_lit" : "")));
        setHardness(5.0F);
        setResistance(10.0F);
        setSoundType(SoundType.GLASS);
        setLightLevel(lit ? 1.0F : 0.0F);
        setTranslationKey(MCGProject.ID + "." + "ranstoneLamp" + (alwaysLit ? "Always" : (lit ? "Lit" : "")));
        setCreativeTab(CreativeTabs.REDSTONE);
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
        if (!world.isRemote && !this.alwaysLit) {
            if (this.isLit && !world.isBlockPowered(pos)) {
                world.setBlockState(pos, BLOCK.getDefaultState().withProperty(COLOR, state.getValue(COLOR)), 2);
            } else if (!this.isLit && world.isBlockPowered(pos)) {
                world.setBlockState(pos, BLOCK_LIT.getDefaultState().withProperty(COLOR, state.getValue(COLOR)), 2);
            }
        }
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block target, BlockPos tarPos) {
        if (!world.isRemote && !this.alwaysLit) {
            if (this.isLit && !world.isBlockPowered(pos)) {
                world.scheduleUpdate(pos, this, 4);
            } else if (!this.isLit && world.isBlockPowered(pos)) {
                world.setBlockState(pos, BLOCK_LIT.getDefaultState().withProperty(COLOR, state.getValue(COLOR)), 2);
            }
        }
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random random) {
        if (!world.isRemote && !this.alwaysLit) {
            if (this.isLit && !world.isBlockPowered(pos)) {
                world.setBlockState(pos, BLOCK.getDefaultState().withProperty(COLOR, state.getValue(COLOR)), 2);
            }
        }
    }

    @Override
    @Nonnull
    public Item getItemDropped(IBlockState state, Random random, int meta) {
        return this.alwaysLit ? ITEM_ALWAYS : ITEM;
    }

    @Override
    @Nonnull
    public ItemStack getItem(World world, BlockPos pos, @Nonnull IBlockState state) {
        return new ItemStack(this.alwaysLit ? ITEM_ALWAYS : ITEM, 1, state.getValue(COLOR).getMetadata());
    }

    @Override
    @Nonnull
    protected ItemStack getSilkTouchDrop(@Nonnull IBlockState state) {
        return new ItemStack(this.alwaysLit ? ITEM_ALWAYS : ITEM, 1, state.getValue(COLOR).getMetadata());
    }

    public static class TileRanstoneLamp extends AbstractTileEntity {

        public final double randomScale = Math.random() * 0.01 + 0.995;

        @Override
        public void fromNBT(NBTTagCompound tag) { }

        @Nonnull
        @Override
        public NBTTagCompound toNBT(NBTTagCompound tag) { return tag; }

        public boolean isLit() {
            Block block = getBlockType();
            if (block instanceof RanstoneLamp)
                return ((RanstoneLamp) block).isLit;
            return false;
        }

        @Override
        public double getMaxRenderDistanceSquared() {
            return 256.0D;
        }

    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(@Nonnull World world, int meta) {
        return new TileRanstoneLamp();
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
