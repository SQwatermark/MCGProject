package moe.gensoukyo.mcgproject.core;

import moe.gensoukyo.mcgproject.cilent.feature.CustomMainMenu;
import moe.gensoukyo.mcgproject.cilent.feature.ItitFeatures;
import moe.gensoukyo.mcgproject.cilent.init.ModelMapper;
import moe.gensoukyo.mcgproject.common.feature.NoRecipeBook;
import moe.gensoukyo.mcgproject.common.feature.rsgauges.ModRsGauges;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.*;
import org.lwjgl.opengl.Display;

public class ClientProxy extends CommonProxy {

    @Override
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        //如果是32位java，抛出错误信息
        ItitFeatures.yellAtJava32x();
        //设置窗口标题（预加载阶段）
        Display.setTitle("Minecraft幻想乡1.12.2");
        //加载游戏图标
        ItitFeatures.loadIcons();
        //写入服务器信息
        ItitFeatures.addServerInformation();
        MinecraftForge.EVENT_BUS.register(ModelMapper.instance());
        MinecraftForge.EVENT_BUS.register(CustomMainMenu.instance());
        ModRsGauges.client.preInit(event);
    }

    @Override
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        super.init(event);
        ModRsGauges.client.init(event);
    }

    @Override
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
        ModRsGauges.client.postInit(event);
    }

    @Mod.EventHandler
    public void loadComplete(FMLLoadCompleteEvent event) {
        //设置窗口标题（加载完成时）
        Display.setTitle("Minecraft幻想乡1.12.2");
    }

    @Mod.EventHandler
    public void constructMod(FMLConstructionEvent event) {
        //隐藏玩家物品栏界面的合成书
        MinecraftForge.EVENT_BUS.register(new NoRecipeBook());
    }

}
