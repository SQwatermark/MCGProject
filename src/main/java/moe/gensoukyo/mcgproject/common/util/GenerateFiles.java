package moe.gensoukyo.mcgproject.common.util;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class GenerateFiles {

    public GenerateFiles()
    {

        //楼梯的示例
        //        for (EnumDyeColor color : EnumDyeColor.values()){
        //            stairs("wool_" + color.getName(), "blocks/wool_colored_" + color.getName());
        //        }
        //台阶的示例
        //        for (EnumDyeColor color : EnumDyeColor.values()){
        //            slab("wool_" + color.getName(), "blocks/wool_colored_" + color.getName());
        //        }

    }

    public void stairs(String name, String source)
    {
        AppendWriteFile("C:\\Users\\Lenovo\\Desktop\\blockstates\\stairs_" + name + ".json",
            "{\n" +
                    "  \"forge_marker\": 1,\n" +
                    "  \"defaults\": {\n" +
                    "    \"model\": \"mcgproject:stairs_" + name + "\"\n" +
                    "  },\n" +
                    "  \"variants\": {\n" +
                    "    \"inventory\": [{}],\n" +
                    "    \"facing=east,half=bottom,shape=straight\": {\"model\": \"mcgproject:stairs_" + name + "\"},\n" +
                    "    \"facing=west,half=bottom,shape=straight\": {\"model\": \"mcgproject:stairs_" + name + "\", \"y\": 180, \"uvlock\": true},\n" +
                    "    \"facing=south,half=bottom,shape=straight\": {\"model\": \"mcgproject:stairs_" + name + "\", \"y\": 90, \"uvlock\": true},\n" +
                    "    \"facing=north,half=bottom,shape=straight\": {\"model\": \"mcgproject:stairs_" + name + "\", \"y\": 270, \"uvlock\": true},\n" +
                    "    \"facing=east,half=bottom,shape=outer_right\": {\"model\": \"mcgproject:stairs_outer_" + name + "\"},\n" +
                    "    \"facing=west,half=bottom,shape=outer_right\": {\"model\": \"mcgproject:stairs_outer_" + name + "\", \"y\": 180, \"uvlock\": true},\n" +
                    "    \"facing=south,half=bottom,shape=outer_right\": {\"model\": \"mcgproject:stairs_outer_" + name + "\", \"y\": 90, \"uvlock\": true},\n" +
                    "    \"facing=north,half=bottom,shape=outer_right\": {\"model\": \"mcgproject:stairs_outer_" + name + "\", \"y\": 270, \"uvlock\": true},\n" +
                    "    \"facing=east,half=bottom,shape=outer_left\": {\"model\": \"mcgproject:stairs_outer_" + name + "\", \"y\": 270, \"uvlock\": true},\n" +
                    "    \"facing=west,half=bottom,shape=outer_left\": {\"model\": \"mcgproject:stairs_outer_" + name + "\", \"y\": 90, \"uvlock\": true},\n" +
                    "    \"facing=south,half=bottom,shape=outer_left\": {\"model\": \"mcgproject:stairs_outer_" + name + "\"},\n" +
                    "    \"facing=north,half=bottom,shape=outer_left\": {\"model\": \"mcgproject:stairs_outer_" + name + "\", \"y\": 180, \"uvlock\": true},\n" +
                    "    \"facing=east,half=bottom,shape=inner_right\": {\"model\": \"mcgproject:stairs_inner_" + name + "\"},\n" +
                    "    \"facing=west,half=bottom,shape=inner_right\": {\"model\": \"mcgproject:stairs_inner_" + name + "\", \"y\": 180, \"uvlock\": true},\n" +
                    "    \"facing=south,half=bottom,shape=inner_right\": {\"model\": \"mcgproject:stairs_inner_" + name + "\", \"y\": 90, \"uvlock\": true},\n" +
                    "    \"facing=north,half=bottom,shape=inner_right\": {\"model\": \"mcgproject:stairs_inner_" + name + "\", \"y\": 270, \"uvlock\": true},\n" +
                    "    \"facing=east,half=bottom,shape=inner_left\": {\"model\": \"mcgproject:stairs_inner_" + name + "\", \"y\": 270, \"uvlock\": true},\n" +
                    "    \"facing=west,half=bottom,shape=inner_left\": {\"model\": \"mcgproject:stairs_inner_" + name + "\", \"y\": 90, \"uvlock\": true},\n" +
                    "    \"facing=south,half=bottom,shape=inner_left\": {\"model\": \"mcgproject:stairs_inner_" + name + "\"},\n" +
                    "    \"facing=north,half=bottom,shape=inner_left\": {\"model\": \"mcgproject:stairs_inner_" + name + "\", \"y\": 180, \"uvlock\": true},\n" +
                    "    \"facing=east,half=top,shape=straight\": {\"model\": \"mcgproject:stairs_" + name + "\", \"x\": 180, \"uvlock\": true},\n" +
                    "    \"facing=west,half=top,shape=straight\": {\"model\": \"mcgproject:stairs_" + name + "\", \"x\": 180, \"y\": 180, \"uvlock\": true},\n" +
                    "    \"facing=south,half=top,shape=straight\": {\"model\": \"mcgproject:stairs_" + name + "\", \"x\": 180, \"y\": 90, \"uvlock\": true},\n" +
                    "    \"facing=north,half=top,shape=straight\": {\"model\": \"mcgproject:stairs_" + name + "\", \"x\": 180, \"y\": 270, \"uvlock\": true},\n" +
                    "    \"facing=east,half=top,shape=outer_right\": {\"model\": \"mcgproject:stairs_outer_" + name + "\", \"x\": 180, \"y\": 90, \"uvlock\": true},\n" +
                    "    \"facing=west,half=top,shape=outer_right\": {\"model\": \"mcgproject:stairs_outer_" + name + "\", \"x\": 180, \"y\": 270, \"uvlock\": true},\n" +
                    "    \"facing=south,half=top,shape=outer_right\": {\"model\": \"mcgproject:stairs_outer_" + name + "\", \"x\": 180, \"y\": 180, \"uvlock\": true},\n" +
                    "    \"facing=north,half=top,shape=outer_right\": {\"model\": \"mcgproject:stairs_outer_" + name + "\", \"x\": 180, \"uvlock\": true},\n" +
                    "    \"facing=east,half=top,shape=outer_left\": {\"model\": \"mcgproject:stairs_outer_" + name + "\", \"x\": 180, \"uvlock\": true},\n" +
                    "    \"facing=west,half=top,shape=outer_left\": {\"model\": \"mcgproject:stairs_outer_" + name + "\", \"x\": 180, \"y\": 180, \"uvlock\": true},\n" +
                    "    \"facing=south,half=top,shape=outer_left\": {\"model\": \"mcgproject:stairs_outer_" + name + "\", \"x\": 180, \"y\": 90, \"uvlock\": true},\n" +
                    "    \"facing=north,half=top,shape=outer_left\": {\"model\": \"mcgproject:stairs_outer_" + name + "\", \"x\": 180, \"y\": 270, \"uvlock\": true},\n" +
                    "    \"facing=east,half=top,shape=inner_right\": {\"model\": \"mcgproject:stairs_inner_" + name + "\", \"x\": 180, \"y\": 90, \"uvlock\": true},\n" +
                    "    \"facing=west,half=top,shape=inner_right\": {\"model\": \"mcgproject:stairs_inner_" + name + "\", \"x\": 180, \"y\": 270, \"uvlock\": true},\n" +
                    "    \"facing=south,half=top,shape=inner_right\": {\"model\": \"mcgproject:stairs_inner_" + name + "\", \"x\": 180, \"y\": 180, \"uvlock\": true},\n" +
                    "    \"facing=north,half=top,shape=inner_right\": {\"model\": \"mcgproject:stairs_inner_" + name + "\", \"x\": 180, \"uvlock\": true},\n" +
                    "    \"facing=east,half=top,shape=inner_left\": {\"model\": \"mcgproject:stairs_inner_" + name + "\", \"x\": 180, \"uvlock\": true},\n" +
                    "    \"facing=west,half=top,shape=inner_left\": {\"model\": \"mcgproject:stairs_inner_" + name + "\", \"x\": 180, \"y\": 180, \"uvlock\": true},\n" +
                    "    \"facing=south,half=top,shape=inner_left\": {\"model\": \"mcgproject:stairs_inner_" + name + "\", \"x\": 180, \"y\": 90, \"uvlock\": true},\n" +
                    "    \"facing=north,half=top,shape=inner_left\": {\"model\": \"mcgproject:stairs_inner_" + name + "\", \"x\": 180, \"y\": 270, \"uvlock\": true}\n" +
                    "  }\n" +
                    "}");

        AppendWriteFile("C:\\Users\\Lenovo\\Desktop\\models\\block\\stairs_" + name + ".json",
                "{\n" +
                        "  \"parent\": \"mcgproject:block/stairs_all\",\n" +
                        "  \"textures\": {\n" +
                        "    \"all\": \"" + source + "\"\n" +
                        "  }\n" +
                        "}"
                );

        AppendWriteFile("C:\\Users\\Lenovo\\Desktop\\models\\block\\stairs_inner_" + name + ".json",
                "{\n" +
                        "  \"parent\": \"mcgproject:block/stairs_inner_all\",\n" +
                        "  \"textures\": {\n" +
                        "    \"all\": \"" + source + "\"\n" +
                        "  }\n" +
                        "}"
                );

        AppendWriteFile("C:\\Users\\Lenovo\\Desktop\\models\\block\\stairs_outer_" + name + ".json",
                "{\n" +
                        "  \"parent\": \"mcgproject:block/stairs_outer_all\",\n" +
                        "  \"textures\": {\n" +
                        "    \"all\": \"" + source + "\"\n" +
                        "  }\n" +
                        "}"
                );

    }

    public void slab(String name, String source)
    {
        AppendWriteFile("C:\\Users\\Lenovo\\Desktop\\blockstates\\slab_" + name + ".json",
                "{\n" +
                        "    \"forge_marker\": 1,\n" +
                        "    \"defaults\": {\"model\": \"mcgproject:slab_half_" + name + "\"},\n" +
                        "    \"variants\": {\n" +
                        "        \"inventory\": [{}],\n" +
                        "        \"half=bottom\": { \"model\": \"mcgproject:slab_half_"  + name +  "\" },\n" +
                        "        \"half=top\": { \"model\": \"mcgproject:slab_upper_" + name + "\" }\n" +
                        "    }\n" +
                        "}");

        AppendWriteFile("C:\\Users\\Lenovo\\Desktop\\models\\block\\slab_half_" + name + ".json",
                "{\n" +
                        "    \"parent\": \"mcgproject:block/slab_half\",\n" +
                        "    \"textures\": {\n" +
                        "    \"all\": \"" + source + "\"\n" +
                        "    }\n" +
                        "}");

        AppendWriteFile("C:\\Users\\Lenovo\\Desktop\\models\\block\\slab_upper_" + name + ".json",
                "{\n" +
                        "    \"parent\": \"mcgproject:block/slab_upper\",\n" +
                        "    \"textures\": {\n" +
                        "    \"all\": \"" + source + "\"\n" +
                        "    }\n" +
                        "}");
    }


    /**
     * 生成文件
     * @param file 文件路径+文件名称
     * @param conent 要生成的文件内容
     */
    public static void AppendWriteFile(String file, String conent)
    {
        // Log4jBean.logger.info("开始以追加的形式写文件到：["+file+"]");
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
            out.write(conent);
            // Log4jBean.logger.info("写文件:["+file+"]完成");
        } catch (Exception e) {
            // Log4jBean.logger.error("写文件:["+file+"]异常，异常信息为:["+e.getMessage()+"]");
        } finally {
            // Log4jBean.logger.info("开始关闭输出流");
            try {
                assert out != null;
                out.close();
            } catch (IOException e) {
                // Log4jBean.logger.info("关闭输出流异常，异常信息为:["+e.getMessage()+"]");
            }
        }
    }

}

