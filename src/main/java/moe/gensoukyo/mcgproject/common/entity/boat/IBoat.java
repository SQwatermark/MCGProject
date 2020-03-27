package moe.gensoukyo.mcgproject.common.entity.boat;

import net.minecraft.util.math.Vec3d;

/**
 * @author drzzm32
 * @date 2020/3/27
 */
interface IBoat {

    /**
     * 计算出一个垂直于这个向量的向量
     * */
    static Vec3d vVec(Vec3d vecIn) {
        double x = vecIn.x, y = vecIn.y, z = vecIn.z;
        Vec3d res = Vec3d.ZERO;
        if (x != 0) {
            res = new Vec3d(-(y + z) / x, 1, 1);
        } else if (y != 0) {
            res = new Vec3d(1, -(x + z) / y, 1);
        } else if (z != 0) {
            res = new Vec3d(1, 1, -(x + y) / z);
        }
        return res.normalize();
    }


    /**
     * @apiNote 给NPC预留的方法
     * @param threshold 撞击阈值速度
     * */
    void setThreshold(float threshold);

    /**
     * @apiNote 给NPC预留的方法
     * @param damage 撞击伤害
     * */
    void setMaxDamage(float damage);

    /**
     * @apiNote 给NPC预留的方法
     * @param mass 船体质量（撞击速率倍数）
     * */
    void setBoatMass(float mass);

    /**
     * @apiNote 给NPC预留的方法
     * @param jump 起跳速度
     * */
    void setBoatJump(float jump);

    /**
     * @apiNote 给NPC预留的方法
     * @param vel 硬质路面运行限速
     * */
    void setBoatMaxV(float vel);

    /**
     * @apiNote 给NPC预留的方法
     * @param vel 软质路面运行速度
     * */
    void setBoatMinV(float vel);

    /**
     * @apiNote 给NPC预留的方法
     * @param vel 前进加速度
     * */
    void setBoatAcc(float vel);

    /**
     * @apiNote 给NPC预留的方法
     * @param vel 减速加速度
     * */
    void setBoatBrk(float vel);

    float getThreshold();
    float getMaxDamage();
    float getBoatMass();
    float getBoatJump();
    float getBoatMaxV();
    float getBoatMinV();
    float getBoatAcc();
    float getBoatBrk();
    
    /**
     * @apiNote 用于调整船体颜色

     * @param color 船体颜色
     * */
    void setBoatColor(int color);
    int getBoatColor();

}
