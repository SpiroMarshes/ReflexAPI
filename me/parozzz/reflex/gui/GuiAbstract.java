/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.parozzz.reflex.gui;

import com.google.common.collect.Sets;
import me.parozzz.reflex.events.wrappers.wrappers.WrappedInventoryClickEvent;
import me.parozzz.reflex.gui.click.ClickAction;
import me.parozzz.reflex.gui.click.ClickEvent;
import me.parozzz.reflex.gui.click.SwapClickEvent;
import me.parozzz.reflex.items.Itemable;
import me.parozzz.reflex.utilities.Util;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Paros
 */
public abstract class GuiAbstract
{
    private final Set<GuiProperty> propertySet;
    private final Map<Integer, ClickAction> slots;
    private final Inventory inventory;

    public GuiAbstract(final String title, final int rows, final GuiProperty... properties)
    {
        slots = new HashMap<>();

        propertySet = Sets.newEnumSet(Arrays.asList(properties), GuiProperty.class);
        inventory = Bukkit.createInventory(new GuiHolder(), (rows >= 6 ? 6 : rows) * 9, Util.cc(title));
    }

    public final boolean hasProperty(final GuiProperty property)
    {
        return propertySet != null && propertySet.contains(property);
    }

    /**
     * Add an ItemStack to the first empty slot. This method WON'T stack the item.
     *
     * @param itemStack The ItemStack to add.
     * @param action    The action to execute on click.
     * @return The slot in which has been added, -1 if there wasn't space.
     */
    public final int addItemStack(final ItemStack itemStack, final ClickAction action)
    {
        int firstEmpty = inventory.firstEmpty();
        if(firstEmpty == -1)
        {
            return firstEmpty;
        }

        if(action != null)
        {
            slots.put(firstEmpty, action);
        }

        inventory.setItem(firstEmpty, itemStack);
        return firstEmpty;
    }

    /**
     * Add an ItemStack to the first empty slot. This method WON'T stack the item.
     *
     * @param itemStack The ItemStack to add.
     * @return The slot in which has been added, -1 if there wasn't space.
     */
    public final int addItemStack(final ItemStack itemStack)
    {
        return addItemStack(itemStack, null);
    }

    /**
     * Add an Itemable to the first empty slot. This method WON'T stack the item.
     *
     * @param itemable The itemable to add.
     * @return The slot in which has been added, -1 if there wasn't space.
     */
    public final int addItemable(final Itemable itemable)
    {
        return addItemStack(itemable.getItemStack());
    }

    /**
     * Add an Itemable to the first empty slot. This method WON'T stack the item.
     *
     * @param itemable The itemable to add.
     * @param action   The action to execute on click.
     * @return The slot in which has been added, -1 if there wasn't space.
     */
    public final int addItemable(final Itemable itemable, final ClickAction action)
    {
        return addItemStack(itemable.getItemStack(), action);
    }

    /**
     * Set an ItemStack to the selected slot.
     *
     * @param itemStack The ItemStack to add.
     * @param action    The action to execute on click.
     * @return The old ItemStack in the select slot, {@code NULL} otherwise
     */
    public final @Nullable
    ItemStack setItemStack(final int slot, final ItemStack itemStack, final ClickAction action)
    {
        Validate.isTrue(slot >= 0 && slot < inventory.getSize(), "The slot is outside the inventory size. Slot: " + slot + ". Size: " + inventory.getSize());

        ItemStack oldItemStack = inventory.getItem(slot);
        oldItemStack = oldItemStack != null && oldItemStack.getType() == Material.AIR ? null : oldItemStack;

        if(action != null)
        {
            slots.put(slot, action);
        }
        inventory.setItem(slot, itemStack);

        return oldItemStack;
    }

    /**
     * Set an ItemStack to the selected slot.
     *
     * @param itemStack The ItemStack to add.
     * @return The old ItemStack in the select slot, {@code NULL} otherwise
     */
    public final @Nullable
    ItemStack setItemStack(final int slot, final ItemStack itemStack)
    {
        return setItemStack(slot, itemStack, null);
    }

    /**
     * Set an Itemable to the selected slot.
     *
     * @param itemable The Itemable to add.
     * @return The old ItemStack in the select slot, {@code NULL} otherwise
     */
    public final @Nullable
    ItemStack setItemable(final int slot, final Itemable itemable)
    {
        return setItemStack(slot, itemable.getItemStack());
    }

    /**
     * Set an Itemable to the selected slot.
     *
     * @param itemable The Itemable to add.
     * @param action   The action to execute on click.
     * @return The old ItemStack in the select slot, {@code NULL} otherwise
     */
    public final @Nullable
    ItemStack setItemable(final int slot, final Itemable itemable, final ClickAction action)
    {
        return setItemStack(slot, itemable.getItemStack(), action);
    }

    protected final void onClick(final WrappedInventoryClickEvent e)
    {
        InventoryClickEvent wrapped = e.getWrappedEvent();

        int slot = wrapped.getSlot();

        ClickAction action = slots.get(slot);
        if(action == null)
        {
            return;
        }

        ClickEvent clickEvent = wrapped.getAction() == InventoryAction.SWAP_WITH_CURSOR ? new SwapClickEvent(e) : new ClickEvent(e);
        action.onClick(clickEvent);

        if(clickEvent.doesRemoveAction())
        {
            slots.remove(slot);
        }
        else if(clickEvent.hasUpdatedClickAction())
        {
            slots.put(slot, clickEvent.getUpdatedClickAction());
        }
    }

    public final Inventory getInventory()
    {
        return inventory;
    }

    public final void open(final HumanEntity he)
    {
        he.openInventory(inventory);
    }

    public final class GuiHolder implements InventoryHolder
    {
        public GuiAbstract getGUI()
        {
            return GuiAbstract.this;
        }

        @Override
        public Inventory getInventory()
        {
            return inventory;
        }
    }
}
