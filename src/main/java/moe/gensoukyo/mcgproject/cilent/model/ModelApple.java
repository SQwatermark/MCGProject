package moe.gensoukyo.mcgproject.cilent.model;// Made with Blockbench
// Paste this code into your mod.
// Make sure to generate all required imports

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelApple extends ModelBase {
	private final ModelRenderer bone;

	public ModelApple() {
		textureWidth = 16;
		textureHeight = 16;

		bone = new ModelRenderer(this);
		bone.setRotationPoint(6.0F, 30.0F, -9.0F);
		bone.cubeList.add(new ModelBox(bone, 0, 0, -7.0F, -11.0F, 5.0F, 0, 2, 1, 0.0F, true));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -7.0F, -8.0F, 6.0F, 0, 1, 1, 0.0F, true));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -7.0F, -13.0F, 6.0F, 0, 1, 1, 0.0F, true));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -7.0F, -7.0F, 8.0F, 0, 1, 2, 0.0F, true));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -7.0F, -14.0F, 8.0F, 0, 1, 2, 0.0F, true));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -7.0F, -11.0F, 12.0F, 0, 2, 1, 0.0F, true));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -7.0F, -13.0F, 11.0F, 0, 1, 1, 0.0F, true));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -7.0F, -8.0F, 11.0F, 0, 1, 1, 0.0F, true));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -8.0F, -9.0F, 6.0F, 0, 1, 1, 0.0F, true));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -8.0F, -12.0F, 6.0F, 0, 1, 1, 0.0F, true));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -8.0F, -8.0F, 7.0F, 0, 1, 1, 0.0F, true));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -8.0F, -13.0F, 7.0F, 0, 1, 1, 0.0F, true));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -8.0F, -8.0F, 10.0F, 0, 1, 1, 0.0F, true));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -8.0F, -13.0F, 10.0F, 0, 1, 1, 0.0F, true));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -8.0F, -9.0F, 11.0F, 0, 1, 1, 0.0F, true));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -8.0F, -12.0F, 11.0F, 0, 1, 1, 0.0F, true));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -9.0F, -11.0F, 6.0F, 0, 2, 1, 0.0F, true));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -9.0F, -12.0F, 7.0F, 0, 4, 1, 0.0F, true));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -9.0F, -9.0F, 8.0F, 0, 2, 2, 0.0F, true));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -9.0F, -13.0F, 8.0F, 0, 2, 2, 0.0F, true));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -9.0F, -11.0F, 11.0F, 0, 2, 1, 0.0F, true));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -9.0F, -12.0F, 10.0F, 0, 4, 1, 0.0F, true));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -10.0F, -11.0F, 8.0F, 0, 2, 2, 0.0F, true));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -2.0F, -11.0F, 8.0F, 0, 2, 2, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -3.0F, -11.0F, 6.0F, 0, 2, 1, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -3.0F, -12.0F, 7.0F, 0, 4, 1, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -3.0F, -9.0F, 8.0F, 0, 2, 2, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -3.0F, -13.0F, 8.0F, 0, 2, 2, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -3.0F, -11.0F, 11.0F, 0, 2, 1, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -3.0F, -12.0F, 10.0F, 0, 4, 1, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -4.0F, -9.0F, 6.0F, 0, 1, 1, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -4.0F, -12.0F, 6.0F, 0, 1, 1, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -4.0F, -8.0F, 7.0F, 0, 1, 1, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -4.0F, -13.0F, 7.0F, 0, 1, 1, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -4.0F, -8.0F, 10.0F, 0, 1, 1, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -4.0F, -13.0F, 10.0F, 0, 1, 1, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -4.0F, -9.0F, 11.0F, 0, 1, 1, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -4.0F, -12.0F, 11.0F, 0, 1, 1, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -5.0F, -11.0F, 5.0F, 0, 2, 1, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -5.0F, -8.0F, 6.0F, 0, 1, 1, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -5.0F, -13.0F, 6.0F, 0, 1, 1, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -5.0F, -7.0F, 8.0F, 0, 1, 2, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -5.0F, -14.0F, 8.0F, 0, 1, 2, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -5.0F, -11.0F, 12.0F, 0, 2, 1, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -5.0F, -13.0F, 11.0F, 0, 1, 1, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -5.0F, -8.0F, 11.0F, 0, 1, 1, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 6, -7.0F, -11.0F, 5.0F, 2, 0, 1, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 7, -4.0F, -11.0F, 6.0F, 1, 0, 1, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 7, -9.0F, -11.0F, 6.0F, 1, 0, 1, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 10, -3.0F, -11.0F, 8.0F, 1, 0, 2, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 10, -10.0F, -11.0F, 8.0F, 1, 0, 2, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 13, -7.0F, -11.0F, 12.0F, 2, 0, 1, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 12, -9.0F, -11.0F, 11.0F, 1, 0, 1, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 12, -4.0F, -11.0F, 11.0F, 1, 0, 1, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 7, -5.0F, -12.0F, 6.0F, 1, 0, 1, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 7, -8.0F, -12.0F, 6.0F, 1, 0, 1, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 8, -4.0F, -12.0F, 7.0F, 1, 0, 1, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 8, -9.0F, -12.0F, 7.0F, 1, 0, 1, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 11, -4.0F, -12.0F, 10.0F, 1, 0, 1, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 11, -9.0F, -12.0F, 10.0F, 1, 0, 1, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 12, -5.0F, -12.0F, 11.0F, 1, 0, 1, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 12, -8.0F, -12.0F, 11.0F, 1, 0, 1, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 7, -7.0F, -13.0F, 6.0F, 2, 0, 1, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 8, -8.0F, -13.0F, 7.0F, 4, 0, 1, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 10, -5.0F, -13.0F, 8.0F, 2, 0, 2, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 10, -9.0F, -13.0F, 8.0F, 2, 0, 2, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 12, -7.0F, -13.0F, 11.0F, 2, 0, 1, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 11, -8.0F, -13.0F, 10.0F, 4, 0, 1, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 10, -7.0F, -14.0F, 8.0F, 2, 0, 2, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -7.0F, -6.0F, 8.0F, 2, 0, 2, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -7.0F, -7.0F, 6.0F, 2, 0, 1, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -8.0F, -7.0F, 7.0F, 4, 0, 1, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -5.0F, -7.0F, 8.0F, 2, 0, 2, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -9.0F, -7.0F, 8.0F, 2, 0, 2, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -7.0F, -7.0F, 11.0F, 2, 0, 1, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -8.0F, -7.0F, 10.0F, 4, 0, 1, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -5.0F, -8.0F, 6.0F, 1, 0, 1, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -8.0F, -8.0F, 6.0F, 1, 0, 1, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -4.0F, -8.0F, 7.0F, 1, 0, 1, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -9.0F, -8.0F, 7.0F, 1, 0, 1, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -4.0F, -8.0F, 10.0F, 1, 0, 1, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -9.0F, -8.0F, 10.0F, 1, 0, 1, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -5.0F, -8.0F, 11.0F, 1, 0, 1, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -8.0F, -8.0F, 11.0F, 1, 0, 1, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -7.0F, -9.0F, 5.0F, 2, 0, 1, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -4.0F, -9.0F, 6.0F, 1, 0, 1, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -9.0F, -9.0F, 6.0F, 1, 0, 1, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -3.0F, -9.0F, 8.0F, 1, 0, 2, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -10.0F, -9.0F, 8.0F, 1, 0, 2, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -7.0F, -9.0F, 12.0F, 2, 0, 1, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -9.0F, -9.0F, 11.0F, 1, 0, 1, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -4.0F, -9.0F, 11.0F, 1, 0, 1, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -7.0F, -7.0F, 10.0F, 2, 1, 0, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -4.0F, -8.0F, 10.0F, 1, 1, 0, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -9.0F, -8.0F, 10.0F, 1, 1, 0, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -3.0F, -11.0F, 10.0F, 1, 2, 0, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -10.0F, -11.0F, 10.0F, 1, 2, 0, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -7.0F, -14.0F, 10.0F, 2, 1, 0, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -4.0F, -13.0F, 10.0F, 1, 1, 0, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -9.0F, -13.0F, 10.0F, 1, 1, 0, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -5.0F, -8.0F, 11.0F, 1, 1, 0, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -8.0F, -8.0F, 11.0F, 1, 1, 0, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -4.0F, -9.0F, 11.0F, 1, 1, 0, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -9.0F, -9.0F, 11.0F, 1, 1, 0, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -4.0F, -12.0F, 11.0F, 1, 1, 0, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -9.0F, -12.0F, 11.0F, 1, 1, 0, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -5.0F, -13.0F, 11.0F, 1, 1, 0, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -8.0F, -13.0F, 11.0F, 1, 1, 0, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -7.0F, -8.0F, 12.0F, 2, 1, 0, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -8.0F, -9.0F, 12.0F, 4, 1, 0, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -5.0F, -11.0F, 12.0F, 2, 2, 0, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -9.0F, -11.0F, 12.0F, 2, 2, 0, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -7.0F, -13.0F, 12.0F, 2, 1, 0, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -8.0F, -12.0F, 12.0F, 4, 1, 0, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -7.0F, -11.0F, 13.0F, 2, 2, 0, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -7.0F, -11.0F, 5.0F, 2, 2, 0, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -7.0F, -8.0F, 6.0F, 2, 1, 0, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -8.0F, -9.0F, 6.0F, 4, 1, 0, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -5.0F, -11.0F, 6.0F, 2, 2, 0, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -9.0F, -11.0F, 6.0F, 2, 2, 0, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -7.0F, -13.0F, 6.0F, 2, 1, 0, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -8.0F, -12.0F, 6.0F, 4, 1, 0, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -5.0F, -8.0F, 7.0F, 1, 1, 0, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -8.0F, -8.0F, 7.0F, 1, 1, 0, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -4.0F, -9.0F, 7.0F, 1, 1, 0, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -9.0F, -9.0F, 7.0F, 1, 1, 0, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -4.0F, -12.0F, 7.0F, 1, 1, 0, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -9.0F, -12.0F, 7.0F, 1, 1, 0, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -5.0F, -13.0F, 7.0F, 1, 1, 0, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -8.0F, -13.0F, 7.0F, 1, 1, 0, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -7.0F, -7.0F, 8.0F, 2, 1, 0, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -4.0F, -8.0F, 8.0F, 1, 1, 0, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -9.0F, -8.0F, 8.0F, 1, 1, 0, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -3.0F, -11.0F, 8.0F, 1, 2, 0, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -10.0F, -11.0F, 8.0F, 1, 2, 0, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -7.0F, -14.0F, 8.0F, 2, 1, 0, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -9.0F, -13.0F, 8.0F, 1, 1, 0, 0.0F, false));
		bone.cubeList.add(new ModelBox(bone, 0, 0, -4.0F, -13.0F, 8.0F, 1, 1, 0, 0.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		bone.render(f5);
	}
}