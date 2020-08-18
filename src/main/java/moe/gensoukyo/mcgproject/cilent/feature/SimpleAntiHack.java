package moe.gensoukyo.mcgproject.cilent.feature;

import moe.gensoukyo.mcgproject.cilent.util.ShouterThread;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.FileInputStream;

@SideOnly(Side.CLIENT)
public class SimpleAntiHack {

    private static SimpleAntiHack instance;
    public static SimpleAntiHack instance() {
        if(instance == null) instance = new SimpleAntiHack();
        return instance;
    }

    private String check() {
        String[] mods = {"forgewurst", "wwe", "forgehax", "kami", "kamiblue", "ares"};
        for (String s : mods) {
            if (Loader.isModLoaded(s)) {
                return s;
            }
        }
        try {
            if (Class.forName("net.impactclient.0") != null) {
                return "impactclient";
            }
        } catch (Exception ignored) {
        }
        try {
            if (Class.forName("net.wolframclient.Wolfram") != null) {
                return "Wolfram";
            }
        } catch (Exception ignored) {
        }
        try {
            File file = new File(MCGProject.modDi, "Armourers-Workshop-1.12.2-0.50.5.jar");
            if (Class.forName("moe.plushie.armourers_workshop.ArmourersWorkshop") != null) {
                if (!file.exists()) {
                    return "armors workshop name changed";
                } else if (!DigestUtils.md5Hex(new FileInputStream(file.getPath())).equals("cea2c120c8cc823a6de83a002b8ca375")) {
                    return  "armors workshop file changed";
                }
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    public void preInit() {
        String hack = this.check();
        if (hack != null) {
            MCGProject.logger.warn("AntiHack: 正在使用违规客户端[" + hack + "]");
            ShouterThread st = new ShouterThread("老大哥在看着你", "Cheat Client Detected");
            st.start();
            Minecraft.getMinecraft().shutdown();
        }
    }

}