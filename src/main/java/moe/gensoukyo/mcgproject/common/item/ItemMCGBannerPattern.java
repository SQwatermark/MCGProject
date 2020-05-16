package moe.gensoukyo.mcgproject.common.item;

import moe.gensoukyo.mcgproject.common.creativetab.MCGTabs;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class ItemMCGBannerPattern extends Item {

    public ItemMCGBannerPattern() {
        super();
        this.setCreativeTab(MCGTabs.TOUHOU);
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
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> list, ITooltipFlag flag) {
        list.add(I18n.format("tooltip.mcgproject.itemmcgbannerpattern"));
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
