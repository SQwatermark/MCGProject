package moe.gensoukyo.mcgproject.common.feature.musicplayer;

import java.io.IOException;
import java.io.InputStream;

public interface IMusic {
    void update(double x, double y, double z);
    InputStream openStream() throws IOException;
    void setMaxVolume(float max);
    float getVolume(double x, double y, double z);
    int getStart();
}
