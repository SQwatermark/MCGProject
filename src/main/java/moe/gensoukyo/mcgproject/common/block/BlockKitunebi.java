package moe.gensoukyo.mcgproject.common.block;

import moe.gensoukyo.mcgproject.common.creativetab.MCGTabs;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author drzzm32
 * @date 2020/5/14
 */
public class BlockKitunebi extends Block implements ITileEntityProvider {

    public static class TileKitunebi extends TileEntity {

        @Override
        public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
            return oldState.getBlock() != newState.getBlock();
        }

        @Override
        public boolean hasFastRenderer() {
            return true;
        }

    }

    public static final PropertyInteger LIGHT = PropertyInteger.create("light", 0, 15);

    public BlockKitunebi() {
        super(Material.BARRIER);
        setLightLevel(1.0F);
        setLightOpacity(0);
        setBlockUnbreakable();
        setResistance(0xFFFFFF);
        setTranslationKey(MCGProject.ID + "." + "kitunebi");
        setRegistryName(MCGProject.ID, "kitunebi");
        setCreativeTab(MCGTabs.FANTASY);
        this.translucent = true;
        this.hasTileEntity = true;
        setDefaultState(this.blockState.getBaseState().withProperty(LIGHT, 15));
    }

    public static boolean heldThis(ItemStack stack) {
        if (stack.getItem() instanceof ItemBlock) {
            return ((ItemBlock) stack.getItem()).getBlock() instanceof BlockKitunebi;
        }
        return false;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World world, int i) {
        return new TileKitunebi();
    }

    @Override
    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos) {
        return NULL_AABB;
    }

    @Override
    public boolean canCollideCheck(IBlockState state, boolean v) {
        if (MCGProject.isServer())
            return false;
        else {
            return heldThis(Minecraft.getMinecraft().player.getHeldItemMainhand());
        }
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
    @SideOnly(Side.CLIENT)
    public float getAmbientOcclusionLightValue(IBlockState state) {
        return 1.0F;
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        state = world.getBlockState(pos);
        return state.getValue(LIGHT);
    }

    @Override
    @Nonnull
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(LIGHT, 0xF - meta);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return 0xF - (state.getValue(LIGHT) & 0xF);
    }

    @Override
    @Nonnull
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, LIGHT);
    }

    @Override
    public boolean isSideSolid(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing facing) {
        return false;
    }

    @Override
    @Nonnull
    public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing facing) {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    @Nonnull
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.INVISIBLE;
    }

    @Override
    public boolean canBeReplacedByLeaves(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos) {
        return false;
    }

    @Override
    public void dropBlockAsItemWithChance(World world, @Nonnull BlockPos pos, @Nonnull IBlockState state, float f, int i) {
    }

    @Override
    public boolean onBlockActivated(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state,
                                    @Nonnull EntityPlayer player, @Nonnull EnumHand hand, @Nonnull EnumFacing facing, float x, float y, float z) {

        if (heldThis(player.getHeldItem(hand))) {
            if (!world.isRemote) {
                state = world.getBlockState(pos);
                int light = state.getValue(LIGHT);
                light += 1; light &= 0xF;
                world.setBlockState(pos, state.withProperty(LIGHT, light), 2);
            }

            return true;
        }

        return false;
    }

}
