package moe.gensoukyo.mcgproject.core;

import moe.gensoukyo.mcgproject.cilent.feature.CustomMainMenu;
import moe.gensoukyo.mcgproject.cilent.feature.ItitFeatures;
import moe.gensoukyo.mcgproject.cilent.feature.musicPlayer.ClientMusicManager;
import moe.gensoukyo.mcgproject.cilent.feature.musicPlayer.StreamStopper;
import moe.gensoukyo.mcgproject.cilent.init.ModelMapper;
import moe.gensoukyo.mcgproject.common.feature.musicplayer.IMusicManager;
import moe.gensoukyo.mcgproject.common.feature.rsgauges.ModRsGauges;
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
        MinecraftForge.EVENT_BUS.register(StreamStopper.instance());
        ModRsGauges.client.preInit(event);
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

    @Override
    public IMusicManager getMusicManager() {
        if (musicManager == null) musicManager = new ClientMusicManager();
        return musicManager;
    }
}
