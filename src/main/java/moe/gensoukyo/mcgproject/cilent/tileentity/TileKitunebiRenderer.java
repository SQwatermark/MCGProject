package moe.gensoukyo.mcgproject.cilent.tileentity;


import moe.gensoukyo.mcgproject.common.block.BlockKitunebi;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.animation.FastTESR;

import javax.annotation.Nonnull;

import static moe.gensoukyo.mcgproject.common.block.BlockKitunebi.heldThis;

/**
 * @author drzzm32
 * @date 2020/5/14
 */
public class TileKitunebiRenderer extends FastTESR<BlockKitunebi.TileKitunebi> {

    @Override
    public void renderTileEntityFast(
            @Nonnull BlockKitunebi.TileKitunebi tile,
             double x, double y, double z,
            float partialTicks, int destroyStage, float partial,
            @Nonnull BufferBuilder buffer) {
        Minecraft mc = Minecraft.getMinecraft();
        if (heldThis(mc.player.getHeldItemMainhand())) {
            BlockRendererDispatcher dispatcher = mc.getBlockRendererDispatcher();
            BlockModelRenderer renderer = dispatcher.getBlockModelRenderer();
            World world = tile.getWorld(); BlockPos pos = tile.getPos();
            IBlockState state = world.getBlockState(pos);
            IBakedModel model = dispatcher.getModelForState(state);

            x = x - pos.getX(); y = y - pos.getY(); z = z - pos.getZ();

            buffer.setTranslation(x, y, z);
            renderer.renderModel(world, model, state, pos, buffer, false);
            buffer.setTranslation(0, 0, 0);
        }
    }

}
