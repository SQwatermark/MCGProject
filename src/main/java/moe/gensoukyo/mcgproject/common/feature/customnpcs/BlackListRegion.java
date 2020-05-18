package moe.gensoukyo.mcgproject.common.feature.customnpcs;

import moe.gensoukyo.mcgproject.common.util.math.Region2d;

public class BlackListRegion {

    public String name;
    public Region2d region;
    public boolean delete;

    public BlackListRegion(String name, Region2d region, boolean delete) {
        this.name = name;
        this.region = region;
        this.delete = delete;
    }

}
