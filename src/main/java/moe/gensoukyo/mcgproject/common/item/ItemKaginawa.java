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
        this.setMaxStackSize(1);
        this.setCreativeTab(MCGTabs.FANTASY);
        this.setRegistryName(MCGProject.ID, "kaginawa");
        this.setTranslationKey(MCGProject.ID + "." + "kaginawa");
        this.setMaxDamage(256);
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

    /**
     * 持有物品右键时执行的操作，判断是否应该抛出钩绳
     * 玩家和钩子实体的对应关系用 WeakHashMap 记录，如果玩家正在抛出钩绳，右键就不会有效果
     */
    @NotNull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, @NotNull EnumHand handIn) {

        if (MCGProject.proxy.kagimap.containsKey(playerIn) && !MCGProject.proxy.kagimap.get(playerIn).isDead) {
            return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
        } else {
            act(worldIn, playerIn, handIn);
            return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
        }
    }

    public void act(World worldIn, EntityPlayer playerIn, @NotNull EnumHand handIn) {
        ItemStack itemStack = playerIn.getHeldItem(handIn);
        //挥手
        playerIn.swingArm(handIn);
        //抛出钩子
        EntityKaginawa hook = new EntityKaginawa(worldIn, playerIn);
        MCGProject.proxy.kagimap.put(playerIn, hook);
        if (!worldIn.isRemote) {
            worldIn.spawnEntity(hook);
        } else {
            worldIn.joinEntityInSurroundings(hook);
        }
        hook.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, this.speed, 1.0F);
        //钩绳损坏值++
        itemStack.damageItem(3, playerIn);

    }

    public int getItemEnchantability()
    {
        return 1;
    }

}
