package moe.gensoukyo.mcgproject.common.entity.butterfly;

import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.world.World;

public abstract class EntityBasicButterfly extends EntityAmbientCreature {

    public EntityBasicButterfly(World worldIn) {
        super(worldIn);
        this.setSize(0.6F, 0.5F);
    }

}