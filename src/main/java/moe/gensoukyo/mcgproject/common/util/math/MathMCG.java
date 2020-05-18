package moe.gensoukyo.mcgproject.common.util.math;

import net.minecraft.util.math.MathHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MathMCG {

    /**
     * 将角度制转为弧度制
     * @param degrees 角度
     * @return 弧度
     */
    public static float degToRad(float degrees)
    {
        return degrees * (float)Math.PI / 180 ;
    }

    /**
     * 计算生物的肢体运动
     * @param tick 时刻
     * @param speed 肢体摆动速度，蝴蝶飞行时振翅速度为0.6f，停歇时为0.05f
     * @param amplitude 肢体摆动幅度，蝴蝶飞行时为1f，停歇时为0.05f
     * @return 当前时刻肢体摆动弧度
     */
    public static float swing(float tick, float speed, float amplitude, float phase) {
        return MathHelper.sin(tick*speed + phase)*amplitude;
    }

    /**
     * 判断字符串是否是纯数字
     * @param str 字符串
     * @return 是否
     */
    public static boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 权重随机数
     * @param weight [15,568,4181,2]
     * @return 索引值
     */
    public static int random(List<Integer> weight){
        List<Integer> weightTmp = new ArrayList<>(weight.size() + 1);
        weightTmp.add(0);
        Integer sum = 0;
        for(Integer d : weight){
            sum += d;
            weightTmp.add(sum);
        }
        Random random = new Random();
        int rand = random.nextInt(sum);
        int index = 0;
        for(int i = weightTmp.size()-1; i >0; i--){
            if( rand >= weightTmp.get(i)){
                index = i;
                break;
            }
        }
        return index;
    }
}
