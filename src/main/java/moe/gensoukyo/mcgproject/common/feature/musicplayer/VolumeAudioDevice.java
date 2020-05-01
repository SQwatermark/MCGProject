package moe.gensoukyo.mcgproject.common.feature.musicplayer;

import javazoom.jl.decoder.Decoder;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.FactoryRegistry;

public class VolumeAudioDevice implements AudioDevice {
    private final AudioDevice device;
    private float volume = 0.0f;
    private float targetVolume = 0.0f;
    public VolumeAudioDevice() throws JavaLayerException {
        this.device = FactoryRegistry.systemRegistry().createAudioDevice();
    }

    @Override
    public void open(Decoder decoder) throws JavaLayerException {
        device.open(decoder);
    }

    @Override
    public boolean isOpen() {
        return device.isOpen();
    }

    @Override
    public void write(short[] samples, int offs, int len) throws JavaLayerException {
        float step = (targetVolume - volume) / Math.min(samples.length, 1);
        for (int samp = 0; samp < samples.length; samp++)
        {
            samples[samp] = (short)(samples[samp] * (step * samp + volume));
        }
        volume = targetVolume;
        device.write(samples, offs, len);
    }

    @Override
    public void close() {
        device.close();
    }

    @Override
    public void flush() {
        device.flush();
    }

    @Override
    public int getPosition() {
        return device.getPosition();
    }

    public void setVolume(float v){
        targetVolume = Math.min(Math.max(v,0.0f),1.0f);
    }

    public float getVolume() {
        return targetVolume;
    }
}
