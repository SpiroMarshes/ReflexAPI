package me.parozzz.reflex.tools.task.subtask;

import me.parozzz.reflex.tools.task.TaskChain;
import me.parozzz.reflex.tools.task.TaskManager;
import org.apache.commons.lang.Validate;

@SuppressWarnings("rawtypes")
public abstract class SubTask
{
    protected final TaskManager taskManager;
    protected volatile ChainSubTask chainSubTask;
    private volatile TaskChain chain;
    private boolean sync = true;
    private boolean nextSync = true;

    private boolean closed = false;

    public SubTask(TaskManager taskManager)
    {
        this.taskManager = taskManager;
    }

    public SubTask(TaskManager taskManager, final TaskChain chain)
    {
        this.taskManager = taskManager;
        this.chain = chain;
    }

    public final SubTask condition(final Condition condition)
    {
        notClosed();
        return shareSync(new ConditionalSubTask(taskManager, getMainChain(), condition));
    }

    public final SubTask conditionWithFail(final Condition condition, final boolean syncFail, final Runnable failRunnable)
    {
        notClosed();
        return shareSync(new ConditionalSubTask(taskManager, getMainChain(), condition)).onFail(syncFail, failRunnable);
    }

    public final SubTask run(final Runnable runnable)
    {
        notClosed();
        return shareSync(new RunnableSubTask(taskManager, getMainChain(), runnable));
    }

    public final SubTask delay(final int delay)
    {
        notClosed();
        return shareSync(new DelayedSubTask(taskManager, getMainChain(), delay));
    }

    private <T extends ChainSubTask> T shareSync(T task)
    {
        this.chainSubTask = task;
        task.setSync(nextSync);
        return task;
    }

    public final void execute()
    {
        notClosed();

        this.getMainChain().chainSubTask.run();
        closed = true;
    }

    public final SubTask sync()
    {
        notClosed();

        nextSync = true;
        return this;
    }

    public final SubTask async()
    {
        notClosed();

        nextSync = false;
        return this;
    }

    protected boolean isSync()
    {
        return sync;
    }

    protected void setSync(final boolean sync)
    {
        this.sync = sync;
        this.nextSync = sync;
    }

    protected final void postRunnable(boolean sync, Runnable run)
    {
        if(sync)
        {
            taskManager.sync(run);
        }
        else
        {
            taskManager.async(run);
        }
    }

    protected final void postRunnable(Runnable run)
    {
        postRunnable(sync, run);
    }

    private void notClosed()
    {
        Validate.isTrue(!closed, "Trying to access a closed chain.");
    }

    private TaskChain getMainChain()
    {
        return this instanceof TaskChain ? (TaskChain) this : chain;
    }

}
