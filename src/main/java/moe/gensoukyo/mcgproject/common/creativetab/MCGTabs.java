package moe.gensoukyo.mcgproject.common.creativetab;

import moe.gensoukyo.mcgproject.common.init.ModBlock;
import moe.gensoukyo.mcgproject.common.init.ModItem;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class MCGTabs {

    public static final CreativeTabs TOUHOU = new CreativeTabs(getLabel("touhou"))
    {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModBlock.NAMAKO);
        }
    };

    public static final CreativeTabs NORMAL = new CreativeTabs(getLabel("normal"))
    {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModBlock.WOOL_GLOWING);
        }
    };

    public static final CreativeTabs EUROPEAN = new CreativeTabs(getLabel("european"))
    {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModBlock.BLOCK_CHIREIDEN);
        }
    };

    public static final CreativeTabs FANTASY = new CreativeTabs(getLabel("fantasy"))
    {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModBlock.GAP);
        }
    };

    public static final CreativeTabs NATURE = new CreativeTabs(getLabel("nature"))
    {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModBlock.FLOWER);
        }
    };

    public static final CreativeTabs OLD = new CreativeTabs(getLabel("old"))
    {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(Items.APPLE);
        }
    };

    public static final CreativeTabs PROP = new CreativeTabs(getLabel("prop"))
    {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModItem.ITEM_MCG_PROP, 1, 34);
        }
    };

    public static String getLabel(String name) {
        return MCGProject.ID + "." + name;
    }

}
