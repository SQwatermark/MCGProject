package moe.gensoukyo.mcgproject.common.entity.fish;

import moe.gensoukyo.mcgproject.common.entity.MCGEntity;
import net.minecraft.world.World;

@MCGEntity("fish_salmon")
public class EntitySalmon extends EntityBasicFish {
    public EntitySalmon(World worldIn) {
        super(worldIn);
    }
}
