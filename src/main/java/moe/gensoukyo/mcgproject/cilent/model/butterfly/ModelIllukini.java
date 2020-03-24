package moe.gensoukyo.mcgproject.cilent.model.butterfly;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelIllukini extends ModelBase {
    public ModelRenderer Head;
    public ModelRenderer Wing4;
    public ModelRenderer Head1;
    public ModelRenderer Czulko_1;
    public ModelRenderer lowerbody;
    public ModelRenderer Head2;
    public ModelRenderer Body;
    public ModelRenderer Wing4_1;

    public ModelIllukini() {
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.lowerbody = new ModelRenderer(this, 0, 46);
        this.lowerbody.setRotationPoint(-1.9F, 21.8F, 1.0F);
        this.lowerbody.addBox(0.0F, 0.0F, 0.0F, 3, 2, 12, 0.0F);
        this.Czulko_1 = new ModelRenderer(this, 24, 48);
        this.Czulko_1.setRotationPoint(-3.3F, 18.2F, -15.0F);
        this.Czulko_1.addBox(0.0F, 0.0F, 0.0F, 6, 0, 8, 0.0F);
        this.setRotateAngle(Czulko_1, -0.36425021489121656F, 0.0F, 0.0F);
        this.Wing4_1 = new ModelRenderer(this, 58, 34);
        this.Wing4_1.setRotationPoint(-1.6F, 22.0F, -11.4F);
        this.Wing4_1.addBox(-16.0F, 0.0F, 0.0F, 16, 0, 28, 0.0F);
        this.Head2 = new ModelRenderer(this, 16, 36);
        this.Head2.setRotationPoint(-2.8F, 20.49F, -10.0F);
        this.Head2.addBox(0.0F, 0.0F, 0.0F, 2, 2, 2, 0.0F);
        this.Body = new ModelRenderer(this, 0, 18);
        this.Body.setRotationPoint(-2.4F, 21.5F, -5.3F);
        this.Body.addBox(0.0F, 0.0F, 0.0F, 4, 3, 8, 0.0F);
        this.Wing4 = new ModelRenderer(this, 10, 4);
        this.Wing4.setRotationPoint(0.8F, 22.0F, -11.4F);
        this.Wing4.addBox(0.0F, 0.0F, 0.0F, 16, 0, 28, 0.0F);
        this.Head = new ModelRenderer(this, 0, 38);
        this.Head.setRotationPoint(-2.3F, 20.5F, -8.9F);
        this.Head.addBox(0.0F, 0.0F, 0.0F, 4, 4, 4, 0.0F);
        this.Head1 = new ModelRenderer(this, 16, 36);
        this.Head1.setRotationPoint(0.2F, 20.49F, -10.0F);
        this.Head1.addBox(0.0F, 0.0F, 0.0F, 2, 2, 2, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.lowerbody.render(f5);
        this.Czulko_1.render(f5);
        this.Wing4_1.render(f5);
        this.Head2.render(f5);
        this.Body.render(f5);
        this.Wing4.render(f5);
        this.Head.render(f5);
        this.Head1.render(f5);
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

    	if(entity.motionY!=0D||entity.motionX!=0D||entity.motionZ!=0D) {
        	Wing4.rotateAngleZ= MathHelper.sin(f2*0.6f);
        	Wing4_1.rotateAngleZ=-MathHelper.sin(f2*0.6f);
    	}else {
        	Wing4.rotateAngleZ=-degToRad(70)- MathHelper.sin(f2*0.05f)*0.05f;
        	Wing4_1.rotateAngleZ= degToRad(70)+ MathHelper.sin(f2*0.05f)*0.05f;
    	}
    }

    public float degToRad(float degrees)
    {
        return degrees * (float)Math.PI / 180 ;
    }

}

