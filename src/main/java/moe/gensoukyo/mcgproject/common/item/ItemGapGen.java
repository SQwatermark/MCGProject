package moe.gensoukyo.mcgproject.common.item;

import moe.gensoukyo.mcgproject.common.creativetab.MCGTabs;
import moe.gensoukyo.mcgproject.common.entity.EntityGap;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class ItemGapGen extends Item {

    public ItemGapGen() {
        this.setMaxStackSize(1);
        this.setCreativeTab(MCGTabs.FANTASY);
        this.setRegistryName(MCGProject.ID, "gap_gen");
        this.setTranslationKey(MCGProject.ID + "." + "gap_gen");
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        Random random = new Random();
        EntityGap entity1 = new EntityGap(worldIn);
        EntityGap entity2 = new EntityGap(worldIn);
        entity1.setPartner(entity2);
        entity2.setPartner(entity1);
        entity1.setLocationAndAngles(pos.getX(), pos.getY() + 1, pos.getZ(), player.cameraYaw, player.cameraPitch);
        entity2.setLocationAndAngles(pos.getX() + random.nextInt(40) - 20, pos.getY() + random.nextInt(4), pos.getZ() + random.nextInt(40) - 20, player.cameraYaw, player.cameraPitch);
        if (!worldIn.isRemote) {
            worldIn.spawnEntity(entity1);
            worldIn.spawnEntity(entity2);
        } else {
            worldIn.joinEntityInSurroundings(entity1);
            worldIn.joinEntityInSurroundings(entity2);
        }
        return EnumActionResult.SUCCESS;
    }

}
