package moe.gensoukyo.mcgproject.common.feature.customnpcs;

import moe.gensoukyo.mcgproject.common.util.math.MathMCG;
import moe.gensoukyo.mcgproject.common.util.math.Vec2d;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import noppes.npcs.api.NpcAPI;
import noppes.npcs.entity.EntityCustomNpc;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

;

public class NPCSpawner {

    private static NPCSpawner instance;
    public static NPCSpawner instance() {
        if(instance == null) instance = new NPCSpawner();
        return instance;
    }

    private NPCSpawnerConfig config;
    Random random;

    private NPCSpawner() {
        this.config = NPCSpawnerConfig.instance();
        random = new Random();
        this.initialize();
    }

    public void initialize() {
        for (MobSpawnRegion mobSpawnRegion : config.mobSpawnRegions) {
            for (BlackListRegion blackListRegion : config.blackListRegions) {
                if (mobSpawnRegion.region.isCoincideWith(blackListRegion.region)) {
                    mobSpawnRegion.blackList.add(blackListRegion);
                }
            }
        }
    }

    @SubscribeEvent
    public void tick(TickEvent.ServerTickEvent event) {
        if (!MCGProject.INSTANCE.server.isDedicatedServer()) return;
        if (random.nextInt(config.interval) == 0) tryToSpawnAMob();
    }

    public void tryToSpawnAMob() {

        //随便找一个倒霉鬼
        List<EntityPlayerMP> list = MCGProject.INSTANCE.server.getPlayerList().getPlayers();
        if (list.size() == 0) return;
        EntityPlayerMP player = list.get(random.nextInt(list.size()));

        //在这个倒霉鬼周围随便找个地方
        int r = config.minSpawnDistance + random.nextInt(config.maxSpawnDistance - config.minSpawnDistance);
        float angle = MathMCG.degToRad(random.nextInt(360));
        double x = player.posX + r * Math.cos(angle);
        double z = player.posZ + r * Math.sin(angle);
        int y = player.world.getHeight((int)x, (int)z);
        Vec3d place = new Vec3d(x, y, z);

        //如果选中的地点周围怪太多，则不能生成
        if (player.world.getEntitiesWithinAABB(EntityCustomNpc.class,
                new AxisAlignedBB(x - 15, y - 15, z - 15, z + 15, y + 15, z + 15)).size() > 4) return;

        //选中的地点在不在某个刷怪区里
        for (MobSpawnRegion mobSpawnRegion : config.mobSpawnRegions) {
            Vec2d vec2d = new Vec2d(place.x, place.z);
            if (mobSpawnRegion.region.isVecInRegion(vec2d)) {
                //如果在黑名单内，则不刷怪
                for (BlackListRegion blackListRegion : mobSpawnRegion.blackList) {
                    if (blackListRegion.region.isVecInRegion(vec2d)) return;
                }
                //如果在刷怪区且不在黑名单内，随便挑一个怪物生成
                NPCMob mob = chooseAMobToSpawn(mobSpawnRegion);
                if (mob != null)
                    try {
                        NpcAPI.Instance().getClones().spawn(place.x, place.y, place.z,
                            mob.tab, mob.name, NpcAPI.Instance().getIWorld(player.getServerWorld()));
                    } catch (Exception e) {
                        MCGProject.logger.info("MCGProject：NPC生成失败，可能是配置文件中提供的信息有误");
                    }

            }
        }
    }

    /**
     * 按权重随机挑一个NPC怪物
     * @param mobSpawnRegion 刷怪区
     * @return 选出的NPC
     */
    @Nullable
    public NPCMob chooseAMobToSpawn(MobSpawnRegion mobSpawnRegion) {
        ArrayList<Integer> weights = new ArrayList<>();
        for (NPCMob mob : mobSpawnRegion.mobs) {
            weights.add((int)mob.weight * 100);
        }
        return mobSpawnRegion.mobs.get(MathMCG.random(weights));
    }

}
