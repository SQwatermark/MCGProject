package moe.gensoukyo.mcgproject.common.item;

import moe.gensoukyo.mcgproject.common.creativetab.MCGTabs;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemMCGHoe extends Item {

    public ItemMCGHoe() {
        this.setMaxStackSize(1);
        this.setCreativeTab(MCGTabs.PROP);
        this.setMaxDamage(16);
        this.setRegistryName(MCGProject.ID, "mcg_hoe");
        this.setTranslationKey(MCGProject.ID + "." + "mcg_hoe");
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        stack.damageItem(1, attacker);
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isFull3D()
    {
        return true;
    }

}
