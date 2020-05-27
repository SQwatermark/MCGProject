package moe.gensoukyo.mcgproject.common.entity.cart;

import club.nsdn.nyasamarailway.api.cart.CartPart;
import club.nsdn.nyasamarailway.ext.AbsMetro;
import club.nsdn.nyasamarailway.api.cart.CartUtil;

import moe.gensoukyo.mcgproject.common.entity.MCGEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.List;

@MCGEntity("grm_3a")
public class GRM3A extends AbsMetro {

    public static final float DOOR_DIST = 5.0F;

    public final LinkedList<CartPart> parts = new LinkedList<>();

    private static final DataParameter<Byte> COLOR_DAMAGE = EntityDataManager.createKey(GRM3A.class, DataSerializers.BYTE);
    private static final DataParameter<Boolean> DOOR_LEFT = EntityDataManager.createKey(GRM3A.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> DOOR_RIGHT = EntityDataManager.createKey(GRM3A.class, DataSerializers.BOOLEAN);

    @Override
    public boolean hasMultiPart() { return true; }

    @Override
    public List<CartPart> getMultiPart() { return parts; }

    public GRM3A(World world) {
        this(world, 0, 0, 0);
    }

    public GRM3A(World world, double x, double y, double z) {
        super(world, x, y, z);
        this.setSize(1.5F, 1.75F);

        parts.add(new CartPart<>(this, "front", new Vec3d(DOOR_DIST, 0, 0)));
        parts.add(new CartPart<>(this, "back", new Vec3d(-DOOR_DIST, 0, 0)));
    }

    @Nonnull
    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return super.getRenderBoundingBox().expand(7, 1, 7).expand(-7, 0, -7);
    }

    @Override
    public void initEntity() {
        dataManager.register(COLOR_DAMAGE, (byte) 0xF);
        dataManager.register(DOOR_LEFT, false);
        dataManager.register(DOOR_RIGHT, false);
    }

    @Override
    public void fromNBT(@Nonnull NBTTagCompound nbtTagCompound) {
        dataManager.set(COLOR_DAMAGE, nbtTagCompound.getByte("colorDamage"));
    }

    @Override
    public void toNBT(@Nonnull NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setByte("colorDamage", dataManager.get(COLOR_DAMAGE));
    }

    public boolean getDoorStateLeft() { return dataManager.get(DOOR_LEFT); }
    public boolean getDoorStateRight() { return dataManager.get(DOOR_RIGHT); }
    public void setDoorStateLeft(boolean value) { dataManager.set(DOOR_LEFT, value); }
    public void setDoorStateRight(boolean value) { dataManager.set(DOOR_RIGHT, value); }

    @Override
    public boolean processInitialInteract(@Nonnull EntityPlayer player, @Nonnull EnumHand hand) {
        if (player.isSneaking()) {
            ItemStack stack = player.getHeldItem(hand);
            Item item = stack.getItem();
            if (item.equals(Items.DYE)) {
                if (!world.isRemote) {
                    byte damage = (byte) (stack.getItemDamage() & 0xF);
                    dataManager.set(COLOR_DAMAGE, damage);
                    player.sendMessage(new TextComponentString(
                            "COLOR: " + damage + ", " + EnumDyeColor.byDyeDamage(damage).getName()));
                }
                return true;
            }
        }

        return super.processInitialInteract(player, hand);
    }

    public byte getColorDamage() { return dataManager.get(COLOR_DAMAGE); }

    @Override
    public double passengerYOffset() { return 0.1; }

    @Override
    public void passengerUpdate(@Nonnull Entity entity) {
        double x = this.posX;
        double z = this.posZ;
        double y = this.posY + this.getMountedYOffset() + entity.getYOffset();
        if (this.isPassenger(entity)) {
            int index = this.getPassengers().indexOf(entity);
            double vx = getMaxPassengerSize() / 2.0 - (index + 1) + getSeatOffset();
            vx = vx * getSeatDist();
            Vec3d vec = new Vec3d(vx, 0.0D, 0.0D);
            vec = CartUtil.rotatePitchFix(vec, (float)((double)((this.rotationPitch + 360.0F) / 180.0F) * Math.PI));
            vec = vec.rotateYaw((float)((double)((180.0F - this.rotationYaw) / 180.0F) * Math.PI));
            entity.setPosition(x + vec.x, y + vec.y, z + vec.z);

            float delta = rotationYaw - prevRotationYaw;
            entity.rotationYaw += delta;
            entity.setRotationYawHead(entity.getRotationYawHead() + delta);
            this.applyYawToEntity(entity);
        }
    }

    public void applyYawToEntity(Entity entity) {
        int index = this.getPassengers().indexOf(entity);
        float yaw = this.rotationYaw - 90;
        if ((index % 2) != 0)
            yaw = (yaw + 180) % 360;
        entity.setRenderYawOffset(yaw);
        float f = MathHelper.wrapDegrees(entity.rotationYaw - yaw);
        float f1 = MathHelper.clamp(f, -105.0F, 105.0F);
        entity.prevRotationYaw += f1 - f;
        entity.rotationYaw += f1 - f;
        entity.setRotationYawHead(entity.rotationYaw);
    }

    @SideOnly(Side.CLIENT)
    public void applyOrientationToEntity(@Nonnull Entity entity) {
        this.applyYawToEntity(entity);
    }

    @Override
    public boolean shouldSit() { return true; }

    @Override
    public int getMaxPassengerSize() { return 8; }

    public double getSeatDist() { return 1.75; }

    public double getSeatOffset() { return 0.5; }

    @Override
    public void update() {
        super.update();

        for (CartPart p : parts) {
            if (!world.loadedEntityList.contains(p))
                world.spawnEntity(p);
        }

        for (CartPart p : parts)
            p.onUpdate();
    }

}
