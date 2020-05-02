package moe.gensoukyo.mcgproject.common.fluid;

import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

import javax.annotation.Nullable;
import java.awt.*;

/**
 * 简化的流体操作，暂时未使用
 * @author Chloe_koopa
 */
@SuppressWarnings("unused")
public class FluidMCG extends Fluid {

    public FluidMCG(String name) {
        this(name, Color.WHITE);
    }

    public FluidMCG(String name, Color color) {
        this(name, false, color);
    }

    public FluidMCG(String name, boolean hasOverlay, Color color) {
        this(name, new ResourceLocation(MCGProject.ID, "fluid/" + name + "_still"),
                new ResourceLocation(MCGProject.ID, "fluid/" + name + "_flow"),
                hasOverlay ? new ResourceLocation(MCGProject.ID, "fluid/" + name + "_overlay") : null, color);
    }

    protected FluidMCG(String fluidName, ResourceLocation still,
                       ResourceLocation flowing, @Nullable ResourceLocation overlay, Color color) {
        super(fluidName, still, flowing, overlay, color);
        this.setUnlocalizedName(MCGProject.ID + "." + fluidName + ".name");
    }

    public static FluidMCG singleTex(String name) {
        return singleTex(name, Color.WHITE);
    }

    /**
     * 生成只有一种材质的水
     * 材质放在fluid/[名称].png
     */
    public static FluidMCG singleTex(String name, Color color) {
        final ResourceLocation theOneTex = new ResourceLocation(MCGProject.ID, "fluid/" + name);
        return new FluidMCG(name, theOneTex, theOneTex, null, color);
    }
}
