package me.parozzz.reflex.events.wrappers;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public abstract class PlayerEventWrapper<T extends Event> extends EventWrapper<T>
{
    public PlayerEventWrapper(T wrapped)
    {
        super(wrapped);
    }

    public abstract Player getPlayer();

    public World getWorld()
    {
        return getPlayer().getWorld();
    }
}
