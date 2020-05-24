package moe.gensoukyo.mcgproject.common.item.cart;

import moe.gensoukyo.mcgproject.common.entity.cart.GRBogie;
import moe.gensoukyo.mcgproject.common.entity.cart.GRM3B;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.thewdj.linkage.core.LinkageManager;

public class ItemGRM3B extends AbsItemMetro {

    final int length = 13, coupler = 4;

    public ItemGRM3B() {
        super(GRM3B.class, MCGProject.ID + "." + "grm_3b", "grm_3b");
    }

    public void doSpawn(World world, double x, double y, double z, EntityPlayer player, EnumFacing facing) {
        BlockPos pos = new BlockPos(0, 0, 0);
        GRBogie bogieA = new GRBogie(world, x, y, z, length / 2.0F, coupler / 2.0F);
        pos = pos.offset(facing, length / 2);
        double v = (length / 2.0F) - (float) (length / 2);
        Vec3d vec = new Vec3d(facing.getDirectionVec()).scale(v);
        GRM3B carA = new GRM3B(world, x + (double)pos.getX() + vec.x, y + 1.0D + vec.y, z + (double)pos.getZ() + vec.z);

        pos = pos.offset(facing, length / 2 + (int) (v * 2));
        GRBogie bogieB = new GRBogie(world, x + (double)pos.getX(), y, z + (double)pos.getZ(), length / 2.0F, coupler / 2.0F);
        bogieB.setIsCenter(true);

        pos = pos.offset(facing, length / 2);
        v = (length / 2.0F) - (float) (length / 2);
        vec = new Vec3d(facing.getDirectionVec()).scale(v);
        GRM3B carB = new GRM3B(world, x + (double)pos.getX() + vec.x, y + 1.0D + vec.y, z + (double)pos.getZ() + vec.z);
        pos = pos.offset(facing, length / 2 + (int) (v * 2));
        GRBogie bogieC = new GRBogie(world, x + (double)pos.getX(), y, z + (double)pos.getZ(), length / 2.0F, coupler / 2.0F);

        GRBogie.linkBogie(bogieA, bogieB); GRBogie.linkBogie(bogieB, bogieC);
        LinkageManager.INSTANCE.createLink(bogieA, bogieB); LinkageManager.INSTANCE.createLink(bogieB, bogieC);
        carA.setBogieA(bogieA).setBogieB(bogieB); carB.setBogieB(bogieB).setBogieA(bogieC);

        world.spawnEntity(bogieA); world.spawnEntity(bogieB); world.spawnEntity(bogieC);
        world.spawnEntity(carA); world.spawnEntity(carB);
    }

}
