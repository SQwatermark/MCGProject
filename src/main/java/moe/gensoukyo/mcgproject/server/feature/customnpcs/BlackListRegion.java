package moe.gensoukyo.mcgproject.server.feature.customnpcs;

import moe.gensoukyo.mcgproject.common.util.math.Region2d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 黑名单区域（安全区，不刷怪）
 */
@SideOnly(Side.SERVER)
public class BlackListRegion {

    public String name;
    public Region2d region;
    public boolean delete;
    public String world;

    public BlackListRegion(String name, Region2d region, boolean delete, String world) {
        this.name = name;
        this.region = region;
        this.delete = delete;
        this.world = world;
    }

}
