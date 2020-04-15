package moe.gensoukyo.mcgproject.cilent.model.fish;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

import static java.lang.Math.sin;

public class ModelPufferFish extends ModelBase {

    private ModelRenderer body;
    private ModelRenderer rightFin;
    private ModelRenderer leftFin;
    private ModelRenderer field_203744_d;
    private ModelRenderer field_203745_e;
    private ModelRenderer field_203746_f;
    private ModelRenderer field_203747_g;
    private ModelRenderer field_203748_h;
    private ModelRenderer field_203749_i;
    private ModelRenderer field_203750_j;
    private ModelRenderer field_203751_k;
    private ModelRenderer field_203752_l;
    private ModelRenderer field_203753_m;

    public ModelPufferFish() {
        textureWidth = 32;
        body = new  ModelRenderer(this, 0, 0);
        body.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8);
        body.setRotationPoint(0.0f, 22.0f, 0.0f);
        rightFin = new  ModelRenderer(this, 24, 0);
        rightFin.addBox(-2.0f, 0.0f, -1.0f, 2, 1, 2);
        rightFin.setRotationPoint(-4.0f, 15.0f, -2.0f);
        leftFin = new  ModelRenderer(this, 24, 3);
        leftFin.addBox(0.0f, 0.0f, -1.0f, 2, 1, 2);
        leftFin.setRotationPoint(4.0f, 15.0f, -2.0f);
        field_203744_d = new  ModelRenderer(this, 15, 17);
        field_203744_d.addBox(-4.0f, -1.0f, 0.0f, 8, 1, 0);
        field_203744_d.setRotationPoint(0.0f, 14.0f, -4.0f);
        field_203744_d.rotateAngleX = 0.7853982f;
        field_203745_e = new  ModelRenderer(this, 14, 16);
        field_203745_e.addBox(-4.0f, -1.0f, 0.0f, 8, 1, 1);
        field_203745_e.setRotationPoint(0.0f, 14.0f, 0.0f);
        field_203746_f = new  ModelRenderer(this, 23, 18);
        field_203746_f.addBox(-4.0f, -1.0f, 0.0f, 8, 1, 0);
        field_203746_f.setRotationPoint(0.0f, 14.0f, 4.0f);
        field_203746_f.rotateAngleX = -0.7853982f;
        field_203747_g = new  ModelRenderer(this, 5, 17);
        field_203747_g.addBox(-1.0f, -8.0f, 0.0f, 1, 8, 0);
        field_203747_g.setRotationPoint(-4.0f, 22.0f, -4.0f);
        field_203747_g.rotateAngleY = -0.7853982f;
        field_203748_h = new  ModelRenderer(this, 1, 17);
        field_203748_h.addBox(0.0f, -8.0f, 0.0f, 1, 8, 0);
        field_203748_h.setRotationPoint(4.0f, 22.0f, -4.0f);
        field_203748_h.rotateAngleY = 0.7853982f;
        field_203749_i = new  ModelRenderer(this, 15, 20);
        field_203749_i.addBox(-4.0f, 0.0f, 0.0f, 8, 1, 0);
        field_203749_i.setRotationPoint(0.0f, 22.0f, -4.0f);
        field_203749_i.rotateAngleX = -0.7853982f;
        field_203751_k = new  ModelRenderer(this, 15, 20);
        field_203751_k.addBox(-4.0f, 0.0f, 0.0f, 8, 1, 0);
        field_203751_k.setRotationPoint(0.0f, 22.0f, 0.0f);
        field_203750_j = new  ModelRenderer(this, 15, 20);
        field_203750_j.addBox(-4.0f, 0.0f, 0.0f, 8, 1, 0);
        field_203750_j.setRotationPoint(0.0f, 22.0f, 4.0f);
        field_203750_j.rotateAngleX = 0.7853982f;
        field_203752_l = new  ModelRenderer(this, 9, 17);
        field_203752_l.addBox(-1.0f, -8.0f, 0.0f, 1, 8, 0);
        field_203752_l.setRotationPoint(-4.0f, 22.0f, 4.0f);
        field_203752_l.rotateAngleY = 0.7853982f;
        field_203753_m = new  ModelRenderer(this, 9, 17);
        field_203753_m.addBox(0.0f, -8.0f, 0.0f, 1, 8, 0);
        field_203753_m.setRotationPoint(4.0f, 22.0f, 4.0f);
        field_203753_m.rotateAngleY = -0.7853982f;
    }

    @Override
    public void render (Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
        body.render(scale);
        rightFin.render(scale);
        leftFin.render(scale);
        field_203744_d.render(scale);
        field_203745_e.render(scale);
        field_203746_f.render(scale);
        field_203747_g.render(scale);
        field_203748_h.render(scale);
        field_203749_i.render(scale);
        field_203750_j.render(scale);
        field_203751_k.render(scale);
        field_203752_l.render(scale);
        field_203753_m.render(scale);
    }

    @Override
    public void setRotationAngles (float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        rightFin.rotateAngleZ = (float) (-0.2F + 0.4F * sin(ageInTicks * 0.2F));
        leftFin.rotateAngleZ = (float) (0.2F - 0.4F * sin(ageInTicks * 0.2F));
    }
}
