package moe.gensoukyo.mcgproject.server.feature.customnpcs;

import moe.gensoukyo.mcgproject.common.util.math.Region2d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;

/**
 * 刷怪区
 */
@SideOnly(Side.SERVER)
public class MobSpawnRegion {

    public String name;
    public Region2d region;
    public int density;
    public ArrayList<NPCMob> mobs;
    public String world;
    /*
        在初始化时会把接壤的黑名单区域存入这里，减少刷怪时需要检测的区域
     */
    public ArrayList<BlackListRegion> blackList;

    public MobSpawnRegion(String name, Region2d region, int density, ArrayList<NPCMob> mobs, String world) {
        this.name = name;
        this.region = region;
        this.density = density;
        this.mobs = mobs;
        this.blackList = new ArrayList<>();
        this.world = world;
    }

}
