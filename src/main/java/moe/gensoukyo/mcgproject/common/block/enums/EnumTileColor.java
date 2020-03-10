package moe.gensoukyo.mcgproject.common.block.enums;

import net.minecraft.util.IStringSerializable;

import java.util.Locale;

public enum EnumTileColor implements IStringSerializable
{
    BLACK(0),
    BLUE(1),
    BROWN(2),
    GRAY(3),
    ULTRAM(4);
    private final int meta;

    private EnumTileColor(int meta){
        this.meta = meta;
    }
    public int getMeta() {
        return this.meta;
    }
    @Override
    public String getName()
    {
        return this.name().toLowerCase(Locale.ENGLISH);
    }
    @Override
    public String toString()
    {
        return this.getName();
    }

}
