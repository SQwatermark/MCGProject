package moe.gensoukyo.mcgproject.common.init;

import moe.gensoukyo.mcgproject.common.entity.EntityItemMCG;
import moe.gensoukyo.mcgproject.common.entity.MCGEntity;
import moe.gensoukyo.mcgproject.common.entity.boat.EntityMCGBoat;
import moe.gensoukyo.mcgproject.common.entity.boat.EntityRACBoat;
import moe.gensoukyo.mcgproject.common.entity.butterfly.*;
import moe.gensoukyo.mcgproject.common.entity.cart.*;
import moe.gensoukyo.mcgproject.common.entity.fish.*;
import moe.gensoukyo.mcgproject.common.feature.farm.apple.EntityApple;
import moe.gensoukyo.mcgproject.common.feature.kaginawa.EntityKaginawa;
import moe.gensoukyo.mcgproject.common.feature.musicplayer.EntityMusicPlayer;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import java.util.LinkedList;

/**
 * @author drzzm32
 * @date 2020/3/10
 */
public class ModEntity {

    private static ModEntity instance;
    public static ModEntity instance() {
        if (instance == null) instance = new ModEntity();
        return instance;
    }

    public static LinkedList<Class<? extends Entity>> entities;

    private static void register(int index, Class<? extends Entity> entity) {
        String name = entity.getSimpleName().toLowerCase();
        if (entity.isAnnotationPresent(MCGEntity.class))
            name = entity.getAnnotation(MCGEntity.class).value();
        EntityRegistry.registerModEntity(
                new ResourceLocation(MCGProject.ID, name),
                entity, name, index, MCGProject.INSTANCE,
                256, 3, true
        );
    }

    @SubscribeEvent
    public void registerEntities(RegistryEvent.Register<EntityEntry> event) {
        MCGProject.logger.info("MCGProject: registering entities");
        for (int i = 0; i < entities.size(); i++)
            register(i, entities.get(i));
    }

    public ModEntity() {
        entities = new LinkedList<>();

        entities.add(EntityMCGBoat.class);
        entities.add(EntityRACBoat.class);
        entities.add(EntityButterfly.class);
        entities.add(EntityButterfly1.class);
        entities.add(EntityCloudShimmer.class);
        entities.add(EntityIllukini.class);
        entities.add(EntitySkylight.class);
        entities.add(EntityKaginawa.class);
        entities.add(EntityRubySile.class);
        entities.add(EntityCod.class);
        entities.add(EntitySalmon.class);
        entities.add(EntityTropicalFishA.class);
        entities.add(EntityTropicalFishB.class);
        entities.add(EntityPufferFish.class);
        entities.add(EntityMusicPlayer.class);
        entities.add(EntityApple.class);
        entities.add(EntityItemMCG.class);

        entities.add(GRBogie.class);
        entities.add(GRMotor.class);
        entities.add(GRM3A.class);
        entities.add(GRM3B.class);
        entities.add(GRW4.class);
        entities.add(GRW4.Basket.class);
        entities.add(GRW4M.class);
        entities.add(GRW4M.Basket.class);
        entities.add(GRC2.class);
        entities.add(GRC2M.class);
        entities.add(GRH2.class);
        entities.add(GRH2M.class);
    }

}
