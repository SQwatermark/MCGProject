package moe.gensoukyo.mcgproject.cilent.model.butterfly;

import moe.gensoukyo.mcgproject.common.util.MathMCG;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

import static moe.gensoukyo.mcgproject.common.util.MathMCG.degToRad;

/**
 * test - SQwatermark
 * Created using Tabula 7.0.1
 */
public class ModelTest extends ModelBase {
    public ModelRenderer body;
    public ModelRenderer wing1;
    public ModelRenderer wing2;

    public ModelTest() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.wing2 = new ModelRenderer(this, -6, 17);
        this.wing2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.wing2.addBox(-10.0F, 0.0F, -6.0F, 10, 0, 12, 0.0F);
        this.body = new ModelRenderer(this, -12, 0);
        this.body.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body.addBox(-1.0F, 0.0F, -6.0F, 2, 1, 12, 0.0F);
        this.wing1 = new ModelRenderer(this, 6, 0);
        this.wing1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.wing1.addBox(0.0F, 0.0F, -6.0F, 10, 0, 12, 0.0F);
        this.setRotateAngle(wing1, 0.0F, 0.0F, -2.4802620430283604E-16F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.wing2.render(f5);
        this.body.render(f5);
        this.wing1.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
    {
        float swing;
        if(entity.motionY!=0D||entity.motionX!=0D||entity.motionZ!=0D) {
            swing = MathMCG.swing(f2, 0.6f, 1);
            wing1.rotateAngleZ= swing;
            wing2.rotateAngleZ=-swing;
        }else {
            swing = MathMCG.swing(f2, 0.05f, 0.05f);
            wing1.rotateAngleZ=-degToRad(70)- swing;
            wing2.rotateAngleZ= degToRad(70)+ swing;
        }
    }

}
