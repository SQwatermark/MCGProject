package moe.gensoukyo.mcgproject.common.init;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModTileEntity {

    private static ModTileEntity instance;
    public static ModTileEntity instance()
    {
        if(instance == null) instance = new ModTileEntity();
        return instance;
    }

    private ModTileEntity() {
    }

    @SubscribeEvent
    public void register(RegistryEvent.Register<Block> event)
    {
    }

    @SubscribeEvent
    public void registerItemBlocks(RegistryEvent.Register<Item> event)
    {
    }

}
