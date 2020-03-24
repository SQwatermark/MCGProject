package moe.gensoukyo.mcgproject.common.entity.butterfly;

import net.minecraft.entity.IEntityLivingData;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityButterfly1 extends EntityBasicButterfly
{

    public EntityButterfly1(World worldIn)
    {
        super(worldIn);
    }

    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
        this.setVariant(ButterflyType.BASIC2.getMetadata());
        return super.onInitialSpawn(difficulty, livingdata);
    }

}