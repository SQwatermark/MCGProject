package moe.gensoukyo.mcgproject.common.init;

import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.LinkedList;

/**
 * @author drzzm32
 * @date 2020/5/26
 * @apiNote 音频播放类
 */
public class ModSound {

    private static ModSound instance;
    public static ModSound instance() {
        if (instance == null) instance = new ModSound();
        return instance;
    }

    public LinkedList<SoundEvent> sounds;

    public final SoundEvent ELEVATOR_UP;
    public final SoundEvent ELEVATOR_DOWN;
    public final SoundEvent ELEVATOR_FLIP;
    public final SoundEvent GENSOCHEST_OPEN;

    @SubscribeEvent
    public void registerSounds(RegistryEvent.Register<SoundEvent> event) {
        MCGProject.logger.info("MCGProject: registering SoundEvents");
        event.getRegistry().registerAll(sounds.toArray(new SoundEvent[0]));
    }

    private SoundEvent addSound(String id) {
        ResourceLocation location = new ResourceLocation(MCGProject.ID, id);
        SoundEvent soundEvent = new SoundEvent(location);
        soundEvent.setRegistryName(location);
        sounds.add(soundEvent);
        return soundEvent;
    }

    public ModSound() {
        sounds = new LinkedList<>();

        ELEVATOR_UP = addSound("effect.elevator.up");
        ELEVATOR_DOWN = addSound("effect.elevator.down");
        ELEVATOR_FLIP = addSound("effect.elevator.flip");
        GENSOCHEST_OPEN = addSound("effect.gensochest.open");
    }

    public void playSound(Entity entity, SoundEvent event, SoundCategory category) {
        playSound(entity.world, entity.posX, entity.posY, entity.posZ, event, category);
    }

    public void playSound(World world, BlockPos pos, SoundEvent event, SoundCategory category) {
        world.playSound(null,
                pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                event, category, 0.5F, 1.0F);
    }

    public void playSound(World world, double x, double y, double z, SoundEvent event, SoundCategory category) {
        world.playSound(null, x, y, z, event, category, 0.5F, 1.0F);
    }

}
