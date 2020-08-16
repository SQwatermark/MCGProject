package moe.gensoukyo.mcgproject.cilent.tileentity;

import moe.gensoukyo.mcgproject.common.feature.backpack.GensoChest;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

import javax.annotation.Nonnull;

/**
 * @author drzzm32
 * @date 2020/5/17
 */
public class TileGensoChestRenderer extends TileEntitySpecialRenderer<GensoChest.TileGensoChest> {

    @Override
    public void render(@Nonnull GensoChest.TileGensoChest chest, double x, double y, double z, float partialTicks, int destroyStage, float partial) {
        super.render(chest, x, y, z, partialTicks, destroyStage, partial);
    }

}
