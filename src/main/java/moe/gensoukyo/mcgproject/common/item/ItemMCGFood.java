package moe.gensoukyo.mcgproject.common.item;

import moe.gensoukyo.mcgproject.cilent.util.AutoTooltipHandler;
import moe.gensoukyo.mcgproject.common.creativetab.MCGTabs;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;

public class ItemMCGFood extends ItemFood {

    public ItemMCGFood() {
        super(5, 0.6F, false);
        this.setAlwaysEdible();
        this.setCreativeTab(MCGTabs.NATURE);
        this.setRegistryName(MCGProject.ID, "mcg_food");
        this.setTranslationKey(MCGProject.ID + "." + "mcg_food");
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setNoRepair();
    }

    @NotNull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        playerIn.setActiveHand(handIn);
        return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }

    @Override
    public @NotNull EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.EAT;
    }


    @Override
    public @NotNull String getTranslationKey(ItemStack stack) {
        return "item." + MCGProject.ID + "." + "mcg_food" +"_" + stack.getMetadata();
    }

    public static final int META_OF_BANANA = 75;
    @Override
    public int getHealAmount(ItemStack stack) {
        if (stack.getMetadata() == META_OF_BANANA) return 1;
        return super.getHealAmount(stack);
    }

    public static final int MAX_META = 120;
    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (this.isInCreativeTab(tab)) {
            for (int i = 0; i <= MAX_META; i++) {
                items.add(new ItemStack(this, 1, i));
            }
        }
    }


    /**
     * 自动tooltip
     * item.mcgproject.mcg_food_<meta>.tooltip.行数
     */
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(@Nonnull ItemStack stack,
                               @Nonnull @Nullable World worldIn,
                               @Nonnull List<String> tooltip,
                               @Nonnull ITooltipFlag flagIn) {
        AutoTooltipHandler.addTooltip(stack, tooltip);
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }
}
