package me.parozzz.reflex.NMS.nbt;

import net.minecraft.server.v1_13_R2.NBTBase;
import net.minecraft.server.v1_13_R2.NBTTagCompound;
import net.minecraft.server.v1_13_R2.NBTTagList;

import java.util.Set;

public class NBTCompound implements NBT<NBTTagCompound>
{
    private NBTTagCompound tag;

    public NBTCompound(final NBTTagCompound compound)
    {
        tag = compound;
    }

    protected NBTCompound()
    {
    }

    public static NBTCompound getNew()
    {
        return new NBTCompound().setCompound(new NBTTagCompound());
    }

    protected final NBTCompound setCompound(final NBTTagCompound tag)
    {
        this.tag = tag;
        return this;
    }

    public final void set(final String key, final NBTBase nbt)
    {
        tag.set(key, nbt);
    }

    public void setCompound(final String key, final NBTCompound compound)
    {
        tag.set(key, compound.getBase());
    }

    public final NBTCompound setBoolean(final String key, final boolean value)
    {
        setByte(key, (byte) (value ? 1 : 0));
        return this;
    }

    public final boolean getBoolean(final String key)
    {
        return getByte(key) != 0;
    }

    public final NBTCompound setByte(final String key, final byte value)
    {
        tag.setByte(key, value);
        return this;
    }

    public final byte getByte(final String key)
    {
        return tag.getByte(key);
    }

    public final NBTCompound setByteArray(final String key, final byte[] value)
    {
        tag.setByteArray(key, value);
        return this;
    }

    public final byte[] getByteArray(final String key)
    {
        return tag.getByteArray(key);
    }

    public final NBTCompound setShort(final String key, final short value)
    {
        tag.setShort(key, value);
        return this;
    }

    public final short getShort(final String key)
    {
        return tag.getShort(key);
    }

    public final NBTCompound setInt(final String key, final int value)
    {
        tag.setInt(key, value);
        return this;
    }

    public final int getInt(final String key)
    {
        return tag.getInt(key);
    }

    public final NBTCompound setIntArray(final String key, final int[] value)
    {
        tag.setIntArray(key, value);
        return this;
    }

    public final int[] getIntArray(final String key)
    {
        return tag.getIntArray(key);
    }

    public final NBTCompound setLong(final String key, final long value)
    {
        tag.setLong(key, value);
        return this;
    }

    public final long getLong(final String key)
    {
        return tag.getLong(key);
    }

    public final NBTCompound setFloat(final String key, final float value)
    {
        tag.setFloat(key, value);
        return this;
    }

    public final float getFloat(final String key)
    {
        return tag.getFloat(key);
    }

    public final NBTCompound setDouble(final String key, final double value)
    {
        tag.setDouble(key, value);
        return this;
    }

    public final double getDouble(final String key)
    {
        return tag.getDouble(key);
    }

    public final NBTCompound setString(final String key, final String value)
    {
        tag.setString(key, value);
        return this;
    }

    public final String getString(final String key)
    {
        return tag.getString(key);
    }

    public final NBTTagCompound getCompound(final String key)
    {
        return tag.getCompound(key);
    }

    /**
     * This value won't need to reapply the NBTTagCompound, since it does it automatically
     *
     * @param key The key parameter for the main NBTTagCompound
     * @return The wrapped NBTTagCompound from the main NBTTagCompound
     */
    public final NBTCompound getWrappedCompound(final String key)
    {
        NBTTagCompound compound = tag.getCompound(key);
        if(!tag.hasKeyOfType(key, NBTType.COMPOUND.getId()))
        {
            tag.set(key, compound);
        }
        return new NBTCompound(compound);
    }

    public final NBTTagList getList(final String key, final NBTType type)
    {
        return tag.getList(key, type.getId());
    }

    public final NBTList getWrapperList(final String key, final NBTType type)
    {
        NBTTagList list = tag.getList(key, type.getId());
        if(!tag.hasKeyOfType(key, NBTType.LIST.getId()))
        {
            tag.set(key, list);
        }
        return new NBTList(list, type);
    }

    public final NBTCompound removeKey(final String key)
    {
        tag.remove(key);
        return this;
    }

    public final boolean hasKey(final String key)
    {
        return tag.hasKey(key);
    }

    public final boolean hasKeyOfType(final String key, final NBTType type)
    {
        return tag.hasKeyOfType(key, type.getId());
    }

    public final Set<String> keySet()
    {
        return tag.getKeys();
    }

    public boolean isEmpty()
    {
        return tag.isEmpty();
    }

    public final int size()
    {
        return tag.d();
    }

    @Override
    public String toString()
    {
        return tag.toString();
    }

    @Override
    public final NBTTagCompound getBase()
    {
        return tag;
    }

    @Override
    public final NBTType getType()
    {
        return NBTType.COMPOUND;
    }
}