package moe.gensoukyo.mcgproject.common.feature.musicplayer;

import moe.gensoukyo.mcgproject.common.creativetab.MCGTabs;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.chunk.storage.AnvilChunkLoader;
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

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> list, ITooltipFlag flag) {
        list.add(TextFormatting.LIGHT_PURPLE + "矿车？");
    }

    @NotNull
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote){
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setString("id", "mcgproject:music_player");
            AnvilChunkLoader.readWorldEntityPos(nbttagcompound, worldIn, pos.getX(), pos.getY(), pos.getZ(), true);
            if(!player.isCreative()) {
                ItemStack itemstack = player.getHeldItem(hand);
                itemstack.shrink(1);
                if (itemstack.isEmpty()) {
                    player.inventory.deleteStack(itemstack);
                }
            }

        }
        return EnumActionResult.SUCCESS;
    }

}
