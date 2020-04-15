package moe.gensoukyo.mcgproject.common.item;

import moe.gensoukyo.mcgproject.common.creativetab.MCGTabs;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemMCGFood extends ItemFood {

    public ItemMCGFood() {
        super(5, 0.6F, false);
        this.setAlwaysEdible();
        this.setCreativeTab(MCGTabs.NATURE);
        this.setRegistryName(MCGProject.ID, "mcg_food");
        this.setTranslationKey(MCGProject.ID + "." + "mcg_food");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        playerIn.setActiveHand(handIn);
        return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }

}
