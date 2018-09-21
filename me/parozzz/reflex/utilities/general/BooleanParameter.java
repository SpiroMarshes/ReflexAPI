package me.parozzz.reflex.utilities.general;

import javax.annotation.Nullable;

public class BooleanParameter<T>
{
    private T trueValue;
    private T falseValue;

    public BooleanParameter(final T paramTrue, final T paramFalse)
    {
        trueValue = paramTrue;
        falseValue = paramFalse;
    }

    public BooleanParameter()
    {
    }

    public BooleanParameter<T> setTrue(final T value)
    {
        trueValue = value;
        return this;
    }

    public BooleanParameter<T> setFalse(final T value)
    {
        falseValue = value;
        return this;
    }

    public BooleanParameter<T> set(final T value, final boolean cond)
    {
        if(cond)
        {
            trueValue = value;
        }
        else
        {
            falseValue = value;
        }
        return this;
    }

    public @Nullable
    T getValue(final boolean paramBool)
    {
        return paramBool ? trueValue : falseValue;
    }
}
