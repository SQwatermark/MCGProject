package moe.gensoukyo.mcgproject.common.fluid.blocks;

import moe.gensoukyo.mcgproject.common.init.ModBlock;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber(modid = MCGProject.ID)
public class BlockFluidSludge extends BlockFluidClassic {

    public BlockFluidSludge(Fluid fluid) {
        super(fluid, Material.SAND, MapColor.DIRT);
        this.setRegistryName(MCGProject.ID, fluid.getName());
    }

    @Override
    public void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entity) {
        if (entity instanceof EntityLivingBase) {
            entity.setInWeb();
            entity.posY -= 0.01D;
            if (isEntityInsideBlock(entity, this)) {
                if (entity instanceof EntityPlayer) {
                    entity.attackEntityFrom(new DamageSource("sludge"), (float) (((EntityPlayer) entity).getMaxHealth() * 0.03));
                } else {
                    entity.attackEntityFrom(new DamageSource("sludge"), (float) (((EntityLivingBase) entity).getMaxHealth() * 0.00001));
                }
            }
        }
    }

    public static boolean isEntityInsideBlock(Entity entity, Block block) {
        if (!entity.noClip) {
            BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain();

            for (int i = 0; i < 8; ++i) {
                int j = MathHelper.floor(entity.posY + (double) (((float) ((i) % 2) - 0.5F) * 0.1F) + (double) entity.getEyeHeight());
                int k = MathHelper.floor(entity.posX + (double) (((float) ((i >> 1) % 2) - 0.5F) * entity.width * 0.8F));
                int l = MathHelper.floor(entity.posZ + (double) (((float) ((i >> 2) % 2) - 0.5F) * entity.width * 0.8F));

                if (blockpos$pooledmutableblockpos.getX() != k || blockpos$pooledmutableblockpos.getY() != j || blockpos$pooledmutableblockpos.getZ() != l) {
                    blockpos$pooledmutableblockpos.setPos(k, j, l);

                    if (entity.world.getBlockState(blockpos$pooledmutableblockpos).getBlock() == block) {
                        blockpos$pooledmutableblockpos.release();
                        return true;
                    }
                }
            }
            blockpos$pooledmutableblockpos.release();
        }
        return false;
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void renderEntityView(RenderHandEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        if (BlockFluidSludge.isEntityInsideBlock(mc.player, ModBlock.BLOCK_FLUID_SLUDGE)) {
            boolean flag = mc.getRenderViewEntity() instanceof EntityLivingBase && ((EntityLivingBase) mc.getRenderViewEntity()).isPlayerSleeping();
            if (mc.gameSettings.thirdPersonView == 0 && !flag) {
                GlStateManager.disableAlpha();
                {
                    IBlockState iblockstate = mc.world.getBlockState(new BlockPos(mc.player).up());
                    TextureAtlasSprite sprite = mc.getBlockRendererDispatcher().getBlockModelShapes().getTexture(iblockstate);
                    Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
                    Tessellator tessellator = Tessellator.getInstance();
                    BufferBuilder bufferbuilder = tessellator.getBuffer();
                    GlStateManager.color(0.5F, 0.3F, 0.3F, 0.5F);
                    GlStateManager.pushMatrix();
                    float f6 = sprite.getMinU();
                    float f7 = sprite.getMaxU();
                    float f8 = sprite.getMinV();
                    float f9 = sprite.getMaxV();
                    bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
                    bufferbuilder.pos(-1.0D, -1.0D, -0.5D).tex(f7, f9).endVertex();
                    bufferbuilder.pos(1.0D, -1.0D, -0.5D).tex(f6, f9).endVertex();
                    bufferbuilder.pos(1.0D, 1.0D, -0.5D).tex(f6, f8).endVertex();
                    bufferbuilder.pos(-1.0D, 1.0D, -0.5D).tex(f7, f8).endVertex();
                    tessellator.draw();
                    GlStateManager.popMatrix();
                    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                }
                GlStateManager.enableAlpha();
            }
        }
    }
}
