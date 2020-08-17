package moe.gensoukyo.mcgproject.common.item;

import moe.gensoukyo.mcgproject.common.creativetab.MCGTabs;
import moe.gensoukyo.mcgproject.core.MCGProject;
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
import org.jetbrains.annotations.NotNull;

public class ItemMCGDrink extends ItemFood {

    public ItemMCGDrink() {
        super(5, 0.6F, false);
        this.setAlwaysEdible();
        this.setCreativeTab(MCGTabs.NATURE);
        this.setRegistryName(MCGProject.ID, "mcg_drink");
        this.setTranslationKey(MCGProject.ID + "." + "mcg_drink");
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setNoRepair();
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        playerIn.setActiveHand(handIn);
        return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.DRINK;
    }

    @Override
    public @NotNull String getTranslationKey(ItemStack stack) {
        return "item." + MCGProject.ID + "." + "mcg_drink" +"_" + stack.getMetadata();
    }

    public static final int MAX_SUB_ITEM = 18;
    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (this.isInCreativeTab(tab)) {
            for (int i = 0; i < 13; i++) {
                items.add(new ItemStack(this, 1, i));
            }
        } else if (tab == MCGTabs.PROP) {
            for (int i = 13; i <= MAX_SUB_ITEM; i++) {
                items.add(new ItemStack(this, 1, i));
            }
        }
    }
}
