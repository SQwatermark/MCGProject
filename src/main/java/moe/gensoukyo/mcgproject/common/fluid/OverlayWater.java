package moe.gensoukyo.mcgproject.common.fluid;

import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

import java.awt.*;

/**
 * 调色水流体，常见于CS4
 * @author Chloe_koopa
 */
public class OverlayWater extends Fluid
{
    private static final ResourceLocation BASE_STILL =
            new ResourceLocation(MCGProject.ID, "fluid/base_still");
    private static final ResourceLocation BASE_FLOW =
            new ResourceLocation(MCGProject.ID, "fluid/base_flow");

    public OverlayWater(String name, Color color)
    {
        super(name,
                BASE_STILL,
                BASE_FLOW,
                null,
                color
        );
        setUnlocalizedName(MCGProject.ID + "." + name);
    }
}
