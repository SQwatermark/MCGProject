package moe.gensoukyo.mcgproject.core;

import moe.gensoukyo.mcgproject.common.entity.EntityKaginawa;
import moe.gensoukyo.mcgproject.common.init.ModArmor;
import moe.gensoukyo.mcgproject.common.init.ModBlock;
import moe.gensoukyo.mcgproject.common.init.ModEntity;
import moe.gensoukyo.mcgproject.common.init.ModItem;
import net.minecraft.entity.Entity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.WeakHashMap;

public class CommonProxy {

    public WeakHashMap<Entity, EntityKaginawa> kagimap = new WeakHashMap();

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(ModItem.instance());
        MinecraftForge.EVENT_BUS.register(ModBlock.instance());
        MinecraftForge.EVENT_BUS.register(ModArmor.instance());
        MinecraftForge.EVENT_BUS.register(ModEntity.instance());
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {

    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }

}
