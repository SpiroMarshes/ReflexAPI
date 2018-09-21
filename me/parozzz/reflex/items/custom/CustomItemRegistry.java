package me.parozzz.reflex.items.custom;

import me.parozzz.reflex.ReflexAPI;
import me.parozzz.reflex.items.NMSStackCompound;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public final class CustomItemRegistry
{
    public final static String ITEM_KEY = "CoreCustomItem";

    private static CustomItemRegistry customItemRegistry;
    public static CustomItemRegistry getInstance()
    {
        return customItemRegistry == null ? (customItemRegistry = new CustomItemRegistry()) : customItemRegistry;
    }

    private final Map<String, CustomItemCreator> getterMap;
    public CustomItemRegistry()
    {
        getterMap = new HashMap<>();
    }

    public <T extends CustomItem> void registerCustomItem(CustomItemCreator<T> itemCreator)
    {
        if(getterMap.containsKey(itemCreator.getItemIdentifier()))
        {
            ReflexAPI.logger().info("Trying to add a CustomItemCreatore with the same item identifier twice.");
            return;
        }

        getterMap.put(itemCreator.getItemIdentifier(), itemCreator);
    }

    @Nullable
    public CustomItem getCustomItemByStack(NMSStackCompound stack)
    {
        if(stack == null || stack.isAir())
        {
            return null;
        }

        String identifier = stack.getString(ITEM_KEY);
        if(identifier.isEmpty())
        {
            return null;
        }

        CustomItemCreator creator = getterMap.get(identifier);
        if(creator == null)
        {
            ReflexAPI.logger().warning("Trying to create a CustomItem with valid NBT but creator doesn't exists? Identifier: " + identifier);
            stack.removeKey(ITEM_KEY);
            return null;
        }

        return creator.createInstance(stack);
    }

}
