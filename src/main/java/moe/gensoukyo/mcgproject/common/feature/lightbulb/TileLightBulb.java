package moe.gensoukyo.mcgproject.common.feature.lightbulb;

import moe.gensoukyo.mcgproject.common.tileentity.AbstractTileEntity;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author drzzm32
 * @date 2020/4/27
 */
public class TileLightBulb extends AbstractTileEntity {

    EnumFacing facing = EnumFacing.UP;
    boolean powered = false;

    private BlockPos source = BlockPos.ORIGIN;
    private boolean hasSource = false;

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return !(oldState.getBlock() instanceof BlockLightBulb) || !(newState.getBlock() instanceof BlockLightBulb);
    }

    @Override
    public void fromNBT(NBTTagCompound tag) {
        facing = EnumFacing.byIndex(tag.getInteger("facing"));
        powered = tag.getBoolean("powered");
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
        if (facing == null) facing = EnumFacing.UP;
        tag.setInteger("facing", facing.getIndex());
        tag.setBoolean("powered", powered);
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
        return powered;
    }

    @Override
    public double getMaxRenderDistanceSquared() {
        return 1024.0D;
    }

}
