package moe.gensoukyo.mcgproject.common.item;

import moe.gensoukyo.mcgproject.common.creativetab.MCGTabs;
import moe.gensoukyo.mcgproject.common.entity.EntityMCGBoat;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * @author drzzm32
 * @date 2020/3/15
 */
public class ItemMCGBoat extends Item {

    public ItemMCGBoat() {
        this.setMaxStackSize(1);
        this.setCreativeTab(MCGTabs.FANTASY);
        this.setRegistryName(MCGProject.ID, "mcg_boat");
        this.setTranslationKey(MCGProject.ID + "." + "mcg_boat");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> list, ITooltipFlag flag) {
        list.add(TextFormatting.LIGHT_PURPLE + "Nice Boat");
        list.add(TextFormatting.DARK_PURPLE + "easy to drive");
    }

    @Override
    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        float pitch = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * 1.0F;
        float yaw = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * 1.0F;
        double x = player.prevPosX + (player.posX - player.prevPosX) * 1.0D;
        double y = player.prevPosY + (player.posY - player.prevPosY) * 1.0D + (double)player.getEyeHeight();
        double z = player.prevPosZ + (player.posZ - player.prevPosZ) * 1.0D;
        Vec3d vec = new Vec3d(x, y, z);
        float yc = MathHelper.cos(-yaw * 0.017453292F - 3.1415927F);
        float ys = MathHelper.sin(-yaw * 0.017453292F - 3.1415927F);
        float pc = -MathHelper.cos(-pitch * 0.017453292F);
        float ps = MathHelper.sin(-pitch * 0.017453292F);
        float rx = ys * pc;
        float rz = yc * pc;
        Vec3d ray = vec.add((double)rx * 5.0D, (double)ps * 5.0D, (double)rz * 5.0D);
        RayTraceResult result = world.rayTraceBlocks(vec, ray, true);
        if (result == null) {
            return ActionResult.newResult(EnumActionResult.PASS, stack);
        } else {
            Vec3d look = player.getLook(1.0F);
            boolean hasEntity = false;
            List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(player, player.getEntityBoundingBox().expand(look.x * 5.0D, look.y * 5.0D, look.z * 5.0D).grow(1.0D));

            for(Entity entity : list) {
                if (entity.canBeCollidedWith()) {
                    AxisAlignedBB aabb = entity.getEntityBoundingBox().grow((double)entity.getCollisionBorderSize());
                    if (aabb.contains(vec)) {
                        hasEntity = true;
                    }
                }
            }

            if (hasEntity) {
                return ActionResult.newResult(EnumActionResult.PASS, stack);
            } else if (result.typeOfHit != net.minecraft.util.math.RayTraceResult.Type.BLOCK) {
                return ActionResult.newResult(EnumActionResult.PASS, stack);
            } else {
                Block block = world.getBlockState(result.getBlockPos()).getBlock();
                boolean inWater = block == Blocks.WATER || block == Blocks.FLOWING_WATER;
                EntityMCGBoat boat = new EntityMCGBoat(world, result.hitVec.x, inWater ? result.hitVec.y - 0.12D : result.hitVec.y, result.hitVec.z);
                boat.rotationYaw = player.rotationYaw;
                if (!world.getCollisionBoxes(boat, boat.getEntityBoundingBox().grow(-0.1D)).isEmpty()) {
                    return ActionResult.newResult(EnumActionResult.FAIL, stack);
                } else {
                    if (!world.isRemote) {
                        world.spawnEntity(boat);
                    }

                    if (!player.capabilities.isCreativeMode) {
                        stack.shrink(1);
                    }

                    StatBase stat = StatList.getObjectUseStats(this);
                    if (stat != null)
                        player.addStat(stat);
                    return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
                }
            }
        }
    }
}
