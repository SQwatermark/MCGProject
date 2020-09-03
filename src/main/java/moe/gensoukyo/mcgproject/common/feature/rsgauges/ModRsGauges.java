/*
 * @file ModRsGauges.java
 * @author Stefan Wilhelm (wile)
 * @copyright (C) 2018 Stefan Wilhelm
 * @license MIT (see https://opensource.org/licenses/MIT)
 *
 * Main mod class.
 */
package moe.gensoukyo.mcgproject.common.feature.rsgauges;

import moe.gensoukyo.mcgproject.common.feature.rsgauges.blocks.IRsNeighbourInteractionSensitive;
import moe.gensoukyo.mcgproject.common.feature.rsgauges.detail.*;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickBlock;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;

@SuppressWarnings({"unused", "ConstantConditions"})
public class ModRsGauges
{
  public static final String RESID = "rsgauges";
  public static final String MODID = MCGProject.ID;
  public static Logger logger = null;
  public static void attachLogger(Logger logger) {
    ModRsGauges.logger = logger;
  }

  public static ModRsGauges INSTANCE = new ModRsGauges();

  public void initNetwork(SimpleNetworkWrapper wrapper, int id) {
    Networking.preInitCommon(wrapper, id);
  }

  public static IProxy proxy = new ServerProxy();
  public static IProxy client = new ClientProxy();

  public interface IProxy
  {
    public void preInit(FMLPreInitializationEvent e);
    public void init(FMLInitializationEvent e);
    public void postInit(FMLPostInitializationEvent e);
  }

  public void preInit(FMLPreInitializationEvent event)
  {
    proxy.preInit(event);
  }

  public void attachLogger(FMLInitializationEvent event)
  {
    DataFixing.registerDataFixers();
    proxy.init(event);
  }

  public void postInit(FMLPostInitializationEvent event)
  {
    ModConfig.onPostInit(event);
    proxy.postInit(event);
    ModAuxiliaries.BlockCategories.compose();
  }

  @Mod.EventBusSubscriber(modid=ModRsGauges.MODID)
  public static final class RegistrationSubscriptions
  {
    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    { ModContent.registerBlocks(event); }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
      ModContent.registerItemBlocks(event);
      ModContent.registerItems(event);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event)
    { ModContent.initModels(); }

    @SubscribeEvent
    public static void playerInteract(PlayerInteractEvent event)
    {
      final World world = event.getWorld();
      if(world.isRemote) return;
      final boolean is_rclick = (event instanceof LeftClickBlock) && (event.getHand()==EnumHand.MAIN_HAND);
      final boolean is_lclick = (event instanceof RightClickBlock) && (event.getHand()==EnumHand.MAIN_HAND);
      if((!is_rclick) && (!is_lclick)) return;
      final BlockPos fromPos = event.getPos();
      for(EnumFacing facing: EnumFacing.values()) {
        final BlockPos pos = fromPos.offset(facing);
        final IBlockState state = event.getWorld().getBlockState(pos);
        if(!((state.getBlock()) instanceof IRsNeighbourInteractionSensitive)) continue;
        if(((IRsNeighbourInteractionSensitive)state.getBlock()).onNeighborBlockPlayerInteraction(world, pos, state, fromPos, event.getEntityLiving(), event.getHand(), is_lclick)) {
          event.setCancellationResult(EnumActionResult.SUCCESS);
        }
      }
    }
  }

  public static final CreativeTabs CREATIVE_TAB_RSGAUGES = (new CreativeTabs("tabrsgauges")
  {
    @Override
    @SideOnly(Side.CLIENT)
    public @Nonnull ItemStack createIcon()
    { return new ItemStack((ModContent.ITEMGROUP_BLOCK != null) ? (ModContent.ITEMGROUP_BLOCK) : (Blocks.LEVER)); }
  });

}
