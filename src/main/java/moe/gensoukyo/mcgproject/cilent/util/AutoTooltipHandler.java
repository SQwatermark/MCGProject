package moe.gensoukyo.mcgproject.cilent.util;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * 给食物依据预定的翻译密钥自动添加tooltip
 * @author Chloe_koopa
 */
public final class AutoTooltipHandler {
    private AutoTooltipHandler() {}

    public static void addTooltip(@Nonnull ItemStack stack, @Nonnull List<String> tooltip) {
        final String trKey = getTooltipKey(stack);
        int i = 1;
        while (true) {
            String key = trKey + i;
            if (!I18n.hasKey(key)) {
                break;
            }
            tooltip.add(I18n.format(key));
            i++;
        }
    }

    private static String getTooltipKey(ItemStack stack) {
        return stack.getItem().getTranslationKey(stack) + ".tooltip.";
    }
}
