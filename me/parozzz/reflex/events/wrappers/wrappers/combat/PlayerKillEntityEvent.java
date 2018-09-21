/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.parozzz.reflex.events.wrappers.wrappers.combat;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * @author Paros
 */
public class PlayerKillEntityEvent extends PlayerCombatEvent<EntityDamageByEntityEvent>
{
    private final EntityDeathEvent deathEvent;
    public PlayerKillEntityEvent(EntityDamageByEntityEvent wrapped, EntityDeathEvent deathEvent, Player damager)
    {
        super(wrapped, damager);

        this.deathEvent = deathEvent;
    }

    public EntityDeathEvent getWrappedDeathEvent()
    {
        return deathEvent;
    }
}
