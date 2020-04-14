package moe.gensoukyo.mcgproject.common.util;

import net.minecraft.block.material.Material;

public class MaterialHelper {

    public static String getMaterial(Material materialIn) {
        String s = "未知材料";
        if (materialIn == Material.AIR) s = "空气";
        else if (materialIn == Material.GRASS) s = "草";
        else if (materialIn == Material.GROUND) s = "泥土";
        else if (materialIn == Material.WOOD) s = "木头";
        else if (materialIn == Material.ROCK) s = "岩石";
        else if (materialIn == Material.IRON) s = "金属";
        else if (materialIn == Material.ANVIL) s = "金属";
        else if (materialIn == Material.WATER) s = "水";
        else if (materialIn == Material.LAVA) s = "熔岩";
        else if (materialIn == Material.LEAVES) s = "树叶";
        else if (materialIn == Material.PLANTS) s = "植物";
        else if (materialIn == Material.VINE) s = "藤蔓";
        else if (materialIn == Material.SPONGE) s = "海绵";
        else if (materialIn == Material.CLOTH) s = "布料";
        else if (materialIn == Material.FIRE) s = "火";
        else if (materialIn == Material.SAND) s = "沙子";
        else if (materialIn == Material.CIRCUITS) s = "红石和石头";
        else if (materialIn == Material.CARPET) s = "地毯";
        else if (materialIn == Material.GLASS) s = "玻璃";
        else if (materialIn == Material.REDSTONE_LIGHT) s = "草";
        else if (materialIn == Material.TNT) s = "三硝基甲苯";
        else if (materialIn == Material.CORAL) s = "不属于陆地上的植物";
        else if (materialIn == Material.ICE) s = "冰";
        else if (materialIn == Material.PACKED_ICE) s = "冰";
        else if (materialIn == Material.SNOW) s = "雪";
        else if (materialIn == Material.CRAFTED_SNOW) s = "雪";
        else if (materialIn == Material.CACTUS) s = "草";
        else if (materialIn == Material.CLAY) s = "黏土";
        else if (materialIn == Material.GOURD) s = "瓜";
        else if (materialIn == Material.DRAGON_EGG) s = "龙蛋";
        else if (materialIn == Material.PORTAL) s = "草";
        else if (materialIn == Material.CAKE) s = "蛋糕";
        else if (materialIn == Material.WEB) s = "蛛丝";

        return s;
    }

}
