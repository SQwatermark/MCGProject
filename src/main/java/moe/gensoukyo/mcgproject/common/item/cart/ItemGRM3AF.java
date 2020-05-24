package moe.gensoukyo.mcgproject.common.item.cart;

import moe.gensoukyo.mcgproject.common.entity.cart.GRBogie;
import moe.gensoukyo.mcgproject.common.entity.cart.GRM3A;
import moe.gensoukyo.mcgproject.common.entity.cart.GRMotor;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ItemGRM3AF extends AbsItemMetro {

    final int length = 11, coupler = 4;

    public ItemGRM3AF() {
        super(GRM3A.class, MCGProject.ID + "." + "grm_3af", "grm_3af");
    }

    public void doSpawn(World world, double x, double y, double z, EntityPlayer player, EnumFacing facing) {
        BlockPos pos = new BlockPos(0, 0, 0);
        GRMotor bogieA = new GRMotor(world, x, y, z, length / 2.0F, coupler / 2.0F);
        pos = pos.offset(facing, length / 2);
        double v = (length / 2.0F) - (float) (length / 2);
        Vec3d vec = new Vec3d(facing.getDirectionVec()).scale(v);
        GRM3A car = new GRM3A(world, x + (double)pos.getX() + vec.x, y + 1.0D + vec.y, z + (double)pos.getZ() + vec.z);
        pos = pos.offset(facing, length / 2 + (int) (v * 2));
        GRBogie bogieB = new GRBogie(world, x + (double)pos.getX(), y, z + (double)pos.getZ(), length / 2.0F, coupler / 2.0F);
        Bogie.link(bogieA, bogieB);
        car.setBogieA(bogieA).setBogieB(bogieB);
        world.spawnEntity(bogieA);
        world.spawnEntity(bogieB);
        world.spawnEntity(car);
    }

}
