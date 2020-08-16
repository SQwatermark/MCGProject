package moe.gensoukyo.mcgproject.cilent.feature;

import moe.gensoukyo.mcgproject.cilent.util.IconLoader;
import moe.gensoukyo.mcgproject.cilent.util.ShouterThread;
import moe.gensoukyo.mcgproject.core.Information;
import moe.gensoukyo.mcgproject.core.MCGProject;
import moe.gensoukyo.mcgproject.core.MCG;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.Display;

import java.io.File;
import java.nio.file.Paths;

@Information(author = {"Zlepper", "SQwatermark"}, licence = "All Rights Reserved", source = "https://github.com/zlepper/itlt")
@SideOnly(Side.CLIENT)
public class ItitFeatures {

    public static void yellAtJava32x() {
        if (!Minecraft.getMinecraft().isJava64bit()) {
            MCGProject.logger.error("正在使用32位java");
            ShouterThread st = new ShouterThread("你正在使用32位java，请使用64位java，可在QQ群中下载", "Java version issue");
            st.start();
        }
    }

    public static void loadIcons() {
        if (MCGProject.modConfigDi.exists()) {
            File icon = Paths.get(MCGProject.modConfigDi.getAbsolutePath(), "icon.png").toFile();
            if(!icon.exists()) MCGProject.logger.info("未找到游戏图标");
            else if(!icon.isDirectory()) Display.setIcon(IconLoader.load(icon));
        } else {
            MCGProject.logger.error("未找到mcgproject目录");
            if (MCGProject.modConfigDi.mkdir()) {
                MCGProject.logger.info("已生成mcgproject目录 ");
            }
        }
    }

    public static void addServerInformation() {
        ServerList serverList = new ServerList(Minecraft.getMinecraft());
        int c = serverList.countServers();
        boolean foundServer = false;
        for (int i = 0; i < c; i++) {
            ServerData data = serverList.getServerData(i);

            if (data.serverIP.equals(MCG.SERVER_IP)) {
                foundServer = true;
                break;
            }
        }
        if (!foundServer) {
            ServerData data = new ServerData(MCG.SERVER_NAME, MCG.SERVER_IP, false);
            serverList.addServerData(data);
            serverList.saveServerList();
            MCGProject.logger.info("已添加MCG服务器");
        }
    }
}
