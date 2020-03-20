package moe.gensoukyo.mcgproject.cilent.init;

import moe.gensoukyo.mcgproject.cilent.entity.RenderMCGBoat;
import moe.gensoukyo.mcgproject.cilent.tileentity.TileRanstonePistonRenderer;
import moe.gensoukyo.mcgproject.common.entity.EntityMCGBoat;
import moe.gensoukyo.mcgproject.common.init.ModItem;
import moe.gensoukyo.mcgproject.common.ranstone.*;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.tileentity.TileEntityPistonRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Objects;

@SideOnly(Side.CLIENT)
public class ModelMapper {

    private static ModelMapper instance;
    public static ModelMapper instance(){
        if(instance == null) instance = new ModelMapper();
        return instance;
    }

    public static LinkedHashMap<Class<? extends Entity>, IRenderFactory<? extends Entity>> renderEntity;

    private static void bind(Class entity, IRenderFactory factory) {
        RenderingRegistry.registerEntityRenderingHandler(entity, factory);
    }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public void registerModels(ModelRegistryEvent event) {
        MCGProject.logger.info("MCGProject: registering models");
        for (Class entity : renderEntity.keySet()) {
            bind(entity, renderEntity.get(entity));
        }

        registerModel(ModItem.ITEM_META_CHANGER);
        registerModel(ModItem.ITEM_POWERTOOL);
        registerModel(ModItem.ITEM_MCG_BOAT);

        registerModel(RanstoneBlock.ITEM);
        registerModel(RanstoneComparator.ITEM);
        registerModel(RanstoneRepeater.ITEM);
        registerModel(RanstoneTorch.ITEM);
        registerModel(RanstoneWire.ITEM);
        registerModel(RanstonePiston.Base.ITEM);

        ClientRegistry.bindTileEntitySpecialRenderer(RanstonePiston.TilePiston.class, new TileRanstonePistonRenderer());
    }

    @SubscribeEvent
    public void blockColorHandler(ColorHandlerEvent.Block event) {
        event.getBlockColors().registerBlockColorHandler(
                (state, world, pos, i) -> RanstoneWire.colorMultiplier(state.getValue(RanstoneWire.POWER)),
                RanstoneWire.BLOCK
        );
    }

    private static void registerModel(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, 
                new ModelResourceLocation(Objects.requireNonNull(item.getRegistryName()), 
                        "inventory"));
    }

    private static void registerModel(Item item, int metadata) {
        ModelLoader.setCustomModelResourceLocation(item, metadata,
                new ModelResourceLocation(Objects.requireNonNull(item.getRegistryName()),
                        "inventory" + metadata));
    }

    private static void registerModel(Item item, int metadata, String variantIn) {
        ModelLoader.setCustomModelResourceLocation(item, metadata,
                new ModelResourceLocation(Objects.requireNonNull(item.getRegistryName()),
                        variantIn));
    }

    static {
        renderEntity = new LinkedHashMap<>();

        renderEntity.put(EntityMCGBoat.class, RenderMCGBoat.FACTORY);
    }

}
