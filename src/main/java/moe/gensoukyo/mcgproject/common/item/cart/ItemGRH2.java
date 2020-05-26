package moe.gensoukyo.mcgproject.common.item.cart;

import moe.gensoukyo.mcgproject.common.entity.cart.GRH2;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.world.World;

public class ItemGRH2 extends AbsItemCart {

    public ItemGRH2() {
        super(GRH2.class, MCGProject.ID + "." + "grh_2", "grh_2");
    }

    @Override
    public EntityMinecart getCart(World world, double x, double y, double z) {
        return new GRH2(world, x, y, z);
    }

}
