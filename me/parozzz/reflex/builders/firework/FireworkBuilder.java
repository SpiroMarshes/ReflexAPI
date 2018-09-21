/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.parozzz.reflex.builders.firework;

import me.parozzz.reflex.tools.task.TaskManager;
import me.parozzz.reflex.utilities.TaskUtil;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Paros
 */
public class FireworkBuilder 
{
    public static final String FIREWORK_DATA ="ReflexAPI.FireworkNoDamage";

    private final JavaPlugin javaPlugin;

    private final FireworkEffect.Builder builder;
    private boolean noDamage;
    private int detonateTicks;

    public FireworkBuilder(JavaPlugin javaPlugin)
    {
        this.javaPlugin = javaPlugin;

        builder = FireworkEffect.builder();
    }

    public FireworkBuilder setNoDamage()
    {
        noDamage = true;
        return this;
    }

    public FireworkBuilder setDetonateTicks(int ticks)
    {
        this.detonateTicks = ticks;
        return this;
    }

    public FireworkBuilder addColor(final Color... colors)
    {
        builder.withColor(colors);
        return this;
    }

    public FireworkBuilder addFadeColor(final Color... colors)
    {
        builder.withFade(colors);
        return this;
    }

    public FireworkBuilder setType(final org.bukkit.FireworkEffect.Type t)
    {
        builder.with(t);
        return this;
    }

    private FireworkEffect effect;
    public void spawn(final Location l)
    {
        if(effect == null)
        {
            effect = builder.build();
        }
        
        Firework fw = (Firework)l.getWorld().spawnEntity(l, EntityType.FIREWORK);

        FireworkMeta meta = fw.getFireworkMeta();
        meta.addEffect(effect);
        fw.setFireworkMeta(meta);

        if(noDamage)
        {
            fw.setMetadata(FIREWORK_DATA, new FixedMetadataValue(JavaPlugin.getProvidingPlugin(FireworkBuilder.class), true));
        }

        TaskManager.getTaskManagerOf(javaPlugin).syncLater(() -> fw.detonate(), detonateTicks);
    }
}
