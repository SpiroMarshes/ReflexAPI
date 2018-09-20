package me.parozzz.reflex.NMS.nbt;

import net.minecraft.server.v1_13_R2.*;

import javax.annotation.Nullable;

public class NBTList implements NBT<NBTTagList>
{
    private final NBTTagList list;
    private NBTType type;

    protected NBTList(final NBTTagList list, final NBTType type)
    {
        this.list = list;
        this.type = type;
    }

    protected NBTList(final NBTTagList list)
    {
        this.list = list;
    }

    /**
     * Get the NBTType of the values in this list.
     *
     * @return The NBTType of the values, or null if the list does not have a defined type.
     */
    @Nullable
    public NBTType getValuesType()
    {
        if(type == null)
        {
            NBTType valuesType = NBTType.getType(list.d());
            if(valuesType != NBTType.END)
            {
                type = valuesType;
            }
        }

        return type;
    }

    public void addString(final String value)
    {
        list.add(new NBTTagString(value));
    }

    public void setString(final int index, final String value)
    {
        list.a(index, new NBTTagString(value));
    }

    @Nullable
    public String getString(final int index)
    {
        return list.getString(index);
    }

    public void addInt(final int value)
    {
        list.add(new NBTTagInt(value));
    }

    public void setInt(final int index, final int value)
    {
        list.a(index, new NBTTagInt(value));
    }

    public int getInt(final int index)
    {
        return list.h(index);
    }

    public void addLong(final long value)
    {
        list.add(new NBTTagLong(value));
    }

    public void setLong(final int index, final long value)
    {
        list.a(index, new NBTTagLong(value));
    }

    public long getLong(final int index)
    {
        NBTBase base = list.get(index);
        return base.getTypeId() == NBTType.LONG.getId() ? ((NBTTagLong) base).d() : 0L;
    }

    public void addDouble(final double value)
    {
        list.add(new NBTTagDouble(value));
    }

    public void setDouble(final int index, final double value)
    {
        list.a(index, new NBTTagDouble(value));
    }

    public double getDouble(final int index)
    {
        return list.k(index);
    }

    public void addNBT(final NBT nbt)
    {
        list.add(nbt.getBase());
    }

    public void setNBT(final int index, final NBT nbt)
    {
        list.a(index, nbt.getBase());
    }

    public void remove(final int index)
    {
        list.remove(index);
    }

    public boolean isEmpty()
    {
        return list.isEmpty();
    }

    public NBTTagCompound getCompound(final int index)
    {
        return list.getCompound(index);
    }

    public NBTTagList getList(final int index)
    {
        return list.f(index);
    }

    public NBTCompound getWrappedCompound(final int index)
    {
        return new NBTCompound(getCompound(index));
    }

    public @Nullable
    NBTList getWrappedList(final int index)
    {
        NBTTagList localList = getList(index);
        return localList != null ? new NBTList(localList) : null;
    }

    public int size()
    {
        return list.size();
    }

    public void clear()
    {
        int size = size();
        while(size-- > 0)
        {
            list.remove(size);
        }
    }

    @Override
    public String toString()
    {
        return list.toString();
    }

    @Override
    public NBTTagList getBase()
    {
        return list;
    }

    @Override
    public NBTType getType()
    {
        return NBTType.LIST;
    }
}
