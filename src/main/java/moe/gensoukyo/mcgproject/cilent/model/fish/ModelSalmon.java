package moe.gensoukyo.mcgproject.cilent.model.fish;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

import static java.lang.Math.PI;
import static java.lang.Math.sin;

public class ModelSalmon extends ModelBase {

    private ModelRenderer bodyFront;
    private ModelRenderer bodyRear;
    private ModelRenderer head;
    private ModelRenderer finTopFront;
    private ModelRenderer finTopRear;
    private ModelRenderer tail;
    private ModelRenderer finRight;
    private ModelRenderer finLeft;

    public ModelSalmon() {
        textureWidth = 32;

        bodyFront = new ModelRenderer(this, 0, 0);
        bodyFront.addBox(-1.5f, -2.5f, 0.0f, 3, 5, 8);
        bodyFront.setRotationPoint(0.0f, 20.0f, 0.0f);
        bodyRear = new ModelRenderer(this, 0, 13);
        bodyRear.addBox(-1.5f, -2.5f, 0.0f, 3, 5, 8);
        bodyRear.setRotationPoint(0.0f, 20.0f, 8.0f);
        head = new ModelRenderer(this, 22, 0);
        head.addBox(-1.0f, -2.0f, -3.0f, 2, 4, 3);
        head.setRotationPoint(0.0f, 20.0f, 0.0f);
        tail = new ModelRenderer(this, 20, 10);
        tail.addBox(0.0f, -2.5f, 0.0f, 0, 5, 6);
        tail.setRotationPoint(0.0f, 0.0f, 8.0f);
        bodyRear.addChild(tail);
        finTopFront = new ModelRenderer(this, 2, 1);
        finTopFront.addBox(0.0f, 0.0f, 0.0f, 0, 2, 3);
        finTopFront.setRotationPoint(0.0f, -4.5f, 5.0f);
        bodyFront.addChild(finTopFront);
        finTopRear = new ModelRenderer(this, 0, 2);
        finTopRear.addBox(0.0f, 0.0f, 0.0f, 0, 2, 4);
        finTopRear.setRotationPoint(0.0f, -4.5f, -1.0f);
        bodyRear.addChild(finTopRear);
        finRight = new ModelRenderer(this, -4, 0);
        finRight.addBox(-2.0f, 0.0f, 0.0f, 2, 0, 2);
        finRight.setRotationPoint(-1.5f, 21.5f, 0.0f);
        finRight.rotateAngleZ = ((float) (-PI) / 4F);
        finLeft = new ModelRenderer(this, 0, 0);
        finLeft.addBox(0.0f, 0.0f, 0.0f, 2, 0, 2);
        finLeft.setRotationPoint(1.5f, 21.5f, 0.0f);
    }

    @Override
    public void render (Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
        bodyFront.render(scale);
        bodyRear.render(scale);
        head.render(scale);
        finRight.render(scale);
        finLeft.render(scale);
    }

    @Override
    public void setRotationAngles (float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        float i = 1F;
        float j = 1F;

        if (!entityIn.isInWater()) {
            i = 1.3F;
            j = 1.7F;
        }

        bodyRear.rotateAngleY = (float) (-i * 0.25f * sin(j * 0.6F * ageInTicks));
    }

}
