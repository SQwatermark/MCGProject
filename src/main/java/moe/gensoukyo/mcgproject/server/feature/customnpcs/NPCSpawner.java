package moe.gensoukyo.mcgproject.server.feature.customnpcs;

import moe.gensoukyo.mcgproject.common.util.math.MathMCG;
import moe.gensoukyo.mcgproject.common.util.math.Vec2d;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import noppes.npcs.api.NpcAPI;
import noppes.npcs.entity.EntityCustomNpc;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SideOnly(Side.SERVER)
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
    }

    @SubscribeEvent
    public void tick(TickEvent.WorldTickEvent event) {
        if (!MCGProject.INSTANCE.server.isDedicatedServer()) return;
        if (random.nextInt(config.interval) == 0) tryToSpawnAMob((WorldServer) event.world);
    }

    public void tryToSpawnAMob(WorldServer worldServer) {

        //随便找几个倒霉鬼
        List<EntityPlayer> list = worldServer.playerEntities;
        if (list.size() == 0) return;
        label:
        for (int i = 0; i < list.size() / 4 + 1; i++) {
            EntityPlayer player = list.get(random.nextInt(list.size()));

            //在这个倒霉鬼周围随便找个地方
            int r = config.minSpawnDistance + random.nextInt(config.maxSpawnDistance - config.minSpawnDistance);
            float angle = MathMCG.degToRad(random.nextInt(360));
            double x = player.posX + r * Math.cos(angle);
            double z = player.posZ + r * Math.sin(angle);
            //自下而上找个高度
            int y = 1;
            for (int j = 0; j < 255; j++) {
                if (worldServer.getBlockState(new BlockPos(x, j, z)).getBlock() == Blocks.AIR) {
                    y = j;
                    break;
                }
            }
            Vec3d place = new Vec3d(x, y, z);

            //选中的地点在不在某个刷怪区里
            for (MobSpawnRegion mobSpawnRegion : config.mobSpawnRegions) {
                //判断世界
                if (!worldServer.getWorldInfo().getWorldName().toLowerCase().equals(mobSpawnRegion.world.toLowerCase())) continue;
                //判断位置
                Vec2d vec2d = new Vec2d(place.x, place.z);
                //如果选中的地点周围怪太多，则不能生成
                if (worldServer.getEntitiesWithinAABB(EntityCustomNpc.class,
                        new AxisAlignedBB(x - 50, y - 50, z - 50, x + 50, y + 50, z + 50)).size() > mobSpawnRegion.density) continue;
                //要在刷怪区内
                if (mobSpawnRegion.region.isVecInRegion(vec2d)) {
                    //如果在黑名单内，则不刷怪
                    for (BlackListRegion blackListRegion : mobSpawnRegion.blackList) {
                        if (blackListRegion.region.isVecInRegion(vec2d)) continue label;
                    }
                    //如果在刷怪区且不在黑名单内，随便挑一个怪物生成
                    NPCMob mob = chooseAMobToSpawn(mobSpawnRegion);
                    if (mob != null) {
                        try {
                            NpcAPI.Instance().getClones().spawn(place.x, place.y, place.z,
                                    mob.tab, mob.name, NpcAPI.Instance().getIWorld(((EntityPlayerMP)player).getServerWorld()));
                            MCGProject.logger.info("MCGProject：NPC[" + mob.name + "]生成成功，位置" + place);
                        } catch (Exception e) {
                            MCGProject.logger.info("MCGProject：NPC[" + mob.name + "]生成失败，可能是配置文件中提供的信息有误");
                        }
                    }
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
