package moe.gensoukyo.mcgproject.cilent.entity;

import moe.gensoukyo.mcgproject.common.feature.farm.stone.EntityLittleRock;
import moe.gensoukyo.mcgproject.common.init.ModItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderLittleStone extends RenderSnowball<EntityLittleRock> {

    public static IRenderFactory<EntityLittleRock> FACTORY = RenderLittleStone::new;

    public RenderLittleStone(RenderManager renderManagerIn) {
        super(renderManagerIn, ModItem.ITEM_LITTLE_STONE, Minecraft.getMinecraft().getRenderItem());
    }

}
