package moe.gensoukyo.mcgproject.common.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ItemBlockWithMeta extends ItemMCGBlock {

    public ItemBlockWithMeta(Block block) {
        super(block);
        this.setHasSubtypes(true);
    }

    @Override
    public @NotNull String getTranslationKey(ItemStack stack) {
        return block.getTranslationKey() +"_" + stack.getMetadata();
    }

}