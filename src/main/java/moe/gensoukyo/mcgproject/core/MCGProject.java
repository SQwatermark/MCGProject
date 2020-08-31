package moe.gensoukyo.mcgproject.core;

import moe.gensoukyo.mcgproject.cilent.feature.NoRecipeBook;
import moe.gensoukyo.mcgproject.common.feature.backpack.BackpackCore;
import moe.gensoukyo.mcgproject.common.feature.customnpcs.CommandKillNPCs;
import moe.gensoukyo.mcgproject.common.feature.customnpcs.NPCSpawnerConfig;
import moe.gensoukyo.mcgproject.common.feature.rsgauges.ModRsGauges;
import moe.gensoukyo.mcgproject.common.feature.sticker.TileSticker;
import moe.gensoukyo.mcgproject.common.util.NashornPool;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import noppes.npcs.controllers.ScriptController;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

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
    public static final String VERSION = "1.3.0";

    public static Logger logger;
    public static File modConfigDi;
    public static File modDi;

    //服务端事件用
    public static MinecraftServer SERVER = null;

    public static boolean isServer() {
        try {
            Class.forName("net.minecraft.client.Minecraft");
            return false;
        } catch (Exception ignored) { }
        return SERVER != null;
    }

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
    @SideOnly(Side.CLIENT)
    public void loadComplete(FMLLoadCompleteEvent event) {
        //设置窗口标题（加载完成时）
        Display.setTitle("Minecraft幻想乡1.12.2");
    }

    @Mod.EventHandler
    @SideOnly(Side.CLIENT)
    public void constructMod(FMLConstructionEvent event) {
        //隐藏玩家物品栏界面的合成书
        MinecraftForge.EVENT_BUS.register(new NoRecipeBook());
    }

    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        SERVER = event.getServer();

        event.registerServerCommand(new BackpackCore.BackpackCommand());
        event.registerServerCommand(new BackpackCore.PackAdminCommand());
        event.registerServerCommand(new BackpackCore.BackpackManageCommand());
        event.registerServerCommand(new TileSticker.RefreshCommand());
        event.registerServerCommand(new NPCSpawnerConfig.CommandRefreshNPCSpawner());
        event.registerServerCommand(new CommandKillNPCs());

        NashornPool.initNashornPool(ScriptController.Instance.factories.get("ecmascript"));
        NashornPool.refresh(); // 加载对象池
    }

    @Mod.EventHandler
    public void serverStop(FMLServerStoppingEvent event) {
        if (SERVER.isDedicatedServer()) { //独立服务端关闭处理
            MCGProject.logger.info("Closing something...");

            SERVER = null;
        }
    }

    //TODO: 音效方块
    //TODO: 图书馆
    //TODO：荆棘（玩家碰到会掉血）
    //TODO: 恐龙的模型
    //TODO：试图渡过三途川的玩家会下沉
    //TODO: 旅行者地图的指令，服务端执行，给客户端添加导航点（客户端应该有防止被添加导航点的选项）
    //TODO: 游戏萌澄果图标改成资源包，把forge文件换回去

}