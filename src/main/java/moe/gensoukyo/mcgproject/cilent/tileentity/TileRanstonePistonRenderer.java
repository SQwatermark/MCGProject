package moe.gensoukyo.mcgproject.cilent.tileentity;

import moe.gensoukyo.mcgproject.common.feature.ranstone.RanstonePiston;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockPistonExtension;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TileRanstonePistonRenderer extends TileEntitySpecialRenderer<RanstonePiston.TilePiston> {
    private BlockRendererDispatcher blockRenderer;

    public TileRanstonePistonRenderer() {
    }

    public void render(RanstonePiston.TilePiston p_render_1_, double p_render_2_, double p_render_4_, double p_render_6_, float p_render_8_, int p_render_9_, float p_render_10_) {
        if (this.blockRenderer == null) {
            this.blockRenderer = Minecraft.getMinecraft().getBlockRendererDispatcher();
        }

        BlockPos blockpos = p_render_1_.getPos();
        IBlockState iblockstate = p_render_1_.getPistonState();
        Block block = iblockstate.getBlock();
        if (iblockstate.getMaterial() != Material.AIR && p_render_1_.getProgress(p_render_8_) < 1.0F) {
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuffer();
            this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            RenderHelper.disableStandardItemLighting();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            GlStateManager.enableBlend();
            GlStateManager.disableCull();
            if (Minecraft.isAmbientOcclusionEnabled()) {
                GlStateManager.shadeModel(7425);
            } else {
                GlStateManager.shadeModel(7424);
            }

            bufferbuilder.begin(7, DefaultVertexFormats.BLOCK);
            bufferbuilder.setTranslation(p_render_2_ - (double)blockpos.getX() + (double)p_render_1_.getOffsetX(p_render_8_), p_render_4_ - (double)blockpos.getY() + (double)p_render_1_.getOffsetY(p_render_8_), p_render_6_ - (double)blockpos.getZ() + (double)p_render_1_.getOffsetZ(p_render_8_));
            World world = this.getWorld();
            if (block == RanstonePiston.Extension.BLOCK && p_render_1_.getProgress(p_render_8_) <= 0.25F) {
                iblockstate = iblockstate.withProperty(BlockPistonExtension.SHORT, true);
                this.renderStateModel(blockpos, iblockstate, bufferbuilder, world, true);
            } else if (p_render_1_.shouldPistonHeadBeRendered() && !p_render_1_.isExtending()) {
                BlockPistonExtension.EnumPistonType blockpistonextension$enumpistontype = BlockPistonExtension.EnumPistonType.DEFAULT;
                IBlockState iblockstate1 = RanstonePiston.Extension.BLOCK.getDefaultState().withProperty(BlockPistonExtension.TYPE, blockpistonextension$enumpistontype).withProperty(BlockPistonExtension.FACING, iblockstate.getValue(BlockPistonBase.FACING));
                iblockstate1 = iblockstate1.withProperty(BlockPistonExtension.SHORT, p_render_1_.getProgress(p_render_8_) >= 0.5F);
                this.renderStateModel(blockpos, iblockstate1, bufferbuilder, world, true);
                bufferbuilder.setTranslation(p_render_2_ - (double)blockpos.getX(), p_render_4_ - (double)blockpos.getY(), p_render_6_ - (double)blockpos.getZ());
                iblockstate = iblockstate.withProperty(BlockPistonBase.EXTENDED, true);
                this.renderStateModel(blockpos, iblockstate, bufferbuilder, world, true);
            } else {
                this.renderStateModel(blockpos, iblockstate, bufferbuilder, world, false);
            }

            bufferbuilder.setTranslation(0.0D, 0.0D, 0.0D);
            tessellator.draw();
            RenderHelper.enableStandardItemLighting();
        }

    }

    private boolean renderStateModel(BlockPos p_renderStateModel_1_, IBlockState p_renderStateModel_2_, BufferBuilder p_renderStateModel_3_, World p_renderStateModel_4_, boolean p_renderStateModel_5_) {
        return this.blockRenderer.getBlockModelRenderer().renderModel(p_renderStateModel_4_, this.blockRenderer.getModelForState(p_renderStateModel_2_), p_renderStateModel_2_, p_renderStateModel_1_, p_renderStateModel_3_, p_renderStateModel_5_);
    }
}
