package moe.gensoukyo.mcgproject.common.item;

import moe.gensoukyo.mcgproject.common.creativetab.MCGTabs;
import moe.gensoukyo.mcgproject.common.entity.EntityKaginawa;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author SQwatermark
 * @date 2020/3/27
 * 普通的钩绳
 *   存在时间 50 tick
 *   速度为 1.8F
 *   不精准度为 1.0F
 *   重力 0.03 F
 *   不会对玩家产生向下的速度
 */
public class ItemKaginawa extends Item {

    final float defaultSpeed = 1.8F;
    final int defaultAge = 50;
    final float defaultInaccuracy = 1.0F;
    final int defaultOnceDamage = 16;

    NBTTagCompound nbtTagCompound;

    public ItemKaginawa() {
        this.setMaxStackSize(1);
        this.setCreativeTab(MCGTabs.FANTASY);
        this.setRegistryName(MCGProject.ID, "kaginawa");
        this.setTranslationKey(MCGProject.ID + "." + "kaginawa");
        this.setMaxDamage(1024);
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

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> list, ITooltipFlag flag) {
        list.add("右键释放钩绳");
        list.add("钩子存在时间为50tick");
        list.add(TextFormatting.DARK_RED + "当心落地");
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

        //初始化钩子参数
        float speed = defaultSpeed;
        int age = defaultAge;
        float inaccuracy = defaultInaccuracy;
        int onceDamage = defaultOnceDamage;
        ItemStack itemStack = playerIn.getHeldItem(handIn);
        if (!itemStack.hasTagCompound()) {
            nbtTagCompound = new NBTTagCompound();
            nbtTagCompound.setFloat("speed", defaultSpeed);
            nbtTagCompound.setInteger("age", defaultAge);
            nbtTagCompound.setFloat("inaccuracy", defaultInaccuracy);
            nbtTagCompound.setInteger("onceDamage", defaultOnceDamage);
            itemStack.setTagCompound(nbtTagCompound);
        } else {
            nbtTagCompound = itemStack.getTagCompound();
            assert nbtTagCompound != null;
            if (nbtTagCompound.hasKey("speed")) {
                speed = nbtTagCompound.getFloat("speed");
            }
            if (nbtTagCompound.hasKey("age")) {
                age = nbtTagCompound.getInteger("age");
            }
            if (nbtTagCompound.hasKey("inaccuracy")) {
                inaccuracy = nbtTagCompound.getFloat("inaccuracy");
            }
            if (nbtTagCompound.hasKey("onceDamage")) {
                onceDamage = nbtTagCompound.getInteger("onceDamage");
            }
        }

        //挥手动作
        playerIn.swingArm(handIn);
        //播放音效
        worldIn.playSound(null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_SNOWBALL_THROW,
                SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
        EntityKaginawa hook = new EntityKaginawa(worldIn, playerIn, age);
        hook.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, speed, inaccuracy);

        if (!worldIn.isRemote) {
            MCGProject.proxy.kagimap.put(playerIn, hook);
            worldIn.spawnEntity(hook);
            //钩绳损坏值++
            itemStack.damageItem(onceDamage, playerIn);
        } else {
            worldIn.joinEntityInSurroundings(hook);
        }

    }

    public int getItemEnchantability()
    {
        return 1;
    }

}
