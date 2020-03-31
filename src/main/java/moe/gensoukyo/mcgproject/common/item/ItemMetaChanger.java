package moe.gensoukyo.mcgproject.common.item;

import moe.gensoukyo.mcgproject.common.creativetab.MCGTabs;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;

public class ItemMetaChanger extends Item {

    public ItemMetaChanger() {
        this.setMaxStackSize(1);
        this.setCreativeTab(MCGTabs.FANTASY);
        this.setRegistryName(MCGProject.ID, "meta_changer");
        this.setTranslationKey(MCGProject.ID + "." + "meta_changer");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> list, ITooltipFlag flag)
    {
        list.add("尝试切换方块的所有方块状态");
    }

    /**
     * 按顺序切换方块的所有（允许的）状态
     */
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        IBlockState iblockstate = worldIn.getBlockState(pos);

        Collection<IProperty<?>> propertyKeys = iblockstate.getPropertyKeys();
        if(propertyKeys.size() == 0) return EnumActionResult.PASS;
        else {
            //将propertyKeys中的属性装进数组
            IProperty<?>[] properties = new IProperty<?>[propertyKeys.size()];
            int j = 0;
            for(IProperty<?> property : propertyKeys) {
                properties[j]=property;
                j++;
            }
            //执行改变方块状态的操作
            iblockstate = iblockstate.cycleProperty(properties[0]);
            for (int i = 0; i < propertyKeys.size()-1; i++) {
                if(iblockstate.getValue(properties[i]) == properties[i].getAllowedValues().toArray()[0]) {
                    iblockstate = iblockstate.cycleProperty(properties[i+1]);
                }
            }
            worldIn.setBlockState(pos, iblockstate, 11);
            return EnumActionResult.SUCCESS;
        }
    }
}