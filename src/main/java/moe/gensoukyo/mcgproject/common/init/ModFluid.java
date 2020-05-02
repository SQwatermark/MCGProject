package moe.gensoukyo.mcgproject.common.init;

import moe.gensoukyo.mcgproject.common.fluid.FluidMCG;
import moe.gensoukyo.mcgproject.common.fluid.OverlayWater;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;

import java.awt.*;
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
    public static final Fluid WATER_BLACK = new OverlayWater("water_black", Color.BLACK);
    public static final Fluid WATER_BLUE = new OverlayWater("water_blue", Color.BLUE.brighter());
    public static final Fluid WATER_CYAN = new OverlayWater("water_cyan", Color.CYAN.brighter());
    public static final Fluid WATER_GREEN = new OverlayWater("water_green", Color.GREEN.brighter());
    public static final Fluid WATER_ORANGE = new OverlayWater("water_orange" ,Color.ORANGE);
    public static final Fluid WATER_PURPLE = new OverlayWater("water_purple", new Color(255, 0, 255).brighter());
    public static final Fluid WATER_RED = new OverlayWater("water_red", Color.RED.brighter());
    public static final Fluid WATER_WHITE = new OverlayWater("water_white", Color.WHITE.brighter());
    public static final Fluid WATER_YELLOW = new OverlayWater("water_yellow", Color.YELLOW.brighter());
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
