package moe.gensoukyo.mcgproject.common.item;

import moe.gensoukyo.mcgproject.common.creativetab.MCGTabs;
import moe.gensoukyo.mcgproject.common.entity.EntityKaginawa;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class ItemKaginawa extends Item {

    public ItemKaginawa() {
        this.setMaxDamage(0);
        this.setMaxStackSize(1);
        this.setCreativeTab(MCGTabs.FANTASY);
        this.setRegistryName(MCGProject.ID, "kaginawa");
        this.setTranslationKey(MCGProject.ID + "." + "kaginawa");
    }

    @NotNull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, @NotNull EnumHand handIn) {
        EntityKaginawa et = new EntityKaginawa(worldIn, playerIn);
        et.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);
        if (MCGProject.proxy.kagimap.containsKey(playerIn)) {
            if (MCGProject.proxy.kagimap.get(playerIn).isDead) {
                MCGProject.proxy.kagimap.put(playerIn, et);
                if (!worldIn.isRemote) {
                    worldIn.spawnEntity(et);
                } else {
                    worldIn.joinEntityInSurroundings(et);
                }
            }
        } else {
            MCGProject.proxy.kagimap.put(playerIn, et);
            if (!worldIn.isRemote) {
                worldIn.spawnEntity(et);
            } else {
                worldIn.joinEntityInSurroundings(et);
            }
        }

        return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }

}
