package moe.gensoukyo.mcgproject.core;

import moe.gensoukyo.mcgproject.common.feature.BetterSign;
import moe.gensoukyo.mcgproject.common.feature.MoreBannerPatterns;
import moe.gensoukyo.mcgproject.common.feature.WorldGuard;
import moe.gensoukyo.mcgproject.common.feature.customnpcs.CustomNPCsHook;
import moe.gensoukyo.mcgproject.common.feature.customnpcs.NPCSpawner;
import moe.gensoukyo.mcgproject.common.feature.futuremc.FMBlock;
import moe.gensoukyo.mcgproject.common.feature.kaginawa.EntityKaginawa;
import moe.gensoukyo.mcgproject.common.feature.musicplayer.MusicPlayerManager;
import moe.gensoukyo.mcgproject.common.feature.musicplayer.StreamStopper;
import moe.gensoukyo.mcgproject.common.init.ModBlock;
import moe.gensoukyo.mcgproject.common.init.ModEntity;
import moe.gensoukyo.mcgproject.common.init.ModItem;
import moe.gensoukyo.mcgproject.common.init.ModTileEntity;
import moe.gensoukyo.mcgproject.common.network.NetworkWrapper;
import moe.gensoukyo.mcgproject.common.util.EntityPool;
import net.minecraft.entity.Entity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.nio.file.Paths;
import java.util.WeakHashMap;

public class CommonProxy {

    public WeakHashMap<Entity, EntityKaginawa> kagimap = new WeakHashMap<>();
    public MusicPlayerManager playerManager = new MusicPlayerManager();

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MCGProject.modConfigDi = Paths.get(event.getModConfigurationDirectory().getAbsolutePath(), "mcgproject").toFile();
        MinecraftForge.EVENT_BUS.register(ModItem.instance());
        MinecraftForge.EVENT_BUS.register(ModBlock.instance());
        MinecraftForge.EVENT_BUS.register(FMBlock.instance());
        MinecraftForge.EVENT_BUS.register(ModEntity.instance());
        MinecraftForge.EVENT_BUS.register(ModTileEntity.instance());
        MinecraftForge.EVENT_BUS.register(BetterSign.instance());
        MinecraftForge.EVENT_BUS.register(EntityPool.instance());
        MinecraftForge.EVENT_BUS.register(WorldGuard.instance());
        MinecraftForge.EVENT_BUS.register(StreamStopper.instance());
        new NetworkWrapper(event);
        if (Loader.isModLoaded("customnpcs")) {
            MCGProject.logger.info("Register CustomNPCs Hook");
            MinecraftForge.EVENT_BUS.register(CustomNPCsHook.instance());
            MinecraftForge.EVENT_BUS.register(NPCSpawner.instance());
        }
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        //添加旗帜图案
        new MoreBannerPatterns();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }

}
