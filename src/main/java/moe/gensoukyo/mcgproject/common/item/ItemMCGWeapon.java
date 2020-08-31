package moe.gensoukyo.mcgproject.common.item;

import moe.gensoukyo.mcgproject.cilent.util.AutoTooltipHandler;
import moe.gensoukyo.mcgproject.common.creativetab.MCGTabs;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author SQWatermark
 * @author Chloe_koopa
 */
public class ItemMCGWeapon extends ItemSword {
    private static final List<ItemSword> INSTANCES = new ArrayList<>(32);
    /**
     * mcgproject:mcg_weapons
     */
    public static final String UNIFIED_NAME = "mcg_weapon";

    public static List<ItemSword> getInstances() {
        return INSTANCES;
    }

    public static List<ItemMCGWeapon> create(int count) {
        List<ItemMCGWeapon> result = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            result.add(new ItemMCGWeapon());
        }
        return result;
    }

    private static int curType = 0;
    private final int type;

    protected ItemMCGWeapon() {
        super(ToolMaterial.IRON);
        INSTANCES.add(this);
        this.type = curType;
        ++curType;

        this.setCreativeTab(MCGTabs.WEAPONS);
        this.setRegistryName(MCGProject.ID, UNIFIED_NAME + "_" + this.type);
    }

    @Override
    public float getAttackDamage() {
        return 0;
    }

    @Override
    public boolean hitEntity(@Nonnull ItemStack stack,
                             @Nonnull EntityLivingBase target,
                             @Nonnull EntityLivingBase attacker) {
        return true;
    }

    @Override
    public @NotNull String getTranslationKey(@Nonnull ItemStack stack) {
        return "item." + MCGProject.ID + "." + UNIFIED_NAME + "_" + this.type;
    }

    /**
     * 自动tooltip
     * item.mcgproject.mcg_prop_<meta>.tooltip.行数
     */
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(@Nonnull ItemStack stack,
                                @Nonnull @Nullable World worldIn,
                                @Nonnull List<String> tooltip,
                                @Nonnull ITooltipFlag flagIn) {
        AutoTooltipHandler.addTooltip(stack, tooltip);
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }
}
