package moe.gensoukyo.mcgproject.cilent.model.fish;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

import static java.lang.Math.PI;
import static java.lang.Math.sin;

public class ModelCod extends ModelBase {

    private ModelRenderer body;
    private ModelRenderer finTop;
    private ModelRenderer head;
    private ModelRenderer headFront;
    private ModelRenderer finRight;
    private ModelRenderer finLeft;
    private ModelRenderer tail;

    public ModelCod() {
        textureWidth = 32;
        body = new  ModelRenderer(this, 0, 0);
        body.addBox(-1.0F, -2.0F, 0.0F, 2, 4, 7);
        body.setRotationPoint(0.0F, 22.0F, 0.0F);
        head = new  ModelRenderer(this, 11, 0);
        head.addBox(-1.0F, -2.0F, -3.0F, 2, 4, 3);
        head.setRotationPoint(0.0F, 22.0F, 0.0F);
        headFront = new  ModelRenderer(this, 0, 0);
        headFront.addBox(-1.0F, -2.0F, -1.0F, 2, 3, 1);
        headFront.setRotationPoint(0.0F, 22.0F, -3.0F);
        finRight = new  ModelRenderer(this, 22, 1);
        finRight.addBox(-2.0F, 0.0F, -1.0F, 2, 0, 2);
        finRight.setRotationPoint(-1.0F, 23.0F, 0.0F);
        finRight.rotateAngleZ = (float)(-PI) / 4F;
        finLeft = new  ModelRenderer(this, 22, 4);
        finLeft.addBox(0.0F, 0.0F, -1.0F, 2, 0, 2);
        finLeft.setRotationPoint(1.0F, 23.0F, 0.0F);
        finLeft.rotateAngleZ = (float)(PI) / 4F;
        tail = new  ModelRenderer(this, 22, 3);
        tail.addBox(0.0F, -2.0F, 0.0F, 0, 4, 4);
        tail.setRotationPoint(0.0F, 22.0F, 7.0F);
        finTop = new  ModelRenderer(this, 20, -6);
        finTop.addBox(0.0F, -1.0F, -1.0F, 0, 1, 6);
        finTop.setRotationPoint(0.0F, 20.0F, 0.0F);
    }

    @Override
    public void render (Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
        body.render(scale);
        head.render(scale);
        headFront.render(scale);
        finRight.render(scale);
        finLeft.render(scale);
        tail.render(scale);
        finTop.render(scale);
    }

    @Override
    public void setRotationAngles (float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        float f = entityIn.isInWater() ? 1F : 1.5F;
        tail.rotateAngleY = (float) (-f * 0.45F * sin(0.6F * ageInTicks));
    }

}
