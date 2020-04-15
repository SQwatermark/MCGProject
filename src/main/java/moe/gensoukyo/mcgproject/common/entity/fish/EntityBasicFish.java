package moe.gensoukyo.mcgproject.common.entity.fish;

import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.world.World;

public class EntityBasicFish extends EntityWaterMob {

    public EntityBasicFish(World worldIn) {
        super(worldIn);
        this.setSize(0.6F, 0.5F);
    }

}
