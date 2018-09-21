/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.parozzz.reflex.events.wrappers.wrappers.combat;

import me.parozzz.reflex.events.wrappers.wrappers.combat.PlayerCombatEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import javax.annotation.Nullable;

/**
 * @author Paros
 */
public class PlayerDamageEntityEvent extends PlayerCombatEvent<EntityDamageByEntityEvent> implements Cancellable
{

    public PlayerDamageEntityEvent(final EntityDamageByEntityEvent wrapped)
    {
        super(wrapped, (Player) wrapped.getDamager());
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
