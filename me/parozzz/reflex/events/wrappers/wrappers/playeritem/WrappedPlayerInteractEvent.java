/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.parozzz.reflex.events.wrappers.wrappers.playeritem;

import me.parozzz.reflex.events.wrappers.PlayerItemEventWrapper;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * @author Paros
 */
public class WrappedPlayerInteractEvent extends PlayerItemEventWrapper<PlayerInteractEvent> implements Cancellable
{
    public WrappedPlayerInteractEvent(PlayerInteractEvent wrapped)
    {
        super(wrapped, wrapped.getItem());
    }

    @Override
    public Player getPlayer()
    {
        return getWrappedEvent().getPlayer();
    }

    public boolean isLeftClickValid()
    {
        Action action = getWrappedEvent().getAction();
        return (action == Action.LEFT_CLICK_BLOCK && !isCancelled()) || action == Action.LEFT_CLICK_AIR;
    }

    public boolean isRightClickValid()
    {
        Action action = getWrappedEvent().getAction();
        return (action == Action.RIGHT_CLICK_BLOCK && !isCancelled()) || action == Action.RIGHT_CLICK_AIR;
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
