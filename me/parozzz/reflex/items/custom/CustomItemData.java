package me.parozzz.reflex.items.custom;

import java.util.function.Supplier;

class CustomItemData<T extends CustomItem>
{
    private final String identifier;
    private final Class<T> itemClass;
    private final Supplier<T> instanceCreator;
    public CustomItemData(String identifier, Class<T> itemClass, Supplier<T> instanceCreator)
    {
        this.identifier = identifier;
        this.itemClass = itemClass;
        this.instanceCreator = instanceCreator;
    }

    public String getIdentifier()
    {
        return identifier;
    }

    public Class<T> getItemClass()
    {
        return itemClass;
    }

    public T createInstance()
    {
        return instanceCreator.get();
    }
}
