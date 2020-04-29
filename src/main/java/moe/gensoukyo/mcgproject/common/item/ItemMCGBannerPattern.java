package moe.gensoukyo.mcgproject.common.item;

import moe.gensoukyo.mcgproject.common.creativetab.MCGTabs;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import org.jetbrains.annotations.NotNull;

public class ItemMCGBannerPattern extends Item {

    public ItemMCGBannerPattern() {
        super();
        this.setCreativeTab(MCGTabs.PROP);
        this.setRegistryName(MCGProject.ID, "mcg_banner_pattern");
        this.setTranslationKey(MCGProject.ID + "." + "mcg_banner_pattern");
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setNoRepair();
    }

    @Override
    public @NotNull String getTranslationKey(ItemStack stack) {
        return "item." + MCGProject.ID + "." + "mcg_banner_pattern" +"_" + stack.getMetadata();
    }


    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        int maxMeta = 16;
        if (this.isInCreativeTab(tab)) {
            for (int i = 0; i < maxMeta + 1; i++) {
                items.add(new ItemStack(this, 1, i));
            }
        }
    }
}
