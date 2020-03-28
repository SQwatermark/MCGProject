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

/**
 * @author SQwatermark
 * @date 2020/3/27
 * 普通的钩绳
 *   存在时间 50 tick
 *   速度为 2.0F
 *   不精准度为 1.0F
 *   重力 0.03 F
 *   不会对玩家产生向下的速度
 */
public class ItemKaginawa extends Item {

    float speed = 2.0F;

    public ItemKaginawa() {
        this.setMaxDamage(0);
        this.setMaxStackSize(1);
        this.setCreativeTab(MCGTabs.FANTASY);
        this.setRegistryName(MCGProject.ID, "kaginawa");
        this.setTranslationKey(MCGProject.ID + "." + "kaginawa");
        this.addPropertyOverride(new ResourceLocation("cast"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(@NotNull ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
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
                et.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, this.speed, 1.0F);
            }
        } else {
            EntityKaginawa et = new EntityKaginawa(worldIn, playerIn);
            MCGProject.proxy.kagimap.put(playerIn, et);
            if (!worldIn.isRemote) {
                worldIn.spawnEntity(et);
            } else {
                worldIn.joinEntityInSurroundings(et);
            }
            et.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, this.speed, 1.0F);
        }

        return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }


}
