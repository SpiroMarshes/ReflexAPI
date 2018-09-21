package me.parozzz.reflex.builders.firework;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class FireworkBuilderListener implements Listener
{

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    private void onFireworkDamage(final EntityDamageByEntityEvent e)
    {
        e.setCancelled(e.getDamager().getType() == EntityType.FIREWORK && e.getDamager().hasMetadata(FireworkBuilder.FIREWORK_DATA));
    }
}
