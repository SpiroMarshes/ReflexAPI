package me.parozzz.reflex.items.custom;

import me.parozzz.reflex.items.Itemable;
import me.parozzz.reflex.items.NMSStackCompound;
import me.parozzz.reflex.nms.nbt.NBTCompound;
import org.apache.commons.lang.Validate;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

public abstract class CustomItem implements Itemable
{
    protected final NMSStackCompound stack;
    protected final NBTCompound customCompound;

    public CustomItem(@Nullable String identifier, NMSStackCompound stack)
    {
        this.stack = stack;

        if(identifier == null)
        {
            identifier = stack.getString(CustomItemRegistry.ITEM_KEY);
            Validate.isTrue(!identifier.isEmpty(), "Something is wrong in a CustomItem. It has not valid identifier yet trying to create it?");
        }
        else
        {
            stack.setString(CustomItemRegistry.ITEM_KEY, identifier);
        }

        customCompound = stack.getWrappedCompound(identifier);
    }

    public final NBTCompound getCustomCompound()
    {
        return customCompound;
    }

    public final NMSStackCompound getStack()
    {
        return stack;
    }

    @Override
    public final ItemStack getItemStack()
    {
        return stack.getItemStack();
    }

    @Override
    public String toString()
    {
        return stack.toString();
    }
}
