package moe.gensoukyo.mcgproject.common.util;

import net.minecraft.util.math.MathHelper;

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
    public static float swing(float tick, float speed, float amplitude) {
        return MathHelper.sin(tick*speed)*amplitude;
    }

}
