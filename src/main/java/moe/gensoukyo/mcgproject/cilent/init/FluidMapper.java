package moe.gensoukyo.mcgproject.cilent.init;

import moe.gensoukyo.mcgproject.common.init.ModBlock;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nonnull;

/**
 * @author Chloe_koopa
 */
@Mod.EventBusSubscriber(value = Side.CLIENT, modid = MCGProject.ID)
public class FluidMapper
{
    @SubscribeEvent
    public static void map(ModelRegistryEvent event)
    {
        ModBlock.CS4_FLUIDS.forEach(FluidMapper::mapBlock);
    }

    private static void mapBlock(BlockFluidBase block)
    {
        ModelLoader.setCustomStateMapper(block, new StateMapperBase()
        {
            final ModelResourceLocation state =
                    new ModelResourceLocation(FLUID_STATE_FILE, block.getFluid().getName());
            @Override
            @Nonnull
            protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state)
            {
                return this.state;
            }
        });
    }

    static final ResourceLocation FLUID_STATE_FILE =
            new ResourceLocation(MCGProject.ID, "fluid");
}
