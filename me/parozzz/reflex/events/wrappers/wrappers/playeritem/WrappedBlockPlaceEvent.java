package me.parozzz.reflex.events.wrappers.wrappers.playeritem;

import me.parozzz.reflex.events.wrappers.PlayerItemEventWrapper;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.block.BlockPlaceEvent;

public class WrappedBlockPlaceEvent extends PlayerItemEventWrapper<BlockPlaceEvent> implements Cancellable
{
    public WrappedBlockPlaceEvent(BlockPlaceEvent wrapped)
    {
        super(wrapped, wrapped.getItemInHand());
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
