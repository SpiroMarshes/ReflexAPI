/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.parozzz.reflex;

import me.parozzz.reflex.builders.firework.FireworkBuilderListener;
import me.parozzz.reflex.events.ArmorHandler;
import me.parozzz.reflex.events.wrappers.EventWrapperListener;
import me.parozzz.reflex.gui.GuiListener;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * @author Paros
 */
public class ReflexAPI extends JavaPlugin
{
    public enum Property
    {
        ARMOREVENTS_LISTENER, GUI_LISTENER, FIREWORK_BUILDER_LISTENER, WRAPPED_EVENTS;
    }

    public static ReflexAPI getAPI()
    {
        return JavaPlugin.getPlugin(ReflexAPI.class);
    }

    private static final Logger logger = Logger.getLogger(ReflexAPI.class.getSimpleName());

    public static Logger logger()
    {
        return logger;
    }

    private Set<Property> propertySet;

    @Override
    public void onEnable()
    {
        propertySet = new HashSet<>();
    }

    @Override
    public void onDisable()
    {
        Bukkit.getScheduler().cancelTasks(this);
        HandlerList.unregisterAll(this);
    }

    public void addProperty(final Property... properties)
    {
        Stream.of(properties).filter(propertySet::add).forEach(property ->
        {
            try
            {
                switch(property)
                {
                    case WRAPPED_EVENTS:
                        registerListener(new EventWrapperListener());
                        break;
                    case ARMOREVENTS_LISTENER:
                        registerListener(new ArmorHandler());
                        break;
                    case GUI_LISTENER:
                        registerListener(new GuiListener());
                        break;
                    case FIREWORK_BUILDER_LISTENER:
                        registerListener(new FireworkBuilderListener());
                        break;
                }
            }
            catch(final Exception ex)
            {
                Logger.getLogger(ReflexAPI.class.getSimpleName()).log(Level.SEVERE, "An error trying to initialize a Listener from a property.", ex);
            }

        });
    }

    private void registerListener(Listener listener)
    {
        Bukkit.getPluginManager().registerEvents(listener, this);
    }
}
