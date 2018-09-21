/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.parozzz.reflex.gui;

import me.parozzz.reflex.events.wrappers.wrappers.WrappedInventoryClickEvent;
import me.parozzz.reflex.gui.types.IBottomEvent;
import me.parozzz.reflex.gui.types.ICloseEvent;
import me.parozzz.reflex.gui.types.IDragEvent;
import me.parozzz.reflex.gui.types.IOpenEvent;
import me.parozzz.reflex.utilities.ItemUtil;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;

/**
 * @author Paros
 */
public class GuiListener implements Listener
{
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    private void onInventoryOpen(final InventoryOpenEvent e)
    {
        if(e.getInventory().getHolder() instanceof GuiAbstract.GuiHolder)
        {
            GuiAbstract.GuiHolder holder = (GuiAbstract.GuiHolder) e.getInventory().getHolder();

            if(holder.getGUI() instanceof IOpenEvent)
            {
                ((IOpenEvent) holder.getGUI()).onOpen(e);
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    private void onInventoryClose(final InventoryCloseEvent e)
    {
        if(e.getInventory().getHolder() instanceof GuiAbstract.GuiHolder)
        {
            GuiAbstract.GuiHolder holder = (GuiAbstract.GuiHolder) e.getInventory().getHolder();

            if(holder.getGUI() instanceof ICloseEvent)
            {
                ((ICloseEvent) holder.getGUI()).onClose(e);
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    private void onInventoryClick(final WrappedInventoryClickEvent wrapper)
    {
        InventoryClickEvent e = wrapper.getWrappedEvent();

        if(e.getClickedInventory() != null && e.getInventory().getHolder() instanceof GuiAbstract.GuiHolder)
        {
            GuiAbstract gui = ((GuiAbstract.GuiHolder) e.getInventory().getHolder()).getGUI();

            e.setCancelled(gui.hasProperty(GuiProperty.ALWAYS_CANCELLED));

            if(gui.hasProperty(GuiProperty.CURRENT_NOT_AIR) && !ItemUtil.nonAir(e.getCurrentItem()))
            {
                e.setCancelled(true);
                return;
            }

            if(e.getInventory().equals(e.getClickedInventory()))
            {
                if(!e.isCancelled()) //Only if is not cancelled, otherwise might reverse a behavior
                {
                    e.setCancelled(gui.hasProperty(GuiProperty.CANCEL_IF_SAME_INVENTORY)); //Cancel if the action happend in the same inventory.
                }

                gui.onClick(wrapper);
            }
            else
            {
                if(e.getClick() == ClickType.SHIFT_LEFT || e.getClick() == ClickType.SHIFT_RIGHT)
                {
                    e.setCancelled(gui.hasProperty(GuiProperty.CANCEL_IF_SAME_INVENTORY)); //Cancel if the item is sent in the same inventory (Similar, am i right?)
                }

                if(gui instanceof IBottomEvent)
                {
                    ((IBottomEvent) gui).onBottomClick(e);
                }
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    private void onInventoryDrag(final InventoryDragEvent e)
    {
        if(e.getInventory().getHolder() instanceof GuiAbstract.GuiHolder)
        {
            GuiAbstract gui = ((GuiAbstract.GuiHolder) e.getInventory().getHolder()).getGUI();
            e.setCancelled(gui.hasProperty(GuiProperty.ALWAYS_CANCELLED) || gui.hasProperty(GuiProperty.CANCEL_IF_SAME_INVENTORY));

            if(gui instanceof IDragEvent)
            {
                ((IDragEvent) gui).onDrag(e);
            }
        }
    }
}
