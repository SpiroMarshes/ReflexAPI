/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.parozzz.reflex.events.wrappers.wrappers.combat;

import me.parozzz.reflex.items.NMSStackCompound;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.entity.EntityDamageEvent;

import javax.annotation.Nullable;

/**
 * @author Paros
 */
public class PlayerTakeDamageEvent extends PlayerCombatEvent<EntityDamageEvent> implements Iterable<NMSStackCompound>, Cancellable
{
    public PlayerTakeDamageEvent(final EntityDamageEvent wrapped)
    {
        super(wrapped, (Player) wrapped.getEntity());
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
