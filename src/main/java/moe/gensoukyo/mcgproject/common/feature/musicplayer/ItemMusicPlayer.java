package moe.gensoukyo.mcgproject.common.feature.musicplayer;

import moe.gensoukyo.mcgproject.common.creativetab.MCGTabs;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class ItemMusicPlayer extends Item {

    public ItemMusicPlayer() {
        this.setMaxStackSize(1);
        this.setCreativeTab(MCGTabs.FANTASY);
        this.setRegistryName(MCGProject.ID, "music_player");
        this.setTranslationKey(MCGProject.ID + "." + "music_player");
    }

    // 来点特效
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack) {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> list, ITooltipFlag flag) {
        list.add(TextFormatting.LIGHT_PURPLE + "矿车？");
    }

    @NotNull
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        if (!BlockRailBase.isRailBlock(iblockstate))
            pos = pos.up();

        ItemStack stack = player.getHeldItem(hand);
        if(!worldIn.isRemote) {
            EntityMusicPlayer musicPlayer = new EntityMusicPlayer(worldIn,
                    (double)pos.getX() + 0.5D,
                    (double)pos.getY() + 0.0625D,
                    (double)pos.getZ() + 0.5D);
            musicPlayer.owner = player.getName();

            if (stack.hasDisplayName())
                musicPlayer.setCustomNameTag(stack.getDisplayName());

            worldIn.spawnEntity(musicPlayer);
        }

        if(!player.isCreative()) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.inventory.deleteStack(stack);
            }
        }

        return EnumActionResult.SUCCESS;
    }

}
