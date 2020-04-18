package moe.gensoukyo.mcgproject.common.feature.futuremc;

import moe.gensoukyo.mcgproject.common.creativetab.MCGTabs;
import moe.gensoukyo.mcgproject.common.init.ModItem;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.*;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BlockBamboo extends Block {

    private static final PropertyBool THICK = PropertyBool.create("thick");
    public static PropertyInteger LEAVES = PropertyInteger.create("leaves", 0, 2);
    private static final PropertyBool MATURE = PropertyBool.create("mature");

    public static final AxisAlignedBB TIGHT_THICK_AABB = new AxisAlignedBB(0.4, 0.0, 0.4, 0.6, 1.0, 0.6);
    public static final AxisAlignedBB TIGHT_THIN_AABB = new AxisAlignedBB(0.43, 0.0, 0.43, 0.57, 1.0, 0.57);

    public BlockBamboo() {
        super(Material.WOOD);
        this.setRegistryName("minecraftfuture", "bamboo");
        this.setCreativeTab(MCGTabs.NORMAL);
        this.setTranslationKey("minecraftfuture" + "." + "bamboo");
        this.setSoundType(SoundType.WOOD);
        this.setHardness(2F);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, THICK, LEAVES, MATURE);
    }

    @Override
    public int getMetaFromState(@NotNull IBlockState state)
    {
        int meta = 0;
        if (state.getValue(MATURE)) {
            meta += 6;
        }
        meta += state.getValue(LEAVES);
        if (state.getValue(THICK)) {
            meta += 3;
        }
        return meta;
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(LEAVES, meta % 3).withProperty(MATURE, meta / 6 != 0).withProperty(THICK, (meta % 6) / 3 != 0);
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getStateFromMeta(placer.getHeldItemMainhand().getMetadata());
    }

    @Override
    public ItemStack getPickBlock(@NotNull IBlockState state, RayTraceResult target, @NotNull World world, @NotNull BlockPos pos, EntityPlayer player)
    {
        return new ItemStack(Item.getItemFromBlock(this), 1, this.getMetaFromState(state));
    }

    @Nonnull
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        Vec3d vec3d = state.getOffset(source, pos);
        return state.getValue(THICK) ? TIGHT_THICK_AABB.offset(vec3d) : TIGHT_THIN_AABB.offset(vec3d);
    }

    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, @Nonnull IBlockAccess worldIn, @Nonnull BlockPos pos) {
        Vec3d vec3d = state.getOffset(worldIn, pos);
        return state.getValue(THICK) ? TIGHT_THICK_AABB.offset(vec3d) : TIGHT_THIN_AABB.offset(vec3d);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    @Nonnull
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    @Nonnull
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public Vec3d getOffset(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        long i = MathHelper.getCoordinateRandom(pos.getX(), 0, pos.getZ());
        return new  Vec3d(((i >> 16 & 15L) / 15.0f - 0.5) * 0.5, 0.0, ((i >> 24 & 15L) / 15.0f - 0.5) * 0.5);
    }


    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (player.getHeldItem(hand).getItem() == Items.DYE && player.getHeldItem(hand).getMetadata() == 15) {
            ModItem.ITEM_META_CHANGER.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
            return true;
        }
        return false;
    }
}