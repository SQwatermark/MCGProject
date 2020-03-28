package moe.gensoukyo.mcgproject.common.item;

import moe.gensoukyo.mcgproject.common.creativetab.MCGTabs;
import moe.gensoukyo.mcgproject.common.entity.EntityKaginawa;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class ItemKaginawa extends Item {

    public ItemKaginawa() {
        this.setMaxDamage(0);
        this.setMaxStackSize(1);
        this.setCreativeTab(MCGTabs.FANTASY);
        this.setRegistryName(MCGProject.ID, "kaginawa");
        this.setTranslationKey(MCGProject.ID + "." + "kaginawa");
        this.addPropertyOverride(new ResourceLocation("cast"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                if (entityIn == null)
                {
                    return 0.0F;
                }
                else
                {
                    boolean flag = entityIn.getHeldItemMainhand() == stack;
                    boolean flag1 = entityIn.getHeldItemOffhand() == stack;

                    if (entityIn.getHeldItemMainhand().getItem() instanceof ItemKaginawa)
                    {
                        flag1 = false;
                    }

                    return (flag || flag1) && MCGProject.proxy.kagimap.containsKey(entityIn) && !MCGProject.proxy.kagimap.get(entityIn).isDead ? 1.0F : 0.0F;
                }
            }
        });
    }

    @NotNull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, @NotNull EnumHand handIn) {
        playerIn.swingArm(handIn);
        if (MCGProject.proxy.kagimap.containsKey(playerIn)) {
            if (MCGProject.proxy.kagimap.get(playerIn).isDead) {
                EntityKaginawa et = new EntityKaginawa(worldIn, playerIn);
                MCGProject.proxy.kagimap.put(playerIn, et);
                if (!worldIn.isRemote) {
                    worldIn.spawnEntity(et);
                } else {
                    worldIn.joinEntityInSurroundings(et);
                }
                et.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);
            }
        } else {
            EntityKaginawa et = new EntityKaginawa(worldIn, playerIn);
            MCGProject.proxy.kagimap.put(playerIn, et);
            if (!worldIn.isRemote) {
                worldIn.spawnEntity(et);
            } else {
                worldIn.joinEntityInSurroundings(et);
            }
            et.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);
        }

        return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }


}
