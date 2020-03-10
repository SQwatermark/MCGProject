package moe.gensoukyo.mcgproject.cilent.init;

import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Objects;

@SideOnly(Side.CLIENT)
public class ModelMapper {

    private static ModelMapper instance;
    public static ModelMapper instance(){
        if(instance == null) instance = new ModelMapper();
        return instance;
    }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public void registerModels(ModelRegistryEvent event) {

        MCGProject.logger.info("MCGProject: registering models");

    }

    private static void registerModel(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, 
                new ModelResourceLocation(Objects.requireNonNull(item.getRegistryName()), 
                        "inventory"));
    }

    private static void registerModel(Item item, int metadata) {
        ModelLoader.setCustomModelResourceLocation(item, metadata,
                new ModelResourceLocation(Objects.requireNonNull(item.getRegistryName()),
                        "inventory"+metadata));
    }

    private static void registerModel(Item item, int metadata, String variantIn) {
        ModelLoader.setCustomModelResourceLocation(item, metadata,
                new ModelResourceLocation(Objects.requireNonNull(item.getRegistryName()),
                        variantIn));
    }
}
