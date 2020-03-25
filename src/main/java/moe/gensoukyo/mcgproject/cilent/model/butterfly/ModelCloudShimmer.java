package moe.gensoukyo.mcgproject.cilent.model.butterfly;

import moe.gensoukyo.mcgproject.common.util.MathMCG;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

import static moe.gensoukyo.mcgproject.common.util.MathMCG.degToRad;

/**
 * ModelButterfly - Either Mojang or a mod author
 * Created using Tabula 7.0.0
 */
public class ModelCloudShimmer extends ModelBase {
    public ModelRenderer Head;
    public ModelRenderer Wing4;
    public ModelRenderer Wing3;
    public ModelRenderer lowerbody;
    public ModelRenderer Czulko1;
    public ModelRenderer Body;

    public ModelCloudShimmer() {
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.Wing3 = new ModelRenderer(this, 47, 30);
        this.Wing3.setRotationPoint(0.0F, 17.0F, -12.6F);
        this.Wing3.addBox(-22.0F, 0.0F, 0.0F, 21, 0, 34, 0.0F);
        this.lowerbody = new ModelRenderer(this, 0, 46);
        this.lowerbody.setRotationPoint(-1.0F, 16.5F, 1.0F);
        this.lowerbody.addBox(0.0F, 0.0F, 0.0F, 2, 2, 12, 0.0F);
        this.Body = new ModelRenderer(this, 0, 18);
        this.Body.setRotationPoint(-2.0F, 16.0F, -7.0F);
        this.Body.addBox(0.0F, 0.0F, 0.0F, 4, 4, 8, 0.0F);
        this.Wing4 = new ModelRenderer(this, 4, 0);
        this.Wing4.setRotationPoint(0.8F, 17.0F, -12.6F);
        this.Wing4.addBox(0.0F, 0.0F, 0.0F, 21, 0, 34, 0.0F);
        this.Czulko1 = new ModelRenderer(this, 0, 30);
        this.Czulko1.setRotationPoint(-3.0F, 14.1F, -14.7F);
        this.Czulko1.addBox(0.0F, 0.0F, 0.0F, 6, 0, 6, 0.0F);
        this.setRotateAngle(Czulko1, -0.2617993877991494F, 0.0F, 0.0F);
        this.Head = new ModelRenderer(this, 0, 38);
        this.Head.setRotationPoint(-2.0F, 15.5F, -10.0F);
        this.Head.addBox(0.0F, 0.0F, 0.0F, 4, 4, 4, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.Wing3.render(f5);
        this.lowerbody.render(f5);
        this.Body.render(f5);
        this.Wing4.render(f5);
        this.Czulko1.render(f5);
        this.Head.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
    
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
    {
        float swing;
        if(entity.motionY!=0D||entity.motionX!=0D||entity.motionZ!=0D) {
            swing = MathMCG.swing(f2, 0.6f, 1);
            Wing4.rotateAngleZ= swing;
            Wing3.rotateAngleZ=-swing;
        }else {
            swing = MathMCG.swing(f2, 0.05f, 0.05f);
            Wing4.rotateAngleZ=-degToRad(70)- swing;
            Wing3.rotateAngleZ= degToRad(70)+ swing;
        }
    }


}
