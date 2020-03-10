package moe.gensoukyo.mcgproject.common.block;

import moe.gensoukyo.mcgproject.common.creativetab.MCGTabs;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.util.math.AxisAlignedBB;

public class XBlockMCGBamboo extends BlockMCG {

    public XBlockMCGBamboo() {
        super(Material.WOOD, "bamboo", MCGTabs.NATURE);
    }

    // add properties
    public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 15);
    @Override
    protected BlockStateContainer createBlockState() {return new BlockStateContainer(this, AGE);}

    protected static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, 0.8125D, 1.0D, 0.8125D);

}
