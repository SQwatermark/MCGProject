package moe.gensoukyo.mcgproject.common.feature.customnpcs;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import moe.gensoukyo.mcgproject.common.util.math.Region2d;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
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
    ArrayList<NPCRegion.MobSpawnRegion> mobSpawnRegions;
    //安全区的集合
    ArrayList<NPCRegion.BlackListRegion> blackListRegions;

    //配置文件
    public File file;

    private NPCSpawnerConfig() {
        minSpawnDistance = 12;
        maxSpawnDistance = 36;
        interval = 300;
        mobSpawnRegions = new ArrayList<>();
        blackListRegions = new ArrayList<>();
        this.refresh();
    }

    public void refresh() {
        if (MCGProject.modConfigDi.exists()) {
            file = Paths.get(MCGProject.modConfigDi.getAbsolutePath(), "npcspawner.json").toFile();
            if(!file.exists()) MCGProject.logger.info("未找到NPC生成配置");
            else if(!file.isDirectory()) {
                mobSpawnRegions.clear();
                blackListRegions.clear();
                try {
                    String content = FileUtils.readFileToString(file, "UTF-8");
                    JsonParser parser = new JsonParser();
                    JsonObject npcSpawnerConfigJson = (JsonObject) parser.parse(content);
                    minSpawnDistance = npcSpawnerConfigJson.get("minSpawnDistance").getAsInt();
                    maxSpawnDistance = npcSpawnerConfigJson.get("maxSpawnDistance").getAsInt();
                    interval = npcSpawnerConfigJson.get("interval").getAsInt();
                    JsonArray array = npcSpawnerConfigJson.get("mobSpawnRegions").getAsJsonArray();
                    for (int i = 0; i < array.size(); i++) {
                        JsonObject mobSpawnRegionJson = array.get(i).getAsJsonObject();
                        NPCRegion.MobSpawnRegion mobSpawnRegion = parseMobSpawnRegion(mobSpawnRegionJson);
                        if (mobSpawnRegion != null)
                            mobSpawnRegions.add(mobSpawnRegion);
                    }
                    try {
                        JsonArray array2 = npcSpawnerConfigJson.get("blackListRegions").getAsJsonArray();
                        for (int i = 0; i < array2.size(); i++) {
                            JsonObject blackListRegionJson = array2.get(i).getAsJsonObject();
                            NPCRegion.BlackListRegion blackListRegion = parseBlackListRegion(blackListRegionJson);
                            if (blackListRegion != null)
                                blackListRegions.add(blackListRegion);
                        }
                    } catch (Exception e) {
                        MCGProject.logger.info("MCGProject：未找到黑名单区域的配置");
                        e.printStackTrace();
                    }


                } catch (Exception e) {
                    MCGProject.logger.info("MCGProject：读取刷怪配置文件出错！");
                    e.printStackTrace();
                }
            }
        } else {
            MCGProject.logger.error("未找到mcgproject目录");
            if (MCGProject.modConfigDi.mkdir()) {
                MCGProject.logger.info("已生成mcgproject目录 ");
            }
        }
        for (NPCRegion.MobSpawnRegion mobSpawnRegion : this.mobSpawnRegions) {
            mobSpawnRegion.blackList.clear();
            for (NPCRegion.BlackListRegion blackListRegion : this.blackListRegions) {
                if (mobSpawnRegion.world.toLowerCase().equals(blackListRegion.world.toLowerCase())) {
                    if (mobSpawnRegion.region.isCoincideWith(blackListRegion.region)) {
                        mobSpawnRegion.blackList.add(blackListRegion);
                    }
                }
            }
        }
    }

    public NPCRegion.MobSpawnRegion parseMobSpawnRegion(JsonObject mobSpawnRegionJson) {
        try {
            String name = mobSpawnRegionJson.get("name").getAsString();
            String world = mobSpawnRegionJson.get("world").getAsString();
            JsonArray pos1 = mobSpawnRegionJson.get("pos1").getAsJsonArray();
            JsonArray pos2 = mobSpawnRegionJson.get("pos2").getAsJsonArray();
            int destiny = mobSpawnRegionJson.get("destiny").getAsInt();
            JsonArray mobs = mobSpawnRegionJson.get("mobs").getAsJsonArray();
            return new NPCRegion.MobSpawnRegion(name, new Region2d(pos1.get(0).getAsDouble(),
                    pos1.get(1).getAsDouble(), pos2.get(0).getAsDouble(), pos2.get(1).getAsDouble()), destiny, parseNPCMobs(mobs), world);
        } catch (Exception e) {
            MCGProject.logger.error("MCGProject：刷怪配置解析错误！刷怪区信息不符合规范！已跳过该刷怪区！");
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<NPCMob> parseNPCMobs(JsonArray mobsJson) {
        ArrayList<NPCMob> npcMobs = new ArrayList<>();
        for (int i = 0; i < mobsJson.size(); i++) {
            JsonObject mobJson = mobsJson.get(i).getAsJsonObject();
            NPCMob mob = parseNPCMob(mobJson);
            if (mob != null) npcMobs.add(mob);
        }
        return npcMobs;
    }

    @Nullable
    public NPCMob parseNPCMob(JsonObject mobJson) {
        try {
            int tab = mobJson.get("tab").getAsInt();
            String mobName = mobJson.get("name").getAsString();
            int weight = mobJson.get("weight").getAsInt();
            return new NPCMob(tab, mobName, weight);
        } catch (Exception e) {
            MCGProject.logger.error("MCGProject：刷怪配置解析错误！怪物信息不符合规范！已跳过该怪物！");
            e.printStackTrace();
            return null;
        }
    }

    public NPCRegion.BlackListRegion parseBlackListRegion(JsonObject blackListRegionJson) {
        try {
            String name = blackListRegionJson.get("name").getAsString();
            JsonArray pos1 = blackListRegionJson.get("pos1").getAsJsonArray();
            JsonArray pos2 = blackListRegionJson.get("pos2").getAsJsonArray();
            String world = blackListRegionJson.get("world").getAsString();
            return new NPCRegion.BlackListRegion(name, new Region2d(pos1.get(0).getAsDouble(),
                    pos1.get(1).getAsDouble(), pos2.get(0).getAsDouble(), pos2.get(1).getAsDouble()), true, world);
        } catch (Exception e) {
            MCGProject.logger.error("MCGProject：刷怪配置解析错误！安全区信息不符合规范！已跳过该安全区！");
            e.printStackTrace();
            return null;
        }
    }

    public static class CommandRefreshNPCSpawner extends CommandBase {

        @Override
        public String getName() {
            return "refreshNpcSpawner";
        }

        @Override
        public String getUsage(ICommandSender sender) {
            return null;
        }

        @Override
        public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
            NPCSpawnerConfig.instance().refresh();
            sender.sendMessage(new TextComponentString("已刷新，请通过控制台查看具体信息！"));
        }

    }

}