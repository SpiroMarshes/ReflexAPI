package me.parozzz.reflex.gui.click;

import me.parozzz.reflex.events.wrappers.wrappers.WrappedInventoryClickEvent;
import me.parozzz.reflex.items.NMSStackCompound;
import me.parozzz.reflex.items.custom.CustomItem;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

public class ClickEvent implements Cancellable
{
    protected final WrappedInventoryClickEvent wrapper;

    private boolean removeAction = false;
    private ClickAction newAction;

    public ClickEvent(final WrappedInventoryClickEvent wrapper)
    {
        this.wrapper = wrapper;
    }

    public Inventory getInventory()
    {
        return getEvent().getInventory();
    }

    public InventoryAction getAction()
    {
        return getEvent().getAction();
    }

    public ClickType getClickType()
    {
        return getEvent().getClick();
    }

    //+++++++++++++++++++++++++++++++++++++++++++++
    //============= CURRENT RELATED ===============
    //+++++++++++++++++++++++++++++++++++++++++++++

    public ItemStack getCurrentItem()
    {
        return getEvent().getCurrentItem();
    }

    public NMSStackCompound getCurrentStack()
    {
        return wrapper.getCurrentStack();
    }

    public CustomItem getCurrentCustomItem()
    {
        return wrapper.getCurrentCustomItem();
    }

    public @Nullable <T extends CustomItem> T getCurrentCustomItem(Class<T> itemClass)
    {
        return wrapper.getCurrentCustomItem(itemClass);
    }

    //+++++++++++++++++++++++++++++++++++++++++++++

    public int getSlot()
    {
        return getEvent().getSlot();
    }

    public HumanEntity getWhoClicked()
    {
        return getEvent().getWhoClicked();
    }

    public void setCurrentItem(final ItemStack itemStack)
    {
        getEvent().setCurrentItem(itemStack);
    }

    public void removeCurrentItem()
    {
        getEvent().setCurrentItem(new ItemStack(Material.AIR));
    }

    public void shouldRemoveAction(boolean removeAction)
    {
        this.removeAction = removeAction;
    }

    public boolean doesRemoveAction()
    {
        return removeAction;
    }

    public void updateClickAction(ClickAction newAction)
    {
        this.newAction = newAction;
    }

    public boolean hasUpdatedClickAction()
    {
        return newAction != null;
    }

    public ClickAction getUpdatedClickAction()
    {
        return newAction;
    }

    protected InventoryClickEvent getEvent()
    {
        return wrapper.getWrappedEvent();
    }

    @Override
    public boolean isCancelled()
    {
        return getEvent().isCancelled();
    }

    @Override
    public void setCancelled(boolean cancel)
    {
        getEvent().setCancelled(cancel);
    }
}
