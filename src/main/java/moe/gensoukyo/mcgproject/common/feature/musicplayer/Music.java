package moe.gensoukyo.mcgproject.common.feature.musicplayer;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Music implements IMusic {
    private final String url;
    private double v,x,y,z;
    private final int start;
    public Music(String url, int start, double x, double y, double z){
        this.url = url;
        this.v = 0;
        this.x = x;
        this.y = y;
        this.z = z;
        this.start = start;
    }
    @Override
    public void update(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public InputStream openStream() throws IOException {
        return new URL(url).openStream();
    }

    @Override
    public void setMaxVolume(float max) {
        this.v = max;
    }

    @Override
    public float getVolume(double x, double y, double z) {
        float volume = (float) v;
        float distanceSq = (float) getDistanceSq(x, y, z);
        if (volume != 0) {
            float n = (1 + volume) * 20;
            float nn = n * n;
            float v;
            if (distanceSq <= nn) {
                v = distanceSq / 4 / nn;
            }
            else {
                v = (float) (- distanceSq / 12 / nn + 2 * Math.sqrt(distanceSq) / 3 / n - 1 / 3.0);
            }
            volume *= (1 - v);
        }
        return volume;
    }

    @Override
    public int getStart() {
        return start;
    }

    protected double getDistanceSq(double x, double y, double z){
        double disX = x - this.x;
        double disY = y - this.y;
        double disZ = z - this.z;
        return disX * disX + disY * disY + disZ * disZ;
    }
}
