package moe.gensoukyo.mcgproject.common.item;

import moe.gensoukyo.mcgproject.common.creativetab.MCGTabs;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;

public class ItemMCGArmor extends ItemArmor implements ISpecialArmor {

    public ItemMCGArmor(String name, ArmorMaterial material, EntityEquipmentSlot slot) {

        super(material, 0, slot);
        String a;
        switch (slot){
            case HEAD: a = "helmet";break;
            case CHEST: a = "chestplate";break;
            case LEGS: a = "leggings";break;
            case FEET: a = "boots";break;
            default: a = "null";
        }
        this.setRegistryName(name + "_" + a);
        this.setCreativeTab(MCGTabs.CLOTHES);
        this.setTranslationKey(MCGProject.ID + "." + name + "_" + a);
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
        return null;
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack item) {
        // 穿在身上的时候的每时每刻都会调用的方法，可以用来追加药水效果什么的
    }

    // ISpecialArmor 接口实现开始

    @Override
    public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
        return new ArmorProperties(0, 1.0, 100);
    }

    @Override
    public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
        return 0;
    }

    @Override
    public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {

    }

    // ISpecialArmor 接口实现结束
}
