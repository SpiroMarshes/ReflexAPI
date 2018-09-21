package me.parozzz.reflex.utilities.general;

public class SimpleBoolean
{
    private boolean value;

    public SimpleBoolean()
    {

    }

    public SimpleBoolean(boolean value)
    {
        this.value = value;
    }

    public boolean invert()
    {
        value = !value;
        return value;
    }

    public void set(boolean value)
    {
        this.value = value;
    }

    public boolean get()
    {
        return value;
    }

}
