package moe.gensoukyo.mcgproject.common.creativetab;

import moe.gensoukyo.mcgproject.common.init.ModBlock;
import moe.gensoukyo.mcgproject.common.init.ModItem;
import moe.gensoukyo.mcgproject.common.item.ItemMCGWeapon;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

/**
 * @author SQWatermark
 * @author Chloe_koopa
 */
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

    public static final CreativeTabs WEAPONS = new CreativeTabs(getLabel("weapon"))
    {
        @Override
        @SideOnly(Side.CLIENT)
        public ItemStack createIcon() {
            return new ItemStack(ModItem.ITEM_MCG_WEAPONS.get(0), 1, 0);
        }

        @Override
        @SideOnly(Side.CLIENT)
        public void displayAllRelevantItems(@Nonnull NonNullList<ItemStack> items) {
            ItemMCGWeapon.getInstances().forEach(weapon -> items.add(
                    new ItemStack(weapon, 1, 0)
            ));
        }
    };

    public static String getLabel(String name) {
        return MCGProject.ID + "." + name;
    }

}
