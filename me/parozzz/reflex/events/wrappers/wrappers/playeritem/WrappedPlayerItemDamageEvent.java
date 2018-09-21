/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.parozzz.reflex.events.wrappers.wrappers.playeritem;

import me.parozzz.reflex.events.wrappers.PlayerItemEventWrapper;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.player.PlayerItemDamageEvent;

/**
 * @author Paros
 */
public class WrappedPlayerItemDamageEvent extends PlayerItemEventWrapper<PlayerItemDamageEvent> implements Cancellable
{
    public WrappedPlayerItemDamageEvent(PlayerItemDamageEvent wrapped)
    {
        super(wrapped, wrapped.getItem());
    }

    @Override
    public Player getPlayer()
    {
        return getWrappedEvent().getPlayer();
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
