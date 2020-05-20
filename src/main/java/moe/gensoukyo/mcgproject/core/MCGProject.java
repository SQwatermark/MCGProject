package moe.gensoukyo.mcgproject.core;

import moe.gensoukyo.mcgproject.common.feature.backpack.BackpackCore;
import moe.gensoukyo.mcgproject.common.feature.rsgauges.ModRsGauges;
import moe.gensoukyo.mcgproject.common.feature.sticker.TileSticker;
import moe.gensoukyo.mcgproject.common.feature.customnpcs.CommandKillNPCs;
import moe.gensoukyo.mcgproject.common.feature.customnpcs.NPCSpawnerConfig;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import org.apache.logging.log4j.Logger;

import java.io.File;

/**
 * @author SQwatermark
 * @date 2020/2/14
 */
@Mod(modid = MCGProject.ID, name = MCGProject.NAME, version = MCGProject.VERSION)
public class MCGProject {

    @Mod.Instance(MCGProject.ID)
    public static MCGProject INSTANCE;

    public static final String ID = "mcgproject";
    public static final String NAME = "MCGProject";
    public static final String VERSION = "1.1.9";

    public static Logger logger;
    public static File modConfigDi;

    //服务端事件用
    public MinecraftServer server;

    @SuppressWarnings("unused")
    public static final String[] CODERS = {"SQwatermark", "drzzm32", "Chloe_koopa"};
    @SuppressWarnings("unused")
    public static final String[] ARTISTS = {"A1181899594", "河豚骨拉面", "MCG旧版本画师"};

    static
    {
        //允许万能桶
        FluidRegistry.enableUniversalBucket();
    }

    @SidedProxy(clientSide = "moe.gensoukyo.mcgproject.core.ClientProxy",
            serverSide = "moe.gensoukyo.mcgproject.core.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preInit(event);

        ModRsGauges.attachLogger(logger);
        ModRsGauges.INSTANCE.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
        ModRsGauges.INSTANCE.attachLogger(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
        ModRsGauges.INSTANCE.postInit(event);
    }

    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        server = event.getServer();
        event.registerServerCommand(new BackpackCore.BackpackCommand());
        event.registerServerCommand(new BackpackCore.BackpackManageCommand());
        event.registerServerCommand(new TileSticker.RefreshCommand());
        event.registerServerCommand(new NPCSpawnerConfig.CommandRefreshNPCSpawner());
        event.registerServerCommand(new CommandKillNPCs());
    }

    @Mod.EventHandler
    public void serverStop(FMLServerStoppingEvent event) {
        if (server.isDedicatedServer()) { //独立服务端关闭处理
            MCGProject.logger.info("Closing something...");
        }
    }

    //TODO: 音效方块
    //TODO: 图书馆
    //TODO：荆棘（玩家碰到会掉血）
    //TODO: 玩家在其中会下陷的泥巴（沼泽）
    //TODO: 恐龙的模型
    //TODO: 狐火
    //TODO：试图渡过三途川的玩家会下沉

}