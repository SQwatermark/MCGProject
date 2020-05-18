package moe.gensoukyo.mcgproject.common.feature.customnpcs;

import moe.gensoukyo.mcgproject.common.util.math.Region2d;

import java.util.ArrayList;

public class MobSpawnRegion {

    public String name;
    public Region2d region;
    public int max;
    public ArrayList<NPCMob> mobs;
    public ArrayList<BlackListRegion> blackList;

    public MobSpawnRegion(String name, Region2d region, int  max, ArrayList<NPCMob> mobs) {
        this.name = name;
        this.region = region;
        this.max = max;
        this.mobs = mobs;
        this.blackList = new ArrayList<>();
    }

}
