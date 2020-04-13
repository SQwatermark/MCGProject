package moe.gensoukyo.mcgproject.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

@MCGEntity("gap")
public class EntityGap extends Entity {

    private EntityGap partner;
    private int age;

    public EntityGap(World worldIn) {
        super(worldIn);
    }

    @Override
    protected void entityInit() {
        this.age = 0;
    }

    public void setPartner(EntityGap partner) {
        this.partner = partner;
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {

    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {

    }

    @Override
    public void onEntityUpdate() {
        this.age ++;
        if (this.age > 200) {
            this.setDead();
            this.partner = null;
        }
    }

    @Override
    public void onCollideWithPlayer(EntityPlayer entityIn)
    {
        if (this.partner != null && !this.partner.isDead) {
            double posX = partner.posX;
            double posY = partner.posY;
            double posZ = partner.posZ;
            this.setDead();
            this.partner.setDead();
            entityIn.setLocationAndAngles(posX, posY, posZ, partner.rotationYaw, partner.rotationPitch);
            this.partner = null;
        }
    }

}
