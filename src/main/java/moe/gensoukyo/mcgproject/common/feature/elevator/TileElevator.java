package moe.gensoukyo.mcgproject.common.feature.elevator;

import moe.gensoukyo.mcgproject.common.tileentity.AbstractTileEntity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;

public class TileElevator extends AbstractTileEntity {

    private boolean hasUp = false;
    private BlockPos upCtl = BlockPos.ORIGIN;

    private boolean hasDown = false;
    private BlockPos downCtl = BlockPos.ORIGIN;

    @Override
    public void fromNBT(NBTTagCompound tag) {
        hasUp = tag.hasKey("upCtl");
        if (hasUp) {
            NBTTagCompound pos = tag.getCompoundTag("upCtl");
            upCtl = new BlockPos(
                    pos.getInteger("x"),
                    pos.getInteger("y"),
                    pos.getInteger("z")
            );
        }
        hasDown = tag.hasKey("downCtl");
        if (hasDown) {
            NBTTagCompound pos = tag.getCompoundTag("downCtl");
            downCtl = new BlockPos(
                    pos.getInteger("x"),
                    pos.getInteger("y"),
                    pos.getInteger("z")
            );
        }
    }

    @Nonnull
    @Override
    public NBTTagCompound toNBT(NBTTagCompound tag) {
        if (hasUp) {
            NBTTagCompound pos = new NBTTagCompound();
            pos.setInteger("x", upCtl.getX());
            pos.setInteger("y", upCtl.getY());
            pos.setInteger("z", upCtl.getZ());
            tag.setTag("upCtl", pos);
        }
        if (hasDown) {
            NBTTagCompound pos = new NBTTagCompound();
            pos.setInteger("x", downCtl.getX());
            pos.setInteger("y", downCtl.getY());
            pos.setInteger("z", downCtl.getZ());
            tag.setTag("downCtl", pos);
        }

        return tag;
    }

    public void setUpCtl(BlockPos pos) {
        hasUp = pos != null;
        upCtl = pos;
    }

    public void setDownCtl(BlockPos pos) {
        hasDown = pos != null;
        downCtl = pos;
    }

    public boolean hasUpCtl() {
        return hasUp;
    }

    public boolean hasDownCtl() {
        return hasDown;
    }

    public boolean upCrl() {
        if (hasUp)
            return world.isBlockPowered(upCtl);
        return false;
    }

    public boolean downCrl() {
        if (hasDown)
            return world.isBlockPowered(downCtl);
        return false;
    }

}
