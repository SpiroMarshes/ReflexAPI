package me.parozzz.reflex.serializable.compound;

import me.parozzz.reflex.nms.nbt.NBTCompound;
import me.parozzz.reflex.serializable.compound.CompoundSerializable;

public interface CompoundSerializedItem<T extends CompoundSerializable>
{
    NBTCompound getSerializedCompound();

    default T saveData(final T t)
    {
        t.loadFromCompound(getSerializedCompound());
        return t;
    }
}
