package moe.gensoukyo.mcgproject.common.feature;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.BannerPattern;
import net.minecraftforge.common.util.EnumHelper;

public class MoreBannerPatterns {

    /**
     * 可随意添加旗帜图案，图片文件名为 pattern_index，放在 assets/minecraft/textures/entity/banner 目录下
     */
    public static void init() {
        //addPattern(1, new ItemStack(ModBlock.FLOWER));
    }

    public static void addPattern(int index, ItemStack itemStack) {
        String enumName = "PATTERN_" + index;
        String fileName = "pattern_" + index;
        String hashName = "mcg" + index;
        EnumHelper.addEnum(BannerPattern.class, enumName, new Class[] { String.class, String.class, ItemStack.class }, fileName, hashName, itemStack);
    }

}
