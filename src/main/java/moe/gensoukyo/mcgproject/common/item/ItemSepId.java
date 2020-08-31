package moe.gensoukyo.mcgproject.common.item;

import moe.gensoukyo.mcgproject.common.creativetab.MCGTabs;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

/**
 * 独立id的道具
 * @author Chloe_koopa
 */
public class ItemSepId extends Item {
    public ItemSepId(ResourceLocation id) {
        setRegistryName(id);
        setTranslationKey(id.toString().replace(":", "."));
        setCreativeTab(MCGTabs.PROP);
    }

    public ItemSepId(String namespace, String path) {
        this(new ResourceLocation(namespace, path));
    }

    public ItemSepId(String path) {
        this(MCGProject.ID, path);
    }
}
