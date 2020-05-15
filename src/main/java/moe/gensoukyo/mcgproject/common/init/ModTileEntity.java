package moe.gensoukyo.mcgproject.common.init;

import moe.gensoukyo.mcgproject.common.block.BlockKitunebi;
import moe.gensoukyo.mcgproject.common.feature.lightbulb.TileLightBulb;
import moe.gensoukyo.mcgproject.common.feature.ranstone.RanstoneLamp;
import moe.gensoukyo.mcgproject.common.feature.ranstone.RanstonePiston;
import moe.gensoukyo.mcgproject.common.feature.sticker.TileSticker;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModTileEntity {

    private static ModTileEntity instance;
    public static ModTileEntity instance() {
        if(instance == null) instance = new ModTileEntity();
        return instance;
    }

    private ModTileEntity() { }

    private static void register(Class<? extends TileEntity> cls, String id) {
        GameRegistry.registerTileEntity(cls, new ResourceLocation(MCGProject.ID, id));
    }

    @SubscribeEvent
    public void register(RegistryEvent.Register<Block> event) {
        MCGProject.logger.info("MCGProject: registering tileEntities");

        register(RanstonePiston.TilePiston.class, "tileRanstonePiston");
        register(TileSticker.class, "tileSticker");
        register(RanstoneLamp.TileRanstoneLamp.class, "tileRanstoneLamp");
        register(TileLightBulb.class, "tileLightBulb");
        register(BlockKitunebi.TileKitunebi.class, "tileKitunebi");
    }

}
