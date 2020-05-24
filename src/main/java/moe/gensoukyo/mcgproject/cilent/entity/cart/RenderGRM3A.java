package moe.gensoukyo.mcgproject.cilent.entity.cart;

import club.nsdn.nyasamarailway.api.cart.AbsTrainBase;
import club.nsdn.nyasamarailway.renderer.entity.AbsTrainRenderer;
import cn.ac.nya.forgeobj.WavefrontObject;
import moe.gensoukyo.mcgproject.common.entity.cart.GRM3A;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderGRM3A extends AbsTrainRenderer {

    public static IRenderFactory<AbsTrainBase> FACTORY = RenderGRM3A::new;
    private final String _name = "grm_3a";
    private final WavefrontObject modelBase = new WavefrontObject(new ResourceLocation(MCGProject.ID, "models/entity/cart/" + _name + "_base.obj"));
    //private final WavefrontObject modelPrint = new WavefrontObject(new ResourceLocation(MCGProject.ID, "models/entity/cart/" + _name + "_print.obj"));
    private final ResourceLocation textureBase = new ResourceLocation(MCGProject.ID, "textures/entity/cart/" + _name + "_base.png");
    //private final ResourceLocation texturePrint = new ResourceLocation(MCGProject.ID, "textures/entity/cart/" + _name + "_print.png");

    private final int doorCnt = 6;
    private final String[] doors, doorsL1,doorsL2, doorsR1, doorsR2;

    public RenderGRM3A(RenderManager manager) {
        super(manager);
        doors = new String[doorCnt * 2];
        doorsL1 = new String[doorCnt / 2]; doorsL2 = new String[doorCnt / 2];
        doorsR1 = new String[doorCnt / 2]; doorsR2 = new String[doorCnt / 2];
        
        for (int i = 1; i <= doorCnt; i++) {
            doors[(i - 1) * 2] = "d" + i + "1";
            doors[(i - 1) * 2 + 1] = "d" + i + "2";
            if ((i % 2) != 0) {
                doorsL1[(i - 1) / 2] = "d" + i + "1";
                doorsL2[(i - 1) / 2] = "d" + i + "2";
            } else {
                doorsR1[i / 2 - 1] = "d" + i + "1";
                doorsR2[i / 2 - 1] = "d" + i + "2";
            }
        }
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
        modelBase.renderAllExcept(doors);
        
        if (train instanceof GRM3A) {
            GRM3A metro = (GRM3A) train;
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
        }

//        GlStateManager.enableAlpha();
//        GlStateManager.enableBlend();
//        Minecraft.getMinecraft().getTextureManager().bindTexture(texturePrint);
//        modelPrint.renderAll();
//        GlStateManager.disableAlpha();
//        GlStateManager.disableBlend();

        GlStateManager.popMatrix();
    }

}
