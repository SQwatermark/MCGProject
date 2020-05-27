package moe.gensoukyo.mcgproject.common.item.cart;

import moe.gensoukyo.mcgproject.common.entity.cart.GRC2M;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.world.World;

public class ItemGRC2M extends AbsItemCart {

    public ItemGRC2M() {
        super(GRC2M.class, MCGProject.ID + "." + "grc_2m", "grc_2m");
    }

    @Override
    public EntityMinecart getCart(World world, double x, double y, double z) {
        return new GRC2M(world, x, y, z);
    }

}
