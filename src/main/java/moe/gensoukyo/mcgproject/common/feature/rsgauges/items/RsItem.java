package moe.gensoukyo.mcgproject.common.feature.rsgauges.items;

import moe.gensoukyo.mcgproject.common.feature.rsgauges.ModRsGauges;
import moe.gensoukyo.mcgproject.common.feature.rsgauges.detail.ModAuxiliaries;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class RsItem extends Item
{

  RsItem(String registryName)
  {
    super();
    setRegistryName(ModRsGauges.RESID, registryName);
    setTranslationKey(ModRsGauges.RESID + "." + registryName);
    setMaxStackSize(64);
    setCreativeTab(ModRsGauges.CREATIVE_TAB_RSGAUGES);
    setHasSubtypes(false);
    setMaxDamage(0);
    setNoRepair();
  }

  @SideOnly(Side.CLIENT)
  public void initModel()
  {
    ModelResourceLocation rc = new ModelResourceLocation(getRegistryName(),"inventory");
    ModelBakery.registerItemVariants(this, rc);
    ModelLoader.setCustomMeshDefinition(this, stack->rc);
  }

  @Override
  @SideOnly(Side.CLIENT)
  public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
  { ModAuxiliaries.Tooltip.addInformation(stack, world, tooltip, flag, true); }

}
