package moe.gensoukyo.mcgproject.common.feature.lightbulb;

import moe.gensoukyo.mcgproject.common.tileentity.AbstractTileEntity;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author drzzm32
 * @date 2020/4/27
 */
public class TileLightBulb extends AbstractTileEntity {

    private BlockPos source = BlockPos.ORIGIN;
    private boolean hasSource = false;

    @Override
    public void fromNBT(NBTTagCompound tag) {
        hasSource = tag.hasKey("source");
        if (hasSource) {
            NBTTagCompound pos = tag.getCompoundTag("source");
            source = new BlockPos(
                    pos.getInteger("x"),
                    pos.getInteger("y"),
                    pos.getInteger("z")
            );
        }
    }

    @Nonnull
    @Override
    public NBTTagCompound toNBT(NBTTagCompound tag) {
        if (hasSource) {
            NBTTagCompound pos = new NBTTagCompound();
            pos.setInteger("x", source.getX());
            pos.setInteger("y", source.getY());
            pos.setInteger("z", source.getZ());
            tag.setTag("source", pos);
        }

        return tag;
    }

    @Nullable
    public BlockPos getSource() {
        return hasSource ? source : null;
    }

    public void setSource(BlockPos pos) {
        hasSource = pos != null;
        source = pos;
    }

    public boolean isLit() {
        IBlockState state = world.getBlockState(pos);
        state = blockType.getActualState(state, world, pos);
        return state.getValue(BlockLightBulb.POWERED);
    }

}
