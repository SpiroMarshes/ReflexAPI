package me.parozzz.reflex.items.custom;

import me.parozzz.reflex.items.NMSStackCompound;

public abstract class CustomItemCreator<T extends CustomItem>
{
    private final Class<T> itemClass;
    private final String itemIdentifier;
    public CustomItemCreator(Class<T> itemClass, String itemIdentifier)
    {
        this.itemClass = itemClass;
        this.itemIdentifier = itemIdentifier;
    }

    protected abstract T createInstance(NMSStackCompound stack);

    public Class<T> getItemClass()
    {
        return itemClass;
    }

    public String getItemIdentifier()
    {
        return itemIdentifier;
    }
}
