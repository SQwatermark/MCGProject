package moe.gensoukyo.mcgproject.common.block;

import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

public class BlockMCGSlab extends net.minecraft.block.BlockSlab {

    public final IBlockState baseBlockState;

    public BlockMCGSlab(Block block, int meta, String registryName)
    {
        super(block.getMaterial(block.getDefaultState()));
        this.baseBlockState = block.getStateFromMeta(meta);
        this.setTranslationKey(MCGProject.ID + "." + registryName);
        this.setRegistryName(registryName);
        this.setHardness(2.0F);
        this.setCreativeTab(block.getCreativeTab());
        this.setSoundType(block.getSoundType());
        this.useNeighborBrightness = true;
    }

    public BlockMCGSlab(Block block, int meta, String registryName, CreativeTabs tab)
    {
        super(block.getMaterial(block.getDefaultState()));
        this.baseBlockState = block.getStateFromMeta(meta);
        this.setTranslationKey(MCGProject.ID + "." + registryName);
        this.setRegistryName(registryName);
        this.setHardness(2.0F);
        this.setCreativeTab(tab);
        this.setSoundType(block.getSoundType());
        this.useNeighborBrightness = true;
    }

    @Override
    public String getLocalizedName()
    {
        String baseName;
        if (baseBlockState != null)
            baseName = baseBlockState.getBlock().getLocalizedName();
        else
            baseName = I18n.translateToLocal(getTranslationKey() + ".name");
        String blockType = getClass().getSimpleName().replace("Block", "");
        return baseName + I18n.translateToLocal("trans." + blockType + ".name");
    }

    @Override
    public boolean isDouble() {
        return false;
    }

    @Override
    public String getTranslationKey(int meta) {
        return super.getTranslationKey();
    }

    @Override
    public IProperty<?> getVariantProperty() {
        return null;
    }

    @Override
    public Comparable<?> getTypeForItem(ItemStack stack) {
        return null;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (!world.isRemote) {
            if (player.getHeldItemMainhand().getItem() == ItemBlock.getItemFromBlock(this)) {
                if (hitY == 0.5) {
                    world.setBlockState(pos, baseBlockState);
                    world.markBlockRangeForRenderUpdate(pos, pos);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        IBlockState state = this.getDefaultState();
        state = state.withProperty(HALF, (meta & 8) == 0 ? EnumBlockHalf.BOTTOM : EnumBlockHalf.TOP);

        return state;
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        int meta = 0;
        if (state.getValue(HALF) == EnumBlockHalf.TOP) {
            meta |= 8;
        }
        return meta;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, HALF);
    }

}