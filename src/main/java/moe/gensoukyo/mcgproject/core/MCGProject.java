package moe.gensoukyo.mcgproject.core;

import moe.gensoukyo.mcgproject.common.feature.NoRecipeBook;
import moe.gensoukyo.mcgproject.common.feature.backpack.BackpackCore;
import moe.gensoukyo.mcgproject.common.feature.rsgauges.ModRsGauges;
import moe.gensoukyo.mcgproject.common.feature.sticker.TileSticker;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
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
    public static final String VERSION = "1.1.5";

    public static Logger logger;
    public static File modConfigDi;

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
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event);

        ModRsGauges.INSTANCE.attachLogger(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        proxy.postInit(event);

        ModRsGauges.INSTANCE.postInit(event);
    }

    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        event.registerServerCommand(new BackpackCore.BackpackCommand());
        event.registerServerCommand(new BackpackCore.BackpackManageCommand());
        event.registerServerCommand(new TileSticker.RefreshCommand());
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


    //TODO: 音效方块
    //TODO: 图书馆
    //TODO：荆棘（玩家碰到会掉血）
    //TODO: 玩家在其中会下陷的泥巴（沼泽）
    //TODO: 水子的石头
    //TODO: 恐龙和普通生物的模型

}