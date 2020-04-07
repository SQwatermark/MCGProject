package moe.gensoukyo.mcgproject.core;

import moe.gensoukyo.mcgproject.cilent.init.ModelMapper;
import moe.gensoukyo.mcgproject.common.util.IconLoader;
import moe.gensoukyo.mcgproject.common.util.ShouterThread;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.lwjgl.opengl.Display;

import java.io.File;
import java.nio.file.Paths;

public class ClientProxy extends CommonProxy {

    @Override
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        //如果是32位java，抛出错误信息
        if (!Minecraft.getMinecraft().isJava64bit()) {
            MCGProject.logger.error("正在使用32位java");
            ShouterThread st = new ShouterThread("你正在使用32位java，请使用64位java，可在QQ群中下载");
            st.start();
        }

        super.preInit(event);
        MinecraftForge.EVENT_BUS.register(ModelMapper.instance());

        //加载图标，方法取自 https://www.curseforge.com/minecraft/mc-mods/its-the-little-things
        File di = Paths.get(event.getModConfigurationDirectory().getAbsolutePath(), "mcgproject").toFile();
        MCGProject.logger.info(di);
        if (di.exists()) {
            File icon = Paths.get(di.getAbsolutePath(), "icon.png").toFile();
            if(!icon.exists()) MCGProject.logger.info("未找到游戏图标");
            else if(!icon.isDirectory()) Display.setIcon(IconLoader.load(icon));
        } else {
            MCGProject.logger.error("未找到mcgproject目录");
            if (di.mkdir()) {
                MCGProject.logger.info("已生成mcgproject目录 ");
            }
        }

        //写入服务器信息，方法取自 https://www.curseforge.com/minecraft/mc-mods/its-the-little-things
        ServerList serverList = new ServerList(Minecraft.getMinecraft());
        int c = serverList.countServers();
        boolean foundServer = false;
        for (int i = 0; i < c; i++) {
            ServerData data = serverList.getServerData(i);

            if (data.serverIP.equals(MCGInformation.SERVER_IP)) {
                foundServer = true;
                break;
            }
        }
        if (!foundServer) {
            ServerData data = new ServerData(MCGInformation.SERVER_NAME, MCGInformation.SERVER_IP, false);
            serverList.addServerData(data);
            serverList.saveServerList();
            MCGProject.logger.info("已添加MCG服务器");
        }

        //加载窗口标题（预加载阶段）
        Display.setTitle("Minecraft幻想乡1.12.2");
    }

    @Override
    @EventHandler
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    @Override
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }

}
