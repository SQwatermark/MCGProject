package moe.gensoukyo.mcgproject.common.init;

import moe.gensoukyo.mcgproject.common.fluid.MCGWater;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModFluid {

    private static ModFluid instance;
    public static ModFluid instance()
    {
        if(instance == null) instance = new ModFluid();
        return instance;
    }

    public static Fluid myFluid;

    private ModFluid()
    {
        myFluid = new MCGWater("example_fluid", new ResourceLocation("mcgproject:blocks/fluid/water_white"), new ResourceLocation("mcgproject:blocks/fluid/water_white")).setGaseous(true).setDensity(Integer.MAX_VALUE);
    }

    @SubscribeEvent
    public void register(RegistryEvent.Register<Block> event)
    {
        MCGProject.logger.info("MCGProject: registering fluids");
        FluidRegistry.registerFluid(myFluid);
        FluidRegistry.addBucketForFluid(myFluid);
        event.getRegistry().register(new BlockFluidClassic(myFluid, Material.WATER).setRegistryName("example_fluid"));
    }

}
