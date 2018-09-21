/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.parozzz.reflex.utilities.general;

import javax.annotation.Nullable;

/**
 * @param <T>
 * @author Paros
 */
public abstract class CachedValue<T>
{
    private T value;

    protected final T setValue(final T value)
    {
        this.value = value;
        return value;
    }

    protected final T getValue()
    {
        return value;
    }

    public void clearCached()
    {
        value = null;
    }

    public final boolean hasCached()
    {
        return value != null;
    }

    public final @Nullable
    T getCached()
    {
        return value == null ? (value = saveCached()) : value;
    }

    public abstract T saveCached();
}
