/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.parozzz.reflex.events.wrappers;

import me.parozzz.reflex.events.wrappers.wrappers.WrappedInventoryClickEvent;
import me.parozzz.reflex.events.wrappers.wrappers.WrappedPlayerDeathEvent;
import me.parozzz.reflex.events.wrappers.wrappers.combat.PlayerDamageEntityEvent;
import me.parozzz.reflex.events.wrappers.wrappers.combat.PlayerKillEntityEvent;
import me.parozzz.reflex.events.wrappers.wrappers.combat.PlayerTakeDamageEvent;
import me.parozzz.reflex.events.wrappers.wrappers.playeritem.WrappedBlockPlaceEvent;
import me.parozzz.reflex.events.wrappers.wrappers.playeritem.WrappedPlayerFishEvent;
import me.parozzz.reflex.events.wrappers.wrappers.playeritem.WrappedPlayerInteractEvent;
import me.parozzz.reflex.events.wrappers.wrappers.playeritem.WrappedPlayerItemDamageEvent;
import me.parozzz.reflex.utilities.Util;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
/**
 * @author Paros
 */
//Cannot ignore cancelled event, since wrappers inherits the cancellable status and value, they are needed to be throws in case something needs it.
public class EventWrapperListener implements Listener
{
    @EventHandler(ignoreCancelled = false, priority = EventPriority.LOWEST)
    private void onInventoryClick(final InventoryClickEvent e)
    {
        Util.callEvent(new WrappedInventoryClickEvent(e));
    }

    @EventHandler(ignoreCancelled = false, priority = EventPriority.MONITOR)
    private void onEntityDamageByEntity(final EntityDamageByEntityEvent e)
    {
        if(e.getDamager().getType() == EntityType.PLAYER)
        {
            if(e.getEntityType() == EntityType.PLAYER)
            {
                Util.callEvent(new PlayerTakeDamageEvent(e));
            }

            Util.callEvent(new PlayerDamageEntityEvent(e));
        }
    }

    @EventHandler(ignoreCancelled = false, priority = EventPriority.LOWEST)
    private void onEntityDeath(final EntityDeathEvent e)
    {
        Player killer = e.getEntity().getKiller();
        if(killer != null)
        {
            Util.callEvent(new PlayerKillEntityEvent((EntityDamageByEntityEvent) e.getEntity().getLastDamageCause(), e, killer));
        }
    }

    @EventHandler(ignoreCancelled = false, priority = EventPriority.LOWEST)
    private void onFishing(final PlayerFishEvent e)
    {
        Util.callEvent(new WrappedPlayerFishEvent(e));
    }

    @EventHandler(ignoreCancelled = false, priority = EventPriority.LOWEST)
    private void onItemDamage(final PlayerItemDamageEvent e)
    {
        Util.callEvent(new WrappedPlayerItemDamageEvent(e));
    }

    @EventHandler(ignoreCancelled = false, priority = EventPriority.LOWEST)
    private void onPlayerInteract(final PlayerInteractEvent e)
    {
        Util.callEvent(new WrappedPlayerInteractEvent(e));
    }

    @EventHandler(ignoreCancelled = false, priority = EventPriority.LOWEST)
    private void onBlockPlace(final BlockPlaceEvent e)
    {
        Util.callEvent(new WrappedBlockPlaceEvent(e));
    }

    @EventHandler(ignoreCancelled = false, priority = EventPriority.LOWEST)
    private void onPlayerDeath(final PlayerDeathEvent e)
    {
        Util.callEvent(new WrappedPlayerDeathEvent(e));
    }
}
