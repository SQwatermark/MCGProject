package moe.gensoukyo.mcgproject.common.item;

import moe.gensoukyo.mcgproject.common.block.futuremc.BlockBamboo;
import moe.gensoukyo.mcgproject.common.block.futuremc.BlockCampfire;
import moe.gensoukyo.mcgproject.common.block.futuremc.BlockComposter;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

/**
 * MCGProject的所有ItemBlock都继承这个类
 * 这个类将ItemBlock的注册名设置为其对应方块的注册名
 */
public class ItemMCGBlock extends ItemBlock {

    public ItemMCGBlock(Block block) {
        super(block);
        this.setRegistryName(Objects.requireNonNull(block.getRegistryName()));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> list, ITooltipFlag flag) {
        if(Block.getBlockFromItem(stack.getItem()) instanceof BlockCampfire)
            list.add(I18n.format("tooltip.minecraftfuture.campfire"));
        if(Block.getBlockFromItem(stack.getItem()) instanceof BlockComposter)
            list.add(I18n.format("tooltip.minecraftfuture.composter"));
        if(Block.getBlockFromItem(stack.getItem()) instanceof BlockBamboo)
            list.add(I18n.format("tooltip.minecraftfuture.bamboo"));
    }

}
