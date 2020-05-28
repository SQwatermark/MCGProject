package moe.gensoukyo.mcgproject.cilent.feature;

import moe.gensoukyo.mcgproject.cilent.util.ShouterThread;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
        return null;
    }

    public void preInit() {
        String hack = this.check();
        if (hack != null) {
            MCGProject.logger.warn("AntiHack: 正在使用外挂[" + hack + "]");
            ShouterThread st = new ShouterThread("老大哥在看着你", "Cheat Client Detected");
            st.start();
            Minecraft.getMinecraft().shutdown();
        }
    }

}