package moe.gensoukyo.mcgproject.cilent.entity;

import moe.gensoukyo.mcgproject.common.feature.littlestone.EntityLittleStone;
import moe.gensoukyo.mcgproject.common.init.ModItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderLittleStone extends RenderSnowball<EntityLittleStone> {

    public static final Factory FACTORY = new Factory();

    public RenderLittleStone(RenderManager renderManagerIn) {
        super(renderManagerIn, ModItem.ITEM_LITTLE_STONE, Minecraft.getMinecraft().getRenderItem());
    }

    public static class Factory implements IRenderFactory<EntityLittleStone> {
        @Override
        public Render<? super EntityLittleStone> createRenderFor(RenderManager manager) {
            return new RenderLittleStone(manager);
        }
    }

}
