package moe.gensoukyo.mcgproject.cilent.entity;

import moe.gensoukyo.mcgproject.common.feature.musicplayer.EntityMusicPlayer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderMinecart;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderMusicPlayer extends RenderMinecart<EntityMusicPlayer> {

    public static final Factory FACTORY = new Factory();

    public RenderMusicPlayer(RenderManager renderManagerIn) {
        super(renderManagerIn);
    }

    public static class Factory implements IRenderFactory<EntityMusicPlayer> {

        @Override
        public Render<? super EntityMusicPlayer> createRenderFor(RenderManager manager) {
            return new RenderMusicPlayer(manager);
        }

    }

    @Override
    protected void renderCartContents(EntityMusicPlayer musicPlayer, float partialTicks, IBlockState blockState)
    {
        super.renderCartContents(musicPlayer, partialTicks, Blocks.JUKEBOX.getDefaultState());
    }

}
