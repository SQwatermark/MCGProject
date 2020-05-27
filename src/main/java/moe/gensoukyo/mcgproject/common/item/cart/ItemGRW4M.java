package moe.gensoukyo.mcgproject.common.item.cart;

import moe.gensoukyo.mcgproject.common.entity.cart.GRW4M;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class ItemGRW4M extends AbsItemCart {

    public ItemGRW4M() {
        super(GRW4M.class, MCGProject.ID + "." + "grw_4m", "grw_4m");
    }

    @Override
    public boolean shouldSelfSpawn() {
        return true;
    }

    @Override
    public void selfSpawn(World world, double x, double y, double z, String name, EntityPlayer player) {
        GRW4M.spawn(world, x, y, z, null);
    }

    @Override
    public EntityMinecart getCart(World world, double x, double y, double z) {
        return null;
    }

}
