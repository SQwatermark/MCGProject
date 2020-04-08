package moe.gensoukyo.mcgproject.core;


import moe.gensoukyo.mcgproject.common.backpack.BackpackCore;
import moe.gensoukyo.mcgproject.common.feature.BetterSign;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

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
    public static final String VERSION = "1.0.17";
    public static Logger logger;

    @SidedProxy(clientSide = "moe.gensoukyo.mcgproject.core.ClientProxy",
            serverSide = "moe.gensoukyo.mcgproject.core.CommonProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        FluidRegistry.enableUniversalBucket();
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
        new BetterSign();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    @EventHandler
    public void serverLoad(FMLServerStartingEvent event)
    {
        event.registerServerCommand(new BackpackCore.BackpackCommand());
        event.registerServerCommand(new BackpackCore.BackpackManageCommand());
    }

    @Mod.EventHandler
    @SideOnly(Side.CLIENT)
    public void construction(FMLLoadCompleteEvent event) {
        //加载窗口标题（加载完成）
        Display.setTitle("Minecraft幻想乡1.12.2");
    }

}