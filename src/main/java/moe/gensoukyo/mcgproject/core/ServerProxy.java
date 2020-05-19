package moe.gensoukyo.mcgproject.core;

import moe.gensoukyo.mcgproject.server.feature.customnpcs.CustomNPCsHook;
import moe.gensoukyo.mcgproject.server.feature.customnpcs.NPCSpawner;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ServerProxy extends CommonProxy {

    @Override
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        if (Loader.isModLoaded("customnpcs")) {
            MCGProject.logger.info("Register CustomNPCs Hook");
            MinecraftForge.EVENT_BUS.register(CustomNPCsHook.instance());
            MCGProject.logger.info("Register NPCSpawner");
            MinecraftForge.EVENT_BUS.register(NPCSpawner.instance());
        }
    }

}
