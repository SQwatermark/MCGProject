package moe.gensoukyo.mcgproject.cilent.util;

import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author drzzm32
 * @date 2020/4/12
 */
public class TextureLoader {

    private static final ResourceLocation DEFAULT = new ResourceLocation(
            "textures/entity/end_portal.png");

    protected static InputStream getImageStream(String url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);
            connection.setRequestMethod("GET");
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                return connection.getInputStream();
            }
        } catch (IOException e) {
            try {
                IResourceManager manager = Minecraft.getMinecraft().getResourceManager();
                return manager.getResource(DEFAULT).getInputStream();
            } catch (IOException e1) {
                MCGProject.logger.error(e1.getMessage());
            }
            MCGProject.logger.error(e.getMessage());
        }
        return null;
    }

    protected static InputStream getImageStream(ResourceLocation loc) {
        try {
            IResourceManager manager = Minecraft.getMinecraft().getResourceManager();
            return manager.getResource(loc).getInputStream();
        } catch (IOException e) {
            try {
                IResourceManager manager = Minecraft.getMinecraft().getResourceManager();
                return manager.getResource(DEFAULT).getInputStream();
            } catch (IOException e1) {
                MCGProject.logger.error(e1.getMessage());
            }
            MCGProject.logger.error(e.getMessage());
        }
        return null;
    }

    protected static class SafePool<K, V> extends LinkedHashMap<K, V> {

        private final ReentrantLock lock = new ReentrantLock();

        public SafePool() { super(); }

        @Override
        public boolean containsKey(Object key) {
            if (lock.tryLock()) {
                try {
                    return super.containsKey(key);
                } finally {
                    lock.unlock();
                }
            }
            return false;
        }

        @Override
        public V put(K key, V value) {
            try {
                lock.lock();
                return super.put(key, value);
            } finally {
                lock.unlock();
            }
        }

        @Override
        public V get(Object key) {
            try {
                lock.lock();
                return super.get(key);
            } finally {
                lock.unlock();
            }
        }

        @Override
        public V remove(Object key) {
            try {
                lock.lock();
                return super.remove(key);
            } finally {
                lock.unlock();
            }
        }
    }

    protected static class SafeList<T> extends ArrayList<T> {

        private final ReentrantLock lock = new ReentrantLock();

        public SafeList() { super(); }

        @Override
        public boolean contains(Object o) {
            if (lock.tryLock()) {
                try {
                    return super.contains(o);
                } finally {
                    lock.unlock();
                }
            }
            return false;
        }

        @Override
        public T remove(int index) {
            try {
                lock.lock();
                return super.remove(index);
            } finally {
                lock.unlock();
            }
        }

        @Override
        public boolean add(T t) {
            try {
                lock.lock();
                return super.add(t);
            } finally {
                lock.unlock();
            }
        }
    }

    public static ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(
            Runtime.getRuntime().availableProcessors() / 2);
    public static SafePool<BlockPos, Texture> textures = new SafePool<>();
    public static SafeList<BlockPos> stack = new SafeList<>();

    public static void removeTexture(TileEntity tileEntity) {
        BlockPos pos = tileEntity.getPos();
        if (textures.containsKey(pos))
            textures.get(pos).deleteGlTexture();
        textures.remove(pos);
    }

    public static Texture getTexture(TileEntity tileEntity, String url) {
        BlockPos pos = tileEntity.getPos();
        if (textures.containsKey(pos)) {
            Texture texture = textures.get(pos);
            texture.loadTexture(Minecraft.getMinecraft().getResourceManager());
            return texture;
        } else if (!stack.contains(pos)) {
            stack.add(pos);
            executor.schedule(() -> {
                Texture texture = new Texture(getImageStream(url));
                texture.preLoadTexture();
                textures.put(pos, texture);
                stack.remove(pos);
            }, 10, TimeUnit.MILLISECONDS);
        }
        return null;
    }

    public static Point getTextureSize(ResourceLocation loc) {
        Texture texture = new Texture(getImageStream(loc));
        texture.preLoadTexture();
        Point size = new Point(0, 0);
        if (texture.image != null) {
            size.x = texture.image.getWidth();
            size.y = texture.image.getHeight();
        }
        return size;
    }

    public static Point getTextureSize(String url) {
        Texture texture = new Texture(getImageStream(url));
        texture.preLoadTexture();
        Point size = new Point(0, 0);
        if (texture.image != null) {
            size.x = texture.image.getWidth();
            size.y = texture.image.getHeight();
        }
        return size;
    }

}
