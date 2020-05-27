package moe.gensoukyo.mcgproject.cilent.entity.cart;

import club.nsdn.nyasamarailway.api.cart.AbsTrainBase;
import club.nsdn.nyasamarailway.renderer.entity.AbsTrainRenderer;
import cn.ac.nya.forgeobj.WavefrontObject;
import moe.gensoukyo.mcgproject.common.entity.cart.GRM3B;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import java.util.ArrayList;

public class RenderGRM3B extends AbsTrainRenderer {

    public static IRenderFactory<AbsTrainBase> FACTORY = RenderGRM3B::new;
    private final String _name = "grm_3b";
    private final WavefrontObject modelBase = new WavefrontObject(new ResourceLocation(MCGProject.ID, "models/entity/cart/" + _name + "_base.obj"));
    private final WavefrontObject modelPrint = new WavefrontObject(new ResourceLocation(MCGProject.ID, "models/entity/cart/" + _name + "_print.obj"));
    private final ResourceLocation textureBase = new ResourceLocation(MCGProject.ID, "textures/entity/cart/" + _name + "_base.png");
    private final ResourceLocation texturePrint = new ResourceLocation(MCGProject.ID, "textures/entity/cart/" + _name + "_print.png");

    private final int doorCnt = 6;
    private final ArrayList<String> exclude;
    private final String[] doorsL1,doorsL2, doorsR1, doorsR2;

    public RenderGRM3B(RenderManager manager) {
        super(manager);
        exclude = new ArrayList<>();
        doorsL1 = new String[doorCnt / 2]; doorsL2 = new String[doorCnt / 2];
        doorsR1 = new String[doorCnt / 2]; doorsR2 = new String[doorCnt / 2];
        
        for (int i = 1; i <= doorCnt; i++) {
            exclude.add("d" + i + "1");
            exclude.add("d" + i + "2");
            if ((i % 2) != 0) {
                doorsL1[(i - 1) / 2] = "d" + i + "1";
                doorsL2[(i - 1) / 2] = "d" + i + "2";
            } else {
                doorsR1[i / 2 - 1] = "d" + i + "1";
                doorsR2[i / 2 - 1] = "d" + i + "2";
            }
        }
        exclude.add("seat");
    }

    @Override
    protected ResourceLocation getEntityTexture(AbsTrainBase train) {
        return textureBase;
    }

    @Override
    public void render(AbsTrainBase train, double x, double y, double z, float yaw) {
        GlStateManager.pushMatrix();
        GlStateManager.scale(0.0625D, 0.0625D, 0.0625D);
        Minecraft.getMinecraft().getTextureManager().bindTexture(textureBase);
        modelBase.renderAllExcept(exclude.toArray(new String[0]));
        
        if (train instanceof GRM3B) {
            GRM3B metro = (GRM3B) train;
            double prog = (double) metro.doorProgressLeft / 100.0D * 15.0D;
            GlStateManager.pushMatrix();
            GlStateManager.translate(-prog, 0.0D, 0.0D);
            modelBase.renderOnly(doorsL2);
            GlStateManager.popMatrix();
            
            GlStateManager.pushMatrix();
            GlStateManager.translate(prog, 0.0D, 0.0D);
            modelBase.renderOnly(doorsL1);
            GlStateManager.popMatrix();
            
            prog = (double) metro.doorProgressRight / 100.0D * 15.0D;
            GlStateManager.pushMatrix();
            GlStateManager.translate(prog, 0.0D, 0.0D);
            modelBase.renderOnly(doorsR1);
            GlStateManager.popMatrix();
            
            GlStateManager.pushMatrix();
            GlStateManager.translate(-prog, 0.0D, 0.0D);
            modelBase.renderOnly(doorsR2);
            GlStateManager.popMatrix();

            float[] color = EnumDyeColor.byDyeDamage(metro.getColorDamage()).getColorComponentValues();
            GlStateManager.color(color[0], color[1], color[2]);
            modelBase.renderOnly("seat");

            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            Minecraft.getMinecraft().getTextureManager().bindTexture(texturePrint);
            modelPrint.renderAll();
            GlStateManager.disableAlpha();
            GlStateManager.disableBlend();
            GlStateManager.color(1, 1, 1);
        }

        GlStateManager.popMatrix();
    }

}
