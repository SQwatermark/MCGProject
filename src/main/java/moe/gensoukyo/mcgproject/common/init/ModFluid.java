package moe.gensoukyo.mcgproject.common.init;

import moe.gensoukyo.mcgproject.common.fluid.FluidMCG;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;

import java.util.LinkedList;
import java.util.List;

/**
 * CS4的水
 * @author Chloe_koopa
 */
@Mod.EventBusSubscriber(modid = MCGProject.ID)
public class ModFluid
{
    public static final Fluid INTERSTICE = FluidMCG.singleTex("interstice");
    public static final Fluid WATER_BLACK = FluidMCG.singleTex("water_black");
    public static final Fluid WATER_BLUE = FluidMCG.singleTex("water_blue");
    public static final Fluid WATER_CYAN = FluidMCG.singleTex("water_cyan");
    public static final Fluid WATER_GREEN = FluidMCG.singleTex("water_green");
    public static final Fluid WATER_ORANGE = FluidMCG.singleTex("water_orange");
    public static final Fluid WATER_PURPLE = FluidMCG.singleTex("water_purple");
    public static final Fluid WATER_RED = FluidMCG.singleTex("water_red");
    public static final Fluid WATER_WHITE = FluidMCG.singleTex("water_white");
    public static final Fluid WATER_YELLOW = FluidMCG.singleTex("water_yellow");
    public static final List<Fluid> FLUIDS = new LinkedList<>();
    static
    {
        FLUIDS.add(INTERSTICE);
        FLUIDS.add(WATER_BLACK);
        FLUIDS.add(WATER_BLUE);
        FLUIDS.add(WATER_CYAN);
        FLUIDS.add(WATER_GREEN);
        FLUIDS.add(WATER_ORANGE);
        FLUIDS.add(WATER_PURPLE);
        FLUIDS.add(WATER_RED);
        FLUIDS.add(WATER_WHITE);
        FLUIDS.add(WATER_YELLOW);
    }

    public static void registerFluids()
    {
        FLUIDS.forEach(fluid ->
        {
            FluidRegistry.registerFluid(fluid);
            FluidRegistry.addBucketForFluid(fluid);
        });
    }
}
