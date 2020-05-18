package moe.gensoukyo.mcgproject.common.feature.customnpcs;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import moe.gensoukyo.mcgproject.common.util.math.Region2d;
import moe.gensoukyo.mcgproject.core.MCGProject;
import org.apache.commons.io.FileUtils;

import javax.annotation.Nullable;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;

public class NPCSpawnerConfig {

    private static NPCSpawnerConfig instance;
    public static NPCSpawnerConfig instance() {
        if(instance == null) instance = new NPCSpawnerConfig();
        return instance;
    }

    //最小刷怪距离
    public int minSpawnDistance;
    //最大刷怪距离
    public int maxSpawnDistance;
    //刷怪触发间隔,以tick计算
    public int interval;
    //刷怪区的集合
    ArrayList<MobSpawnRegion> mobSpawnRegions;
    //安全区的集合
    ArrayList<BlackListRegion> blackListRegions;

    //配置文件
    public File file;

    private NPCSpawnerConfig() {
        minSpawnDistance = 12;
        maxSpawnDistance = 36;
        interval = 600;
        mobSpawnRegions = new ArrayList<>();
        blackListRegions = new ArrayList<>();
        this.refresh();
    }

    public void refresh() {
        if (MCGProject.modConfigDi.exists()) {
            file = Paths.get(MCGProject.modConfigDi.getAbsolutePath(), "npcspawner.json").toFile();
            if(!file.exists()) MCGProject.logger.info("未找到NPC生成配置");
            else if(!file.isDirectory()) {
                try {
                    String content = FileUtils.readFileToString(file, "UTF-8");
                    JsonParser parser = new JsonParser();
                    JsonObject object = (JsonObject) parser.parse(content);
                    minSpawnDistance = object.get("minSpawnDistance").getAsInt();
                    maxSpawnDistance = object.get("maxSpawnDistance").getAsInt();
                    interval = object.get("interval").getAsInt();
                    JsonArray array = object.get("mobSpawnRegions").getAsJsonArray();
                    for (int i = 0; i < array.size(); i++) {
                        JsonObject mobSpawnRegion = array.get(i).getAsJsonObject();
                        mobSpawnRegions.add(parseMobSpawnRegion(mobSpawnRegion));
                    }

                } catch (Exception e) {
                    MCGProject.logger.info("MCGProject：读取刷怪配置文件出错！");
                }
            }
        } else {
            MCGProject.logger.error("未找到mcgproject目录");
            if (MCGProject.modConfigDi.mkdir()) {
                MCGProject.logger.info("已生成mcgproject目录 ");
            }
        }
    }

    public MobSpawnRegion parseMobSpawnRegion(JsonObject mobSpawnRegion) {
        try {
            String name = mobSpawnRegion.get("name").getAsString();
            JsonArray pos1 = mobSpawnRegion.get("pos1").getAsJsonArray();
            JsonArray pos2 = mobSpawnRegion.get("pos2").getAsJsonArray();
            int max = mobSpawnRegion.get("max").getAsInt();
            JsonArray mobs = mobSpawnRegion.get("mobs").getAsJsonArray();
            return new MobSpawnRegion(name, new Region2d(pos1.get(0).getAsDouble(),
                    pos1.get(1).getAsDouble(), pos2.get(0).getAsDouble(), pos2.get(1).getAsDouble()), max, parseNPCMobs(mobs));
        } catch (Exception e) {
            MCGProject.logger.error("MCGProject：刷怪配置解析错误！刷怪区信息不符合规范！已跳过该刷怪区！");
            return null;
        }
    }

    public ArrayList<NPCMob> parseNPCMobs(JsonArray mobs) {
        ArrayList<NPCMob> npcMobs = new ArrayList<>();
        for (int i = 0; i < mobs.size(); i++) {
            JsonObject mob = mobs.get(i).getAsJsonObject();
            NPCMob npcMob = parseNPCMob(mob);
            if (npcMob != null) npcMobs.add(npcMob);
        }
        return npcMobs;
    }

    @Nullable
    public NPCMob parseNPCMob(JsonObject mob) {
        try {
            int tab = mob.get("tab").getAsInt();
            String mobName = mob.get("name").getAsString();
            int maxMob = mob.get("max").getAsInt();
            int weight = mob.get("weight").getAsInt();
            return new NPCMob(tab, mobName, maxMob, weight);
        } catch (Exception e) {
            MCGProject.logger.error("MCGProject：刷怪配置解析错误！怪物信息不符合规范！已跳过该怪物！");
            return null;
        }
    }

}
