package moe.gensoukyo.mcgproject.common.util;

import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.entity.Entity;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Stack;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 用于多线程中实体生成同步，通常通过脚本调用
 * @author drzzm32
 * @date 2020/3/27
 */
public class EntityPool {

    private static EntityPool instance;
    public static EntityPool instance() {
        if (instance == null)
            instance = new EntityPool();
        return instance;
    }

    static final ReentrantLock lock = new ReentrantLock();
    static final Stack<Entity> entityStack = new Stack<>();
    static final Stack<Entity> removalStack = new Stack<>();

    @SubscribeEvent
    public void tick(TickEvent.ServerTickEvent event) {
        while (!removalStack.isEmpty()) {
            Entity e = removalStack.pop();
            int dim = e.dimension;
            MCGProject.logger.debug("[EntityPool] Remove entity [{}] at dimension [{}]", e, dim);
            WorldServer world = DimensionManager.getWorld(dim);
            world.removeEntity(e);
        }
        while (!entityStack.isEmpty()) {
            Entity e = entityStack.pop();
            int dim = e.dimension;
            MCGProject.logger.debug("[EntityPool] Spawn entity [{}] at dimension [{}]", e, dim);
            WorldServer world = DimensionManager.getWorld(dim);
            world.spawnEntity(e);
        }
    }

    public static void put(Entity entity) {
        if (entity == null)
            return;
        lock.lock();
        entityStack.push(entity);
        lock.unlock();
    }

    public static void kill(Entity entity) {
        if (entity == null)
            return;
        lock.lock();
        removalStack.push(entity);
        lock.unlock();
    }

}
