package moe.gensoukyo.mcgproject.cilent.entity;

import moe.gensoukyo.mcgproject.cilent.model.ModelApple;
import moe.gensoukyo.mcgproject.common.feature.farm.apple.EntityApple;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import javax.annotation.Nonnull;
import java.util.Random;

public class RenderApple extends Render<EntityApple> {

    public static IRenderFactory<EntityApple> FACTORY = RenderApple::new;

    private static final ResourceLocation TEXTURE = new ResourceLocation("textures/blocks/redstone_block.png");
    protected ModelBase modelApple = new ModelApple();
    Random random = new Random();
    float hy;
    float hp;

    public RenderApple(RenderManager manager) {
        super(manager);
        this.shadowSize = 0.2F;
        this.hy = random.nextFloat();
        this.hp = random.nextFloat();
    }

    @Override
    public void doRender(@Nonnull EntityApple apple, double x, double y, double z, float yaw, float lerp) {
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)x, (float)y - 0.95, (float)z);
        this.bindEntityTexture(apple);
        if (this.renderOutlines) {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(this.getTeamColor(apple));
        }
        this.modelApple.render(apple, lerp, 0.0F, -0.1F, hy, hp, 0.06F);
        if (this.renderOutlines) {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }

        GlStateManager.popMatrix();
        super.doRender(apple, x, y, z, yaw, lerp);


    }


    @Override
    protected ResourceLocation getEntityTexture(@Nonnull EntityApple boat) {
        return TEXTURE;
    }


}
