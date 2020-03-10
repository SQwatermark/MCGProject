package moe.gensoukyo.mcgproject.common.fluid.blocks;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class BlockMCGWater extends BlockFluidClassic {

    public BlockMCGWater(Fluid fluid, Material material, MapColor mapColor) {
        super(fluid, material, mapColor);
    }

    public BlockMCGWater(Fluid fluid, Material material) {
        super(fluid, material);
    }

}
