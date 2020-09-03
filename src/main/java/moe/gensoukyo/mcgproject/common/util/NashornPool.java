package moe.gensoukyo.mcgproject.common.util;

import jdk.nashorn.internal.runtime.ScriptFunction;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.DimensionManager;

import javax.annotation.Nonnull;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * 脚本对象池
 * 游戏加载时从世界NBT里读取代码进行对象初始化
 * 供NPC使用，以实现单例模式
 * @author drzzm32
 * @date 2020/6/15
 */
public class NashornPool {

    public static class CodePool {

        private static final String NBT_TAG = "mcg.npc.codeMap";

        public static class SaveData extends WorldSavedData {

            public LinkedHashMap<String, String> codeMap;

            public SaveData(String mapName) {
                super(mapName);
                codeMap = new LinkedHashMap<>();
            }

            @Nonnull
            @Override
            public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound tagCompound) {
                if (codeMap.isEmpty())
                    return tagCompound;

                for (Map.Entry<String, String> i : codeMap.entrySet())
                    tagCompound.setString(i.getKey(), i.getValue());

                MCGProject.logger.info("CodePool saving...");

                return tagCompound;
            }

            @Override
            public void readFromNBT(@Nonnull NBTTagCompound tagCompound) {
                if (tagCompound.getKeySet().isEmpty())
                    return;

                MCGProject.logger.info("CodePool loading...");

                codeMap.clear();
                for (String key : tagCompound.getKeySet())
                    codeMap.put(key, tagCompound.getString(key));
            }

        }

        private static SaveData getData(World world) {
            MapStorage storage = world.getPerWorldStorage();
            SaveData data = (SaveData) storage.getOrLoadData(SaveData.class, NBT_TAG);
            if (data == null) {
                data = new SaveData(NBT_TAG);
                storage.setData(NBT_TAG, data);
            }
            return data;
        }

        public static String get(World world, String key) {
            SaveData data = getData(world);
            if (!data.codeMap.containsKey(key))
                return "";
            return data.codeMap.get(key);
        }

        public static void put(World world, String key, String value) {
            SaveData data = getData(world);
            data.codeMap.put(key, value);
            data.markDirty();
        }

        public static WorldServer world() {
            return DimensionManager.getWorld(0);
        }

        public static String get(String key) {
            return get(world(), key);
        }

        public static void put(String key, String value) {
            put(world(), key, value);
        }

        public static Set<String> keySet() {
            SaveData data = getData(world());
            return data.codeMap.keySet();
        }

        public static boolean has(String id) {
            SaveData data = getData(world());
            return data.codeMap.containsKey(id);
        }

        public static void remove(String id) {
            SaveData data = getData(world());
            data.codeMap.remove(id);
            data.markDirty();
        }

    }

    public static LinkedHashMap<String, Object> objMap = new LinkedHashMap<>();

    public static ScriptEngineFactory FACTORY = null;
    public static void initNashornPool(ScriptEngineFactory fac) {
        FACTORY = fac;
    }
    public static Object fromCode(String code) {
        if (FACTORY == null) return null;
        ScriptEngine engine = FACTORY.getScriptEngine();
        engine.getContext().setReader(new StringReader(""));
        engine.getContext().setWriter(new StringWriter());
        engine.getContext().setErrorWriter(new StringWriter());

        Object obj = null, res = null;
        try {
            res = engine.eval(code);
        } catch (Exception ignored) { }
        try {
            Invocable invocable = (Invocable) engine;
            obj = invocable.invokeFunction("build");
        } catch (Exception ex) {
            obj = res;
        }
        return obj;
    }

    /**
     * @apiNote 强制刷新对象池
     * */
    public static void refresh() {
        objMap.clear();
        for (String id : CodePool.keySet()) {
            Object obj = fromCode(CodePool.get(id));
            if (obj == null)
                continue;
            objMap.put(id, obj);
        }
    }

    /**
     * @apiNote 获取所有键
     * @return 所有对象的键表
     * */
    public static Set<String> keys() {
        return objMap.keySet();
    }

    /**
     * @apiNote 执行初始化函数，存入对象，并存入对象初始化代码
     * @param id 对象ID
     * @param code 对象初始化代码，需实现function build()，使用此函数的返回值；如果不声明build则直接使用代码最后一行的返回值
     * @return 如果存入成功，则为true，否则为false，并实际不存入
     * */
    public static boolean put(String id, String code) {
        Object obj = fromCode(code);
        if (obj == null) {
            remove(id);
            return false;
        }
        CodePool.put(id, code);
        objMap.put(id, obj);
        return true;
    }

    /**
     * @apiNote 执行初始化函数，存入对象，并存入对象初始化代码
     * @param id 对象ID
     * @param func 对象初始化函数，需保证函数声明为function build()，使用此函数的返回值；不声明build的情况下存入的是函数本身
     * @return 如果存入成功，则为true，否则为false，并实际不存入
     * */
    public static boolean put(String id, ScriptFunction func) {
        return put(id, func.toSource());
    }

    /**
     * @apiNote 查找是否存在对象
     * @param id 对象ID
     * @return 如果存在对象，则为true，否则为false
     * */
    public static boolean has(String id) {
        return objMap.containsKey(id);
    }

    /**
     * @apiNote 删除对象
     * @param id 对象ID
     * @return 如果删除成功，则为true，否则为false
     * */
    public static boolean remove(String id) {
        if (CodePool.has(id) || objMap.containsKey(id)) {
            CodePool.remove(id);
            objMap.remove(id);
            return true;
        }
        return false;
    }

    /**
     * @apiNote 获取对象
     * @param id 对象ID
     * @return 对象
     * */
    public static Object get(String id) {
        if (objMap.containsKey(id))
            return objMap.get(id);
        return null;
    }

}
