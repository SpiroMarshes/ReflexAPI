package me.parozzz.reflex.gui.click;

import me.parozzz.reflex.events.wrappers.wrappers.WrappedInventoryClickEvent;
import me.parozzz.reflex.items.NMSStackCompound;
import me.parozzz.reflex.items.custom.CustomItem;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

public final class SwapClickEvent extends ClickEvent
{
    public SwapClickEvent(final WrappedInventoryClickEvent wrapper)
    {
        super(wrapper);
    }

    //++++++++++++++++++++++++++++++++++++++++++++
    //============= CURSOR RELATED ===============
    //++++++++++++++++++++++++++++++++++++++++++++

    public ItemStack getCursor()
    {
        return getEvent().getCursor();
    }

    public NMSStackCompound getCursorStack()
    {
        return wrapper.getCursorStack();
    }

    public CustomItem getCursorCustomItem()
    {
        return wrapper.getCursorCustomItem();
    }

    public @Nullable <T extends CustomItem> T getCursorCustomItem(Class<T> itemClass)
    {
        return wrapper.getCursorCustomItem(itemClass);
    }

    //++++++++++++++++++++++++++++++++++++++++++++
}
