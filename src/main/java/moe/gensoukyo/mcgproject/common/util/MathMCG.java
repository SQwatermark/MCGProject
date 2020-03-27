package moe.gensoukyo.mcgproject.common.util;

import net.minecraft.util.math.MathHelper;

public class MathMCG {
    public static float degToRad(float degrees)
    {
        return degrees * (float)Math.PI / 180 ;
    }
    public static float swing(float tick, float speed, float amplitude) {
        return MathHelper.sin(tick*speed)*amplitude;
    }
}
