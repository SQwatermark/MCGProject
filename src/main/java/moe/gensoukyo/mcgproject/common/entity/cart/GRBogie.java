package moe.gensoukyo.mcgproject.common.entity.cart;

import club.nsdn.nyasamarailway.ext.AbsBogie;

import moe.gensoukyo.mcgproject.common.entity.MCGEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import javax.annotation.Nonnull;
import java.util.UUID;

@MCGEntity("gr_bogie")
public class GRBogie extends AbsBogie {

    public static void linkBogie(GRBogie a, GRBogie b) {
        a.setTargetBogie(b);
        b.setTargetBogie(a);
    }

    private static final DataParameter<Integer> TARGET_BOGIE = EntityDataManager.createKey(GRBogie.class, DataSerializers.VARINT);
    public UUID targetUUID = UUID.randomUUID();

    private static final DataParameter<Boolean> IS_CENTER = EntityDataManager.createKey(GRBogie.class, DataSerializers.BOOLEAN);

    public float bogieDist = 1.0F;
    public float couplerDist = 1.0F;

    public GRBogie(World world) {
        super(world);
    }

    public GRBogie(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    public GRBogie(World world, double x, double y, double z, float bogieDist, float couplerDist) {
        super(world, x, y, z);
        this.bogieDist = bogieDist;
        this.couplerDist = couplerDist;
    }

    public void setIsCenter(boolean val) { dataManager.set(IS_CENTER, val); }
    public boolean isCenter() { return dataManager.get(IS_CENTER); }

    @Override
    public float getMaxCartSpeedOnRail() {
        return 3.0F;
    }

    @Override
    public float getLinkageDistance(EntityMinecart cart) {
        return 8.0F;
    }

    @Override
    public float getOptimalDistance(EntityMinecart cart) {
        return (cart == getTargetBogie() || isCenter()) ? bogieDist : couplerDist;
    }

    @Override
    public void initEntity() {
        dataManager.register(TARGET_BOGIE, -1);
        dataManager.register(IS_CENTER, false);
    }

    @Override
    public void fromNBT(@Nonnull NBTTagCompound tag) {
        dataManager.set(TARGET_BOGIE, tag.getInteger("targetBogie"));
        targetUUID = tag.getUniqueId("targetUUID");
        dataManager.set(IS_CENTER, tag.getBoolean("isCenter"));

        bogieDist = tag.getFloat("bogieDist");
        couplerDist = tag.getFloat("couplerDist");
    }

    @Override
    public void toNBT(@Nonnull NBTTagCompound tag) {
        tag.setInteger("targetBogie", dataManager.get(TARGET_BOGIE));
        tag.setUniqueId("targetUUID", targetUUID);
        tag.setBoolean("isCenter", dataManager.get(IS_CENTER));

        tag.setFloat("bogieDist", bogieDist);
        tag.setFloat("couplerDist", couplerDist);
    }

    public void setTargetBogie(Entity bogie) {
        if (isCenter()) return;
        if (bogie != null) {
            if (!world.isRemote) {
                targetUUID = bogie.getUniqueID();
            }

            dataManager.set(TARGET_BOGIE, bogie.getEntityId());
        }
    }

    public Entity getTargetBogie() {
        Entity entity = world.getEntityByID(dataManager.get(TARGET_BOGIE));
        if (!world.isRemote && world instanceof WorldServer) {
            Entity serverEntity = ((WorldServer)world).getEntityFromUuid(targetUUID);
            if (serverEntity != null) {
                if (!serverEntity.equals(entity)) {
                    this.dataManager.set(TARGET_BOGIE, serverEntity.getEntityId());
                }

                return serverEntity;
            } else {
                targetUUID = UUID.randomUUID();
                this.dataManager.set(TARGET_BOGIE, -1);
                return null;
            }
        } else {
            return entity;
        }
    }

}
