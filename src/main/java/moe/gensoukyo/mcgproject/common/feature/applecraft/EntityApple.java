package moe.gensoukyo.mcgproject.common.feature.applecraft;

import moe.gensoukyo.mcgproject.common.entity.MCGEntity;
import moe.gensoukyo.mcgproject.common.init.ModItem;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import java.util.Random;

@MCGEntity("mcg_apple")
public class EntityApple extends EntityLiving {

    public static final DataParameter<Boolean> BAD = EntityDataManager.createKey(EntityApple.class, DataSerializers.BOOLEAN);

    private int age;
    private boolean bad;
    Random random;

    public EntityApple(World worldIn) {
        super(worldIn);
        this.random = new Random();
        this.age = 600 + random.nextInt(4800);
        this.bad = false;
        this.setSize(0.4F, 0.4F);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(BAD, false);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!world.isRemote) {
            if (this.ticksExisted >= age && !this.bad) {
                this.bad = true;
                this.dataManager.set(BAD, true);
            } else if (this.ticksExisted >= age + 400) {
                this.setDead();
            }
        }
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (!world.isRemote) {
            if (!this.bad) {
                this.entityDropItem(new ItemStack(ModItem.ITEM_MCG_FOOD, 1, 40), 0);
            } else {
                this.dropItem(Items.APPLE, 1);
            }
            this.setDead();
        }
        return true;
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        this.bad = compound.getBoolean("bad");
        this.dataManager.set(BAD, bad);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        compound.setBoolean("bad", bad);
    }

    protected boolean processInteract(EntityPlayer player, EnumHand hand) {
        if (!world.isRemote) {
            if (this.bad) {
                this.entityDropItem(new ItemStack(ModItem.ITEM_MCG_FOOD, 1, 40), 0);
            } else {
                this.dropItem(Items.APPLE, 1);
            }
            this.setDead();
        }
        return true;
    }

}
