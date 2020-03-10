package moe.gensoukyo.mcgproject.common.block.enums;

import net.minecraft.util.IStringSerializable;

import java.util.Locale;

public enum EnumFlowerVariant implements IStringSerializable
{
    CONVALLARIA_MAJALIS, IRIS, LYCORIS, SUNFLOWER, SUNFLOWER_STALK;
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
