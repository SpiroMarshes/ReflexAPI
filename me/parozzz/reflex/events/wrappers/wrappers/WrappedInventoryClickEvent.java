package me.parozzz.reflex.events.wrappers.wrappers;

import me.parozzz.reflex.events.wrappers.PlayerEventWrapper;
import me.parozzz.reflex.items.NMSStackCompound;
import me.parozzz.reflex.items.custom.CustomItem;
import me.parozzz.reflex.items.custom.CustomItemRegistry;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.inventory.InventoryClickEvent;

import javax.annotation.Nullable;

public class WrappedInventoryClickEvent extends PlayerEventWrapper<InventoryClickEvent> implements Cancellable
{
    private NMSStackCompound currentStack;
    private NMSStackCompound cursorStack;

    private CustomItem currentCustomItem;
    private boolean currentConversionTried;

    private CustomItem cursorCustomItem;
    private boolean cursorConversionTried;

    public WrappedInventoryClickEvent(InventoryClickEvent wrapped)
    {
        super(wrapped);
    }

    @Override
    public Player getPlayer()
    {
        return (Player) getWrappedEvent().getWhoClicked();
    }

    //+++++++++++++++++++++++++++++++++++++++++++++
    //============= CURRENT RELATED ===============
    //+++++++++++++++++++++++++++++++++++++++++++++


    public NMSStackCompound getCurrentStack()
    {
        return currentStack == null ? (currentStack = new NMSStackCompound(getWrappedEvent().getCurrentItem())) : currentStack;
    }

    public @Nullable CustomItem getCurrentCustomItem()
    {
        if(currentConversionTried)
        {
            return currentCustomItem;
        }

        currentConversionTried = true;
        return (currentCustomItem = CustomItemRegistry.getInstance().getCustomItemByStack(getCurrentStack()));
    }

    public @Nullable <T extends CustomItem> T getCurrentCustomItem(Class<T> itemClass)
    {
        CustomItem customItem = getCurrentCustomItem();
        return itemClass.isInstance(customItem) ? (T) customItem : null;
    }

    //++++++++++++++++++++++++++++++++++++++++++++
    //============= CURSOR RELATED ===============
    //++++++++++++++++++++++++++++++++++++++++++++

    public NMSStackCompound getCursorStack()
    {
        return cursorStack == null ? (cursorStack = new NMSStackCompound(getWrappedEvent().getCursor())) : cursorStack;
    }

    public @Nullable CustomItem getCursorCustomItem()
    {
        if(cursorConversionTried)
        {
            return cursorCustomItem;
        }

        cursorConversionTried = true;
        return (cursorCustomItem = CustomItemRegistry.getInstance().getCustomItemByStack(getCursorStack()));
    }

    public @Nullable <T extends CustomItem> T getCursorCustomItem(Class<T> itemClass)
    {
        CustomItem customItem = getCursorCustomItem();
        return itemClass.isInstance(customItem) ? (T) customItem : null;
    }

    @Override
    public boolean isCancelled()
    {
        return getWrappedEvent().isCancelled();
    }

    @Override
    public void setCancelled(boolean cancel)
    {
        getWrappedEvent().setCancelled(cancel);
    }
}
