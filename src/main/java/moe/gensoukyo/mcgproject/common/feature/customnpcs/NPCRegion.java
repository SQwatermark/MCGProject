package moe.gensoukyo.mcgproject.common.feature.customnpcs;

import moe.gensoukyo.mcgproject.common.util.math.Region2d;

import java.util.ArrayList;

public class NPCRegion {

    public String name;
    public Region2d region;
    public String world;

    /**
     * 刷怪区
     */
    public static class MobSpawnRegion extends NPCRegion {

        public int density;
        public ArrayList<NPCMob> mobs;
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

    /**
     * 黑名单区域（安全区，不刷怪）
     */
    public static class BlackListRegion extends NPCRegion {

        public boolean delete;
        public BlackListRegion(String name, Region2d region, boolean delete, String world) {
            this.name = name;
            this.region = region;
            this.delete = delete;
            this.world = world;
        }

    }

}
