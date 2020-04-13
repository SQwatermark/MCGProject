package moe.gensoukyo.mcgproject.common.network;

import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

/**
 * @author drzzm32
 * @date 2020/3/15
 */
public class NetworkWrapper {

    public static SimpleNetworkWrapper INSTANCE;

    public NetworkWrapper(FMLPreInitializationEvent event) {
        INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(MCGProject.ID);

        INSTANCE.registerMessage(BackpackGuiHandler.class, BackpackPacket.class, 0, Side.CLIENT);
        INSTANCE.registerMessage(BoatStCHandler.class, BoatPacket.class, 1, Side.CLIENT);
        INSTANCE.registerMessage(BoatCtSHandler.class, BoatPacket.class, 2, Side.SERVER);
        //INSTANCE.registerMessage(SignHandler.class, SignPacket.class, 3, Side.SERVER);
        INSTANCE.registerMessage(StickerHandler.class, StickerPacket.class, 4, Side.SERVER);
        INSTANCE.registerMessage(StickerRefreshHandler.class, StickerRefreshPacket.class, 5, Side.CLIENT);
    }

}
