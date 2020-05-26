package moe.gensoukyo.mcgproject.common.item.cart;

import moe.gensoukyo.mcgproject.common.entity.cart.GRH2M;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.world.World;

public class ItemGRH2M extends AbsItemCart {

    public ItemGRH2M() {
        super(GRH2M.class, MCGProject.ID + "." + "grh_2m", "grh_2m");
    }

    @Override
    public EntityMinecart getCart(World world, double x, double y, double z) {
        return new GRH2M(world, x, y, z);
    }

}
