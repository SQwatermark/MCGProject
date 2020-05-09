package moe.gensoukyo.mcgproject.cilent.init;

import moe.gensoukyo.mcgproject.cilent.entity.*;
import moe.gensoukyo.mcgproject.cilent.entity.boat.RenderMCGBoat;
import moe.gensoukyo.mcgproject.cilent.entity.boat.RenderRACBoat;
import moe.gensoukyo.mcgproject.cilent.entity.butterfly.*;
import moe.gensoukyo.mcgproject.cilent.entity.fish.*;
import moe.gensoukyo.mcgproject.cilent.tileentity.TileLightBulbLightRenderer;
import moe.gensoukyo.mcgproject.cilent.tileentity.TileRanstoneLampLightRenderer;
import moe.gensoukyo.mcgproject.cilent.tileentity.TileRanstonePistonRenderer;
import moe.gensoukyo.mcgproject.cilent.tileentity.TileStickerRenderer;
import moe.gensoukyo.mcgproject.common.entity.EntityItemMCG;
import moe.gensoukyo.mcgproject.common.feature.kaginawa.EntityKaginawa;
import moe.gensoukyo.mcgproject.common.entity.boat.EntityMCGBoat;
import moe.gensoukyo.mcgproject.common.entity.boat.EntityRACBoat;
import moe.gensoukyo.mcgproject.common.entity.butterfly.*;
import moe.gensoukyo.mcgproject.common.entity.fish.*;
import moe.gensoukyo.mcgproject.common.feature.farm.apple.EntityApple;
import moe.gensoukyo.mcgproject.common.feature.lightbulb.BlockLightBulb;
import moe.gensoukyo.mcgproject.common.feature.lightbulb.TileLightBulb;
import moe.gensoukyo.mcgproject.common.feature.farm.stone.EntityLittleRock;
import moe.gensoukyo.mcgproject.common.feature.musicplayer.EntityMusicPlayer;
import moe.gensoukyo.mcgproject.common.feature.ranstone.*;
import moe.gensoukyo.mcgproject.common.feature.sticker.BlockSticker;
import moe.gensoukyo.mcgproject.common.feature.sticker.TileSticker;
import moe.gensoukyo.mcgproject.common.init.ModItem;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
        for (Class<? extends Entity> entity : renderEntity.keySet()) {
            bind(entity, renderEntity.get(entity));
        }

        registerModel(ModItem.ITEM_META_CHANGER);
        registerModel(ModItem.ITEM_MCG_BOAT);
        registerModel(ModItem.ITEM_RAC_BOAT);
        registerModel(ModItem.ITEM_GEN_BIG_OAK);
        registerModel(ModItem.ITEM_KAGINAWA);
        registerModel(ModItem.ITEM_BLOCK_INFO);
        registerModel(ModItem.ITEM_MCG_FOOD, 94);
        registerModel(ModItem.ITEM_MCG_DRINK, 30);
        registerModel(ModItem.ITEM_MCG_PROP, 94);
        registerModel(ModItem.ITEM_MCG_BANNER_PATTERN, 16);
        registerModel(ModItem.ITEM_MUSIC_PLAYER);
        registerModel(ModItem.ITEM_LITTLE_STONE);
        registerModel(ModItem.ITEM_ROU_KAN_KEN);

        registerColoredModel(RanstoneLamp.ITEM, "");
        registerColoredModel(RanstoneLamp.ITEM_ALWAYS, "");
        registerColoredModel(BlockLightBulb.ITEM, ",facing=up,powered=false");
        registerModel(RanstoneBlock.ITEM);
        registerModel(RanstoneComparator.ITEM);
        registerModel(RanstoneRepeater.ITEM);
        registerModel(RanstoneTorch.ITEM);
        registerModel(RanstoneWire.ITEM);
        registerModel(RanstonePiston.Base.ITEM);

        registerModel(BlockSticker.ITEM);
        registerModel(BlockSticker.ITEM_LIT);

        ClientRegistry.bindTileEntitySpecialRenderer(RanstonePiston.TilePiston.class, new TileRanstonePistonRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileSticker.class, new TileStickerRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(RanstoneLamp.TileRanstoneLamp.class, new TileRanstoneLampLightRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileLightBulb.class, new TileLightBulbLightRenderer());
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

    private static void registerModel(Item item, int maxMeta) {
        int t = maxMeta + 1;
        for (int i = 0; i < t; i++) {
            ModelLoader.setCustomModelResourceLocation(item, i,
                    new ModelResourceLocation(Objects.requireNonNull(item.getRegistryName()),
                            String.valueOf(i)));
        }
    }

    private static void registerColoredModel(Item item, String extra) {
        for (int i = 0; i < 16; i++) {
            ModelLoader.setCustomModelResourceLocation(item, i,
                    new ModelResourceLocation(Objects.requireNonNull(item.getRegistryName()),
                            "color=" + EnumDyeColor.byMetadata(i).getName() + extra));
        }
    }

    static {
        renderEntity = new LinkedHashMap<>();

        renderEntity.put(EntityMCGBoat.class, RenderMCGBoat.FACTORY);
        renderEntity.put(EntityRACBoat.class, RenderRACBoat.FACTORY);
        renderEntity.put(EntityButterfly.class, RenderButterfly.FACTORY);
        renderEntity.put(EntityButterfly1.class, RenderButterfly1.FACTORY);
        renderEntity.put(EntityCloudShimmer.class, RenderCloudShimmer.FACTORY);
        renderEntity.put(EntityIllukini.class, RenderIllukini.FACTORY);
        renderEntity.put(EntitySkylight.class, RenderSkylight.FACTORY);
        renderEntity.put(EntityKaginawa.class, RenderKaginawa.FACTORY);
        renderEntity.put(EntityRubySile.class, RenderRubySile.FACTORY);
        renderEntity.put(EntityCod.class, RenderCod.FACTORY);
        renderEntity.put(EntitySalmon.class, RenderSalmon.FACTORY);
        renderEntity.put(EntityTropicalFishA.class, RenderTropicalFishA.FACTORY);
        renderEntity.put(EntityTropicalFishB.class, RenderTropicalFishB.FACTORY);
        renderEntity.put(EntityPufferFish.class, RenderPufferFish.FACTORY);
        renderEntity.put(EntityMusicPlayer.class, RenderMusicPlayer.FACTORY);
        renderEntity.put(EntityApple.class, RenderApple.FACTORY);
        renderEntity.put(EntityItemMCG.class, RenderItemMCG.FACTORY);
        renderEntity.put(EntityLittleRock.class, RenderLittleStone.FACTORY);
    }

}
