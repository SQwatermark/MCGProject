package moe.gensoukyo.mcgproject.cilent.entity.boat;

import moe.gensoukyo.mcgproject.cilent.model.RendererHelper;
import moe.gensoukyo.mcgproject.cilent.model.obj.WavefrontObject;
import moe.gensoukyo.mcgproject.common.entity.boat.EntityRACBoat;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL45;

import javax.annotation.Nonnull;

/**
 * @author drzzm32
 * @date 2020/3/27
 */
public class RenderRACBoat extends Render<EntityRACBoat> {

    public static IRenderFactory<EntityRACBoat> FACTORY = RenderRACBoat::new;

    private static final ResourceLocation TEXTURE = new ResourceLocation(MCGProject.ID, "textures/entity/boat/rac_boat.png");
    private static final WavefrontObject MODEL = new WavefrontObject(
            new ResourceLocation(MCGProject.ID, "models/entity/boat/rac_boat.obj"));

    private static final String _screen = "rac_boat";

    private static final WavefrontObject modelMeterV = new WavefrontObject(
            new ResourceLocation(MCGProject.ID, "models/entity/boat/" + _screen + "_meter_v.obj")
    );
    private static final ResourceLocation textureMeterV = new ResourceLocation(
            MCGProject.ID, "textures/entity/boat/" + _screen + "_meter_v.png"
    );

    private static final WavefrontObject modelMeterA = new WavefrontObject(
            new ResourceLocation(MCGProject.ID, "models/entity/boat/" + _screen + "_meter_a.obj")
    );
    private static final ResourceLocation textureMeterA = new ResourceLocation(
            MCGProject.ID, "textures/entity/boat/" + _screen + "_meter_a.png"
    );

    private static final WavefrontObject modelMeterPointer = new WavefrontObject(
            new ResourceLocation(MCGProject.ID, "models/entity/boat/" + _screen + "_meter_pointer.obj")
    );
    private static final ResourceLocation textureMeterPointer = new ResourceLocation(
            MCGProject.ID, "textures/entity/boat/" + _screen + "_meter_pointer.png"
    );

    private static final float ANGLE_HALF = 143;

    public RenderRACBoat(RenderManager manager) {
        super(manager);
        this.shadowSize = 0.5F;
    }

    @Override
    public void doRender(@Nonnull EntityRACBoat boat, double x, double y, double z, float yaw, float lerp) {
        GlStateManager.pushMatrix();
        this.setupTranslation(x, y, z);
        this.setupRotation(boat, yaw, lerp);
        this.bindEntityTexture(boat);
        if (this.renderOutlines) {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(this.getTeamColor(boat));
        }

        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, -0.125F, 0.0F);
        GL11.glPushMatrix();
        GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
        GL11.glPushMatrix();
        GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
        int c = boat.getBoatColor();
        GL11.glColor3f(
                ((c & 0xFF0000) >> 16) / 255.0F,
                ((c & 0x00FF00) >> 8) / 255.0F,
                (c & 0x0000FF) / 255.0F
        );
        RendererHelper.renderWithResource(MODEL, TEXTURE);
        GL11.glColor3f(1.0F, 1.0F, 1.0F);

        renderHUD(boat);
        GL11.glPopMatrix();
        GL11.glPopMatrix();
        GL11.glPopMatrix();

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
    protected ResourceLocation getEntityTexture(@Nonnull EntityRACBoat boat) {
        return TEXTURE;
    }

    private void renderHUD(EntityRACBoat boat) {
        GL11.glPushMatrix();
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
        GL11.glTranslatef(0.0F, -0.5F, 0.0F);
        doRenderHUD(boat);
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
        GL11.glPopMatrix();
    }

    private void doRenderHUD(EntityRACBoat boat) {
        float v = (float) boat.vel;
        float a = (float) (boat.vel - boat.prevVel);
        float angle;

        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        RendererHelper.renderWithResource(modelMeterV, textureMeterV);
        angle = v / 9.0F * ANGLE_HALF * 2 - ANGLE_HALF;
        if (angle > ANGLE_HALF) angle = ANGLE_HALF;
        GL11.glPushMatrix();
        GL11.glTranslatef(0.625F, 0.9375F, -0.625F);
        GL11.glTranslatef(-0.00625F, 0.0F, 0.00625F);
        GL11.glPushMatrix();
        GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
        GL11.glPushMatrix();
        GL11.glRotatef(angle, 1.0F, 0.0F, 0.0F);
        RendererHelper.renderWithResource(modelMeterPointer, textureMeterPointer);
        GL11.glPopMatrix();
        GL11.glPopMatrix();
        GL11.glPopMatrix();

        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        RendererHelper.renderWithResource(modelMeterA, textureMeterA);
        angle = a / 0.6F * ANGLE_HALF;
        if (Math.abs(angle) > ANGLE_HALF) angle = Math.signum(angle) * ANGLE_HALF;
        GL11.glPushMatrix();
        GL11.glTranslatef(0.625F, 0.9375F, 0.625F);
        GL11.glTranslatef(-0.00625F, 0.0F, -0.00625F);
        GL11.glPushMatrix();
        GL11.glRotatef(-45.0F, 0.0F, 1.0F, 0.0F);
        GL11.glPushMatrix();
        GL11.glRotatef(angle, 1.0F, 0.0F, 0.0F);
        RendererHelper.renderWithResource(modelMeterPointer, textureMeterPointer);
        GL11.glPopMatrix();
        GL11.glPopMatrix();
        GL11.glPopMatrix();

        GlStateManager.disableAlpha();
        GlStateManager.disableBlend();
    }

}
