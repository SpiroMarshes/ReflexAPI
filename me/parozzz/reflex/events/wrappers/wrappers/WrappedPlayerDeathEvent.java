package me.parozzz.reflex.events.wrappers.wrappers;

import me.parozzz.reflex.events.wrappers.PlayerEventWrapper;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

public class WrappedPlayerDeathEvent extends PlayerEventWrapper<PlayerDeathEvent>
{
    public WrappedPlayerDeathEvent(PlayerDeathEvent wrapped)
    {
        super(wrapped);
    }

    @Override
    public Player getPlayer()
    {
        return getWrappedEvent().getEntity();
    }
}
