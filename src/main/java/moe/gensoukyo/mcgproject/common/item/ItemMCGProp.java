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
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * @author SQWatermark
 * @author Chloe_koopa
 */
public class ItemMCGProp extends Item {

    public ItemMCGProp() {
        super();
        this.setCreativeTab(MCGTabs.PROP);
        this.setRegistryName(MCGProject.ID, "mcg_prop");
        this.setTranslationKey(MCGProject.ID + "." + "mcg_prop");
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setNoRepair();
    }

    @Override
    public @NotNull String getTranslationKey(ItemStack stack) {
        return "item." + MCGProject.ID + "." + "mcg_prop" + "_" + stack.getMetadata();
    }


    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (tab == MCGTabs.NATURE) {
            for (int i = 0; i < 20; i++) {
                items.add(new ItemStack(this, 1, i));
            }
        } else if (this.isInCreativeTab(tab)) {
            for (int i = 20; i <= 95; i++) {
                items.add(new ItemStack(this, 1, i));
            }
        }
    }

    /**
     * 翻译密钥为
     * item.mcgproject.mcg_prop_<meta>.tooltip.行数
     */
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(@Nonnull ItemStack stack,
                               @Nonnull @Nullable World worldIn,
                               @Nonnull List<String> tooltip,
                               @Nonnull ITooltipFlag flagIn) {
        final String trKey = getTooltipKey(stack);
        int i = 1;
        while (true) {
            String key = trKey + i;
            if (!I18n.hasKey(key)) {
                break;
            }
            tooltip.add(I18n.format(key));
            i++;
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    private String getTooltipKey(ItemStack stack) {
        return this.getTranslationKey(stack) + ".tooltip.";
    }
}
