package moe.gensoukyo.mcgproject.common.feature.farm.stone;

import moe.gensoukyo.mcgproject.common.creativetab.MCGTabs;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

import java.util.Objects;

public class ItemLittleRock extends Item {

    public ItemLittleRock() {
        //this.setMaxStackSize(16);
        this.setCreativeTab(MCGTabs.PROP);
        this.setRegistryName(MCGProject.ID, "little_rock");
        this.setTranslationKey(MCGProject.ID + "." + "little_rock");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        if (!playerIn.capabilities.isCreativeMode) {
            itemstack.shrink(1);
        }

        worldIn.playSound(null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_EGG_THROW, SoundCategory.PLAYERS, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        EntityLittleRock littleStone = new EntityLittleRock(worldIn, playerIn);
        littleStone.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);

        if (!worldIn.isRemote) {
            worldIn.spawnEntity(littleStone);
        } else {
            worldIn.joinEntityInSurroundings(littleStone);
        }

        playerIn.addStat(Objects.requireNonNull(StatList.getObjectUseStats(this)));
        return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
    }

}
