package moe.gensoukyo.mcgproject.cilent.util;

import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResourceManager;

import javax.annotation.Nonnull;
import java.awt.image.BufferedImage;
import java.io.InputStream;

/**
 * @author drzzm32
 * @date 2020/4/12
 */
public class Texture extends AbstractTexture {

    protected final InputStream stream;
    protected BufferedImage image = null;

    public Texture(InputStream stream) {
        this.stream = stream;
    }

    public Texture(BufferedImage image) {
        this.stream = null;
        this.image = image;
    }

    public void preLoadTexture() {
        if (stream == null) return;
        try {
            image = TextureUtil.readBufferedImage(stream);
        } catch (Exception e) {
            MCGProject.logger.error(e.getMessage());
        }
    }

    public void loadTexture(@Nonnull IResourceManager manager) {
        this.deleteGlTexture();
        if (image == null) return;
        TextureUtil.uploadTextureImageAllocate(this.getGlTextureId(), image,
                false, false);
    }

}
