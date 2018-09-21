package me.parozzz.reflex.events.wrappers;

import me.parozzz.reflex.items.NMSStackCompound;
import me.parozzz.reflex.items.custom.CustomItem;
import me.parozzz.reflex.items.custom.CustomItemRegistry;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

public abstract class PlayerItemEventWrapper<T extends Event> extends PlayerEventWrapper<T>
{
    private NMSStackCompound involvedStack;
    private CustomItem involvedCustomItem;

    private boolean conversionTried = false;

    public PlayerItemEventWrapper(T wrapped, NMSStackCompound handStack)
    {
        super(wrapped);

        this.involvedStack = handStack;
    }

    public PlayerItemEventWrapper(T wrapped, ItemStack itemStack)
    {
        this(wrapped, new NMSStackCompound(itemStack));
    }

    public NMSStackCompound getInvolvedStack()
    {
        return involvedStack;
    }

    public @Nullable <T extends CustomItem> T getInvolvedCustomItem(Class<T> itemClass)
    {
        CustomItem customItem = getInvolvedCustomItem();
        return itemClass.isInstance(customItem) ? (T) customItem : null;
    }

    public @Nullable CustomItem getInvolvedCustomItem()
    {
        if(conversionTried)
        {
            return involvedCustomItem;
        }

        conversionTried = true;
        return (involvedCustomItem = CustomItemRegistry.getInstance().getCustomItemByStack(involvedStack));
    }
}
