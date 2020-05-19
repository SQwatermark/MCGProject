package moe.gensoukyo.mcgproject.server.feature.customnpcs;

import moe.gensoukyo.mcgproject.common.util.EntityPool;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import noppes.npcs.api.IWorld;
import noppes.npcs.api.entity.IEntity;
import noppes.npcs.controllers.ServerCloneController;
import noppes.npcs.entity.EntityNPCInterface;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 把NPC MOD的实体复制器覆盖掉
 * 换成线程安全并且不占用文件IO的版本
 * @author drzzm32
 * @date 2020/4/10
 */
@SideOnly(Side.SERVER)
public class CustomNPCsHook {

    private static CustomNPCsHook instance;
    public static CustomNPCsHook instance() {
        if (instance == null)
            instance = new CustomNPCsHook();
        return instance;
    }

    /**
     * @apiNote 用于替换原有的实体复制器
     * */
    @SubscribeEvent
    public void tick(TickEvent.ServerTickEvent event) {
        if (!(ServerCloneController.Instance instanceof MCGHandler)) {
            MCGProject.logger.info("[NPC Hook] Replace original ServerCloneController");
            ServerCloneController.Instance = new MCGHandler();
        }
    }

    /**
     * @apiNote 线程安全的链式哈希表
     * */
    public static class SafePool<K, V> extends LinkedHashMap<K, V> {

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

        @Override
        public void clear() {
            lock.lock();
            super.clear();
            lock.unlock();
        }
    }

    public static class MCGHandler extends ServerCloneController {

        private final long CURRENT_THREAD_ID;
        private static ReentrantLock lock = new ReentrantLock();
        protected static boolean LOADED = false, DO_SAVE = false;
        public static SafePool<Integer, SafePool<String, NBTTagCompound>> POOL = new SafePool<>();

        public void put(int tab, String name, @Nonnull NBTTagCompound tag) {
            if (!POOL.containsKey(tab))
                POOL.put(tab, new SafePool<>());
            POOL.get(tab).put(name, tag);
        }

        @Nullable
        public NBTTagCompound get(int tab, String name) {
            if (!POOL.containsKey(tab))
                return null;
            return POOL.get(tab).get(name);
        }

        public void purge(int tab, String name) {
            if (!POOL.containsKey(tab))
                return;
            POOL.get(tab).remove(name);
        }

        public MCGHandler() {
            super();
            LOADED = true;
            CURRENT_THREAD_ID = Thread.currentThread().getId();
        }

        /**
         * @apiNote 用于强制刷新存储
         * */
        public void refresh() {
            Class<?> cls = ServerCloneController.class;
            try {
                Method method = cls.getDeclaredMethod("loadClones");
                method.setAccessible(true);
                POOL.clear();
                DO_SAVE = true;
                method.invoke(this);
            } catch (Exception ignored) { }
            DO_SAVE = false;
        }

        /**
         * @apiNote 早期加载及运行中 set, add 方法更新
         * */
        @Override
        public void saveClone(int tab, String name, NBTTagCompound compound) {
            if (!LOADED || DO_SAVE)
                put(tab, name, compound);
            super.saveClone(tab, name, compound);
        }

        /**
         * @apiNote 游戏内GUI应该是调用的这个方法
         * */
        @Override
        public String addClone(NBTTagCompound compound, String name, int tab) {
            DO_SAVE = true;
            String res = super.addClone(compound, name, tab);
            DO_SAVE = false;
            return res;
        }

        /**
         * @apiNote 线程安全，忽略返回值
         * */
        @Nullable
        @Override
        public IEntity<?> spawn(double x, double y, double z, int tab, String name, IWorld world) {
            if (Thread.currentThread().getId() == CURRENT_THREAD_ID)
                return super.spawn(x, y, z, tab, name, world);

            NBTTagCompound tag = get(tab, name);
            if (tag == null) return null;
            Entity entity;
            lock.lock();
            {
                entity = EntityList.createEntityFromNBT(tag, world.getMCWorld());
            }
            lock.unlock();
            if (entity == null) return null;

            entity.setPosition(x, y, z);
            if (entity instanceof EntityNPCInterface) {
                EntityNPCInterface npc = (EntityNPCInterface) entity;
                npc.ais.setStartPos(new BlockPos(npc));
            }
            EntityPool.put(entity);

            return null;
        }

        /**
         * @apiNote 线程不安全，有文件IO
         * */
        @Override
        public IEntity<?> get(int tab, String name, IWorld world) {
            if (Thread.currentThread().getId() != CURRENT_THREAD_ID) {
                MCGProject.logger.info("[NPC Hook] You are calling a thread-unsafe method [{}]",
                        this.getClass().getName() + "." + "get"
                );
                return null;
            }

            return super.get(tab, name, world);
        }

        /**
         * @apiNote 线程不安全，有文件IO
         * */
        @Override
        public void set(int tab, String name, IEntity entity) {
            if (Thread.currentThread().getId() != CURRENT_THREAD_ID) {
                MCGProject.logger.info("[NPC Hook] You are calling a thread-unsafe method [{}]",
                        this.getClass().getName() + "." + "set"
                );
                return;
            }

            DO_SAVE = true;
            super.set(tab, name, entity);
            DO_SAVE = false;
        }

        /**
         * @apiNote 线程不安全，有文件IO
         * */
        @Override
        public void remove(int tab, String name) {
            if (Thread.currentThread().getId() != CURRENT_THREAD_ID) {
                MCGProject.logger.info("[NPC Hook] You are calling a thread-unsafe method [{}]",
                        this.getClass().getName() + "." + "remove"
                );
                return;
            }

            this.purge(tab, name);
            super.remove(tab, name);
        }

    }

}
