package me.parozzz.reflex.nms.nbt;

import net.minecraft.server.v1_13_R2.NBTBase;

/**
 * @param <T>
 * @author Paros
 */
public interface NBT<T extends NBTBase>
{
    T getBase();

    NBTType getType();
}
