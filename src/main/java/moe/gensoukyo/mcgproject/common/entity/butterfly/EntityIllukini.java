package moe.gensoukyo.mcgproject.common.entity.butterfly;

import moe.gensoukyo.mcgproject.common.entity.MCGEntity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import javax.annotation.Nullable;

//代码取自 kathairis mod 并进行了修改，仅用于测试
@MCGEntity("butterfly_3")
public class EntityIllukini extends EntityBasicButterfly
{

    public EntityIllukini(World worldIn)
    {
        super(worldIn);
    }

    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
        this.setVariant(ButterflyType.ILLUKINI.getMetadata());
        return super.onInitialSpawn(difficulty, livingdata);
    }

}