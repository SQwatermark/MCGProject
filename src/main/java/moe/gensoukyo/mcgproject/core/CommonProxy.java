package moe.gensoukyo.mcgproject.core;

import moe.gensoukyo.mcgproject.common.entity.EntityKaginawa;
import moe.gensoukyo.mcgproject.common.feature.MoreBannerPatterns;
import moe.gensoukyo.mcgproject.common.feature.BetterSign;
import moe.gensoukyo.mcgproject.common.init.*;
import moe.gensoukyo.mcgproject.common.util.EntityPool;
import net.minecraft.entity.Entity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.WeakHashMap;

public class CommonProxy {

    public WeakHashMap<Entity, EntityKaginawa> kagimap = new WeakHashMap<>();

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(ModItem.instance());
        MinecraftForge.EVENT_BUS.register(ModBlock.instance());
        MinecraftForge.EVENT_BUS.register(ModArmor.instance());
        MinecraftForge.EVENT_BUS.register(ModEntity.instance());
        MinecraftForge.EVENT_BUS.register(ModTileEntity.instance());
        MinecraftForge.EVENT_BUS.register(BetterSign.instance());
        //MinecraftForge.EVENT_BUS.register(ModFluid.instance());

        MinecraftForge.EVENT_BUS.register(EntityPool.instance());
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        //添加旗帜图案
        MoreBannerPatterns.init();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {

    }

}
