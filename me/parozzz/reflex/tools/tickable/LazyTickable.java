package me.parozzz.reflex.tools.tickable;

public final class LazyTickable
{
    private final int tickPeriod;
    private final Runnable run;

    private int counter = 0;

    public LazyTickable(final int tickPeriod, final Runnable run)
    {
        this.tickPeriod = tickPeriod;
        this.run = run;
    }

    public void resetCounter()
    {
        counter = 0;
    }

    public void tick()
    {
        if(counter++ >= tickPeriod)
        {
            run.run();
            counter = 0;
        }
    }
}
