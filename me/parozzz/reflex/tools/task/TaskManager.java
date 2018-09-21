package me.parozzz.reflex.tools.task;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.WeakHashMap;

public final class TaskManager
{
    private final static Map<JavaPlugin, TaskManager> taskManagerMap = new WeakHashMap<>();
    public static TaskManager getTaskManagerOf(JavaPlugin javaPlugin)
    {
        return taskManagerMap.computeIfAbsent(javaPlugin, TaskManager::new);
    }

    private final JavaPlugin javaPlugin;

    private TaskManager(JavaPlugin javaPlugin)
    {
        this.javaPlugin = javaPlugin;
    }

    public <T> TaskChain<T> newChain(final Class<T> clazz)
    {
        return new TaskChain<>(this);
    }

    public void sync(final Runnable run)
    {
        Bukkit.getScheduler().runTask(javaPlugin, run);
    }

    public void syncLater(final Runnable run, long delay)
    {
        Bukkit.getScheduler().runTaskLater(javaPlugin, run, delay);
    }

    /**
     * Create a Timer with the defined parameters
     *
     * @param predicate The predicate to execute. If return {@code True}, the timer is cancelled
     * @param period    The period of this timer
     * @param delay     The delay of the first execution of this timer
     */
    public void syncTimed(final SimplePredicate predicate, long period, long delay)
    {
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                if(predicate.test())
                {
                    this.cancel();
                }
            }
        }.runTaskTimer(javaPlugin, delay, period);
    }

    /**
     * {@link me.parozzz.reflex.tools.task.TaskManager#syncTimed(SimplePredicate, long, long)}
     */
    public void syncTimed(final SimplePredicate predicate, long period)
    {
        this.syncTimed(predicate, period, 0L);
    }

    public void async(final Runnable run)
    {
        Bukkit.getScheduler().runTaskAsynchronously(javaPlugin, run);
    }

    public void asyncLater(final Runnable run, long delay)
    {
        Bukkit.getScheduler().runTaskLaterAsynchronously(javaPlugin, run, delay);
    }

    /**
     * Create a Timer that is executed in another Thread with defined parameters
     * {@link me.parozzz.reflex.tools.task.TaskManager#syncTimed(SimplePredicate, long, long)}
     */
    public void asyncTimed(final SimplePredicate predicate, long period, long delay)
    {
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                if(predicate.test())
                {
                    this.cancel();
                }
            }
        }.runTaskTimerAsynchronously(javaPlugin, delay, period);
    }

    /**
     * Create a Timer that is executed in another Thread with defined parameters
     * {@link me.parozzz.reflex.tools.task.TaskManager#asyncTimed(SimplePredicate, long, long)}
     */
    public void asyncTimed(final SimplePredicate predicate, long period)
    {
        this.asyncTimed(predicate, period, 0L);
    }

    @FunctionalInterface
    public interface SimplePredicate
    {
        boolean test();
    }
}
