package moe.gensoukyo.mcgproject.common.entity.butterfly;

import net.minecraft.entity.IEntityLivingData;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityButterfly extends EntityBasicButterfly
{

    public EntityButterfly(World worldIn)
    {
        super(worldIn);
    }

    public int getMaxSpawnedInChunk()
    {
        return 2;
    }

    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
        this.setVariant(ButterflyType.BASIC1.getMetadata());
        return super.onInitialSpawn(difficulty, livingdata);
    }

}