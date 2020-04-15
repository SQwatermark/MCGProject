package moe.gensoukyo.mcgproject.cilent.model.fish;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

import static java.lang.Math.PI;
import static java.lang.Math.sin;

public class ModelTropicalFishA extends ModelBase {

    private ModelRenderer body;
    private ModelRenderer tail;
    private ModelRenderer finRight;
    private ModelRenderer finLeft;
    private ModelRenderer finTop;

    public ModelTropicalFishA() {
        textureWidth = 32;
        body = new  ModelRenderer(this, 0, 0);
        float scale = 0F;
        body.addBox(-1.0F, -1.5F, -3.0F, 2, 3, 6, scale);
        body.setRotationPoint(0.0F, 22.0F, 0.0F);
        tail = new  ModelRenderer(this, 22, -6);
        tail.addBox(0.0F, -1.5F, 0.0F, 0, 3, 6, scale);
        tail.setRotationPoint(0.0F, 22.0F, 3.0F);
        finRight = new  ModelRenderer(this, 2, 16);
        finRight.addBox(-2.0F, -1.0F, 0.0F, 2, 2, 0, scale);
        finRight.setRotationPoint(-1.0F, 22.5F, 0.0F);
        finRight.rotateAngleY = (float) PI / 4F;
        finLeft = new  ModelRenderer(this, 2, 12);
        finLeft.addBox(0.0F, -1.0F, 0.0F, 2, 2, 0, scale);
        finLeft.setRotationPoint(1.0F, 22.5F, 0.0F);
        finLeft.rotateAngleY = (float) (-PI) / 4F;
        finTop = new  ModelRenderer(this, 10, -5);
        finTop.addBox(0.0F, -3.0F, 0.0F, 0, 3, 6, scale);
        finTop.setRotationPoint(0.0F, 20.5F, -3.0F);
    }


    @Override
    public void render (Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
        body.render(scale);
        tail.render(scale);
        finRight.render(scale);
        finLeft.render(scale);
        finTop.render(scale);
    }

    @Override
    public void setRotationAngles (float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        float f = entityIn.isInWater() ? 1F : 1.5F;
        tail.rotateAngleY = (float) (-f * 0.45F * sin(0.6F * ageInTicks));
    }
}
