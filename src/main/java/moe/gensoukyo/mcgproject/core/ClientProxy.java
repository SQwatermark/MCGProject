package moe.gensoukyo.mcgproject.core;

import moe.gensoukyo.mcgproject.cilent.feature.CustomMainMenu;
import moe.gensoukyo.mcgproject.cilent.feature.ItitFeatures;
import moe.gensoukyo.mcgproject.cilent.feature.SimpleAntiHack;
import moe.gensoukyo.mcgproject.cilent.gui.PackAdmin;
import moe.gensoukyo.mcgproject.cilent.init.ModelMapper;
import moe.gensoukyo.mcgproject.common.feature.rsgauges.ModRsGauges;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
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
        SimpleAntiHack.instance().preInit();
        MinecraftForge.EVENT_BUS.register(SimpleAntiHack.instance());
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

        PackAdmin.setInvoke(((name, type) -> {
            Minecraft mc = Minecraft.getMinecraft();
            mc.addScheduledTask(() -> {
                String cmd = "/mcgPack show " + name + " " + type;
                mc.player.sendChatMessage(cmd);
            });
        }));
    }

}
