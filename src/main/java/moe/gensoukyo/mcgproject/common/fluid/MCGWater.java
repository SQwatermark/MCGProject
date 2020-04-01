package moe.gensoukyo.mcgproject.common.fluid;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class MCGWater extends Fluid {

    public MCGWater(String fluidName, ResourceLocation still, ResourceLocation flowing) {
        super(fluidName, still, flowing);
    }
}
