package moe.gensoukyo.mcgproject.common.item.cart;

import moe.gensoukyo.mcgproject.common.entity.cart.GRC2;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.world.World;

public class ItemGRC2 extends AbsItemCart {

    public ItemGRC2() {
        super(GRC2.class, MCGProject.ID + "." + "grc_2", "grc_2");
    }

    @Override
    public EntityMinecart getCart(World world, double x, double y, double z) {
        return new GRC2(world, x, y, z);
    }

}
