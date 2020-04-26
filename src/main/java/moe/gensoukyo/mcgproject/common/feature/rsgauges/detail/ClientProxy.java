/*
 * @file ClientProxy.java
 * @author Stefan Wilhelm (wile)
 * @copyright (C) 2018 Stefan Wilhelm
 * @license MIT (see https://opensource.org/licenses/MIT)
 *
 * Client side only initialisation.
 */
package moe.gensoukyo.mcgproject.common.feature.rsgauges.detail;

import moe.gensoukyo.mcgproject.common.feature.rsgauges.ModRsGauges;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy implements ModRsGauges.IProxy
{
  public void preInit(FMLPreInitializationEvent e)
  {
    OBJLoader.INSTANCE.addDomain(ModRsGauges.RESID);
  }

  public void init(FMLInitializationEvent e)
  {}

  public void postInit(FMLPostInitializationEvent e)
  {
    OverlayEventHandler.register();
  }
}
