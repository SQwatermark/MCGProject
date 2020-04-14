package moe.gensoukyo.mcgproject.common.item;

import moe.gensoukyo.mcgproject.common.creativetab.MCGTabs;
import moe.gensoukyo.mcgproject.common.util.MaterialHelper;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class ItemBlockInfo extends Item {

    public ItemBlockInfo() {
        this.setMaxStackSize(1);
        this.setCreativeTab(MCGTabs.FANTASY);
        this.setRegistryName(MCGProject.ID, "block_info_displayer");
        this.setTranslationKey(MCGProject.ID + "." + "block_info_displayer");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> list, ITooltipFlag flag) {
        list.add(I18n.format("tooltip.mcgproject.itemblockinfo"));
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        Block block = iblockstate.getBlock();
        Random random = new Random();
        int colorIndex = random.nextInt(16);
        TextFormatting color = TextFormatting.fromColorIndex(colorIndex);
        StringBuilder s = new StringBuilder();
        for (IProperty<?> iProperty : iblockstate.getProperties().keySet()) {
            String key = iProperty.getName();
            String value = iblockstate.getValue(iProperty).toString();
            s.append(key).append("=").append(value);
            s.append(",");
        }
        String s1;
         s1 = s.length() > 0 ? s.substring(0, s.length()-1) : "无";

        if (!worldIn.isRemote) {
            player.sendMessage(new TextComponentString(color + block.getLocalizedName()));
            player.sendMessage(new TextComponentString(color + "    SID: " + block.getRegistryName()));
            player.sendMessage(new TextComponentString(color + "    NID: " + Block.getIdFromBlock(block) + ":" + block.getMetaFromState(iblockstate)));
            player.sendMessage(new TextComponentString(color + "    方块状态: " + s1));
            player.sendMessage(new TextComponentString(color
                    + "    材料: " + MaterialHelper.getMaterial(iblockstate.getMaterial())
                    + "    硬度: " + iblockstate.getBlockHardness(worldIn, pos)
                    + "    亮度: " + block.getLightValue(iblockstate, worldIn, pos)
                    + "    透光度: " + block.getLightOpacity(iblockstate, worldIn, pos)
                    ));
            player.sendMessage(new TextComponentString(color
                    + "    渲染层: " + block.getRenderLayer()
                    + "    渲染类型: " + iblockstate.getRenderType()
                    + "    透明性: " + (iblockstate.isTranslucent() ? "半透明" : (iblockstate.isOpaqueCube() ? "不透明" : "透明"))
            ));
            player.sendMessage(new TextComponentString(color
                    + "    是否造成窒息: " + iblockstate.causesSuffocation()
                    + "    是否为方块实体: " + block.hasTileEntity()
            ));

        } else {
            return EnumActionResult.PASS;
        }

        return EnumActionResult.SUCCESS;
    }

}
