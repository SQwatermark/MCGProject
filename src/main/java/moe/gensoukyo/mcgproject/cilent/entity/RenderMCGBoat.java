package moe.gensoukyo.mcgproject.cilent.entity;

import moe.gensoukyo.mcgproject.common.entity.EntityMCGBoat;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.client.model.IMultipassModel;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBoat;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import javax.annotation.Nonnull;

/**
 * @author drzzm32
 * @date 2020/3/15
 */
public class RenderMCGBoat extends Render<EntityMCGBoat> {

    public static IRenderFactory<EntityMCGBoat> FACTORY = RenderMCGBoat::new;

    private static final ResourceLocation TEXTURE = new ResourceLocation(MCGProject.ID, "textures/entity/mcg_boat.png");
    protected ModelBase modelBoat = new ModelBoat();

    public RenderMCGBoat(RenderManager manager) {
        super(manager);
        this.shadowSize = 0.5F;
    }

    @Override
    public void doRender(@Nonnull EntityMCGBoat boat, double x, double y, double z, float yaw, float lerp) {
        GlStateManager.pushMatrix();
        this.setupTranslation(x, y, z);
        this.setupRotation(boat, yaw, lerp);
        this.bindEntityTexture(boat);
        if (this.renderOutlines) {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(this.getTeamColor(boat));
        }

        this.modelBoat.render(boat, lerp, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
        if (this.renderOutlines) {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }

        GlStateManager.popMatrix();
        super.doRender(boat, x, y, z, yaw, lerp);
    }

    public void setupRotation(EntityBoat boat, float yaw, float lerp) {
        GlStateManager.rotate(180.0F - yaw, 0.0F, 1.0F, 0.0F);
        float time = (float)boat.getTimeSinceHit() - lerp;
        float damage = boat.getDamageTaken() - lerp;
        if (damage < 0.0F) {
            damage = 0.0F;
        }

        if (time > 0.0F) {
            GlStateManager.rotate(MathHelper.sin(time) * time * damage / 10.0F * (float)boat.getForwardDirection(), 1.0F, 0.0F, 0.0F);
        }

        GlStateManager.scale(-1.0F, -1.0F, 1.0F);
    }

    public void setupTranslation(double x, double y, double z) {
        GlStateManager.translate((float)x, (float)y + 0.375F, (float)z);
    }

    @Override
    protected ResourceLocation getEntityTexture(@Nonnull EntityMCGBoat boat) {
        return TEXTURE;
    }

    @Override
    public boolean isMultipass() {
        return true;
    }

    @Override
    public void renderMultipass(EntityMCGBoat boat, double x, double y, double z, float yaw, float lerp) {
        GlStateManager.pushMatrix();
        this.setupTranslation(x, y, z);
        this.setupRotation(boat, yaw, lerp);
        this.bindEntityTexture(boat);
        ((IMultipassModel)this.modelBoat).renderMultipass(boat, lerp, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
        GlStateManager.popMatrix();
    }

}
