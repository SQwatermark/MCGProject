package moe.gensoukyo.mcgproject.cilent.entity;

import moe.gensoukyo.mcgproject.common.feature.musicplayer.EntityMusicPlayer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderMinecart;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderMusicPlayer extends RenderMinecart<EntityMusicPlayer> {

    public static final IRenderFactory<EntityMusicPlayer> FACTORY = RenderMusicPlayer::new;

    public RenderMusicPlayer(RenderManager renderManagerIn) {
        super(renderManagerIn);
    }

}
