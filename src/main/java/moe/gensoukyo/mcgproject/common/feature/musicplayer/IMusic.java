package moe.gensoukyo.mcgproject.common.feature.musicplayer;

import net.minecraft.world.World;

import java.io.IOException;
import java.io.InputStream;

public interface IMusic {
    void update(World world, double x, double y, double z);
    InputStream openStream() throws IOException;
    void setMaxVolume(float max);
    float getVolume(World world, double x, double y, double z);
    int getStart();
    World getWorld();
}
