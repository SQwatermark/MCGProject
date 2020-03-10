package moe.gensoukyo.mcgproject.common.util;

import moe.gensoukyo.mcgproject.core.MCGProject;

/**
 * @author SQwatermark
 * @date 2020/2/16
 * 用于生成一些字符串
 */
public class GetString {

    //生成方块的translationKey
    public static String blockKey(String name){
        return "tile." + MCGProject.ID + "." + name;
    }

}
