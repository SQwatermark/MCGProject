package moe.gensoukyo.mcgproject.core;

import moe.gensoukyo.mcgproject.cilent.feature.CustomMainMenu;
import moe.gensoukyo.mcgproject.cilent.feature.ItitFeatures;
import moe.gensoukyo.mcgproject.cilent.init.ModelMapper;
import moe.gensoukyo.mcgproject.common.feature.hackychat.ClientCmdHacker;
import moe.gensoukyo.mcgproject.common.feature.hackychat.FilteredChatHandler;
import moe.gensoukyo.mcgproject.common.feature.hackychat.CmdFilteredChat;
import moe.gensoukyo.mcgproject.common.feature.rsgauges.ModRsGauges;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.lwjgl.opengl.Display;

import java.nio.file.Paths;

public class ClientProxy extends CommonProxy {

    @Override
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        //客户端关闭钩子
        Runtime.getRuntime().addShutdownHook(new Thread(MCGProject.NAME + " Shutdown Thread") {
            @Override
            public void run() {
                MCGProject.logger.info("Closing something...");
                ClientCmdHacker.FUNC_TIMER.cancel();
            }
        });
        //获取mcgproject配置文件目录
        MCGProject.modConfigDi = Paths.get(event.getModConfigurationDirectory().getAbsolutePath(), "mcgproject").toFile();
        //如果是32位java，抛出错误信息
        ItitFeatures.yellAtJava32x();
        //设置窗口标题（预加载阶段）
        Display.setTitle("Minecraft幻想乡1.12.2");
        //加载游戏图标
        ItitFeatures.loadIcons();
        //写入服务器信息
        ItitFeatures.addServerInformation();
        super.preInit(event);
        MinecraftForge.EVENT_BUS.register(ModelMapper.instance());
        MinecraftForge.EVENT_BUS.register(CustomMainMenu.instance());
        ModRsGauges.client.preInit(event);

        MinecraftForge.EVENT_BUS.register(FilteredChatHandler.instance());
        ClientCmdHacker.replaceHandlerInstance();
        // 指令注册需要在这行之后，这里替换了客户端指令处理器的实现
        ClientCommandHandler.instance.registerCommand(new CmdFilteredChat());
    }

    @Override
    @EventHandler
    public void init(FMLInitializationEvent event) {
        super.init(event);
        ModRsGauges.client.init(event);
    }

    @Override
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
        ModRsGauges.client.postInit(event);
    }

}
