/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.parozzz.reflex.events.wrappers;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @param <T>
 * @author Paros
 */
public abstract class EventWrapper<T extends Event> extends Event
{
    private final static HandlerList handlers = new HandlerList();

    public static HandlerList getHandlerList()
    {
        return EventWrapper.handlers;
    }

    private final T wrapped;

    public EventWrapper(T wrapped)
    {
        this.wrapped = wrapped;
    }

    @Override
    public HandlerList getHandlers()
    {
        return EventWrapper.handlers;
    }

    public final T getWrappedEvent()
    {
        return wrapped;
    }

}
