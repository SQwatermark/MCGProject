package moe.gensoukyo.mcgproject.cilent.entity;

import moe.gensoukyo.mcgproject.common.feature.musicplayer.EntityMusicPlayer;
import moe.gensoukyo.mcgproject.common.feature.musicplayer.ItemMusicPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderMinecart;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderMusicPlayer extends RenderMinecart<EntityMusicPlayer> {

    public static final IRenderFactory<EntityMusicPlayer> FACTORY = RenderMusicPlayer::new;

    public RenderMusicPlayer(RenderManager renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public boolean shouldRender(EntityMusicPlayer livingEntity, ICamera camera, double camX, double camY, double camZ) {
        if (livingEntity.immersive && !(Minecraft.getMinecraft().player.getHeldItemMainhand().getItem() instanceof ItemMusicPlayer)) return false;
        return super.shouldRender(livingEntity, camera, camX, camY, camZ);
    }
}