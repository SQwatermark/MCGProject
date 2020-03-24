package moe.gensoukyo.mcgproject.common.entity.butterfly;

import net.minecraft.entity.IEntityLivingData;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityRubySile extends EntityBasicButterfly
{

    public EntityRubySile(World worldIn)
    {
        super(worldIn);
    }

    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
        this.setVariant(ButterflyType.RUBYSILE.getMetadata());
        return super.onInitialSpawn(difficulty, livingdata);
    }

}