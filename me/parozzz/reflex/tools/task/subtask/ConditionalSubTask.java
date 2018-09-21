package me.parozzz.reflex.tools.task.subtask;

import me.parozzz.reflex.tools.task.TaskChain;
import me.parozzz.reflex.tools.task.TaskManager;

@SuppressWarnings("rawtypes")
class ConditionalSubTask extends ChainSubTask
{
    private final Condition condition;
    private Runnable failRunnable;
    private boolean failSynced = false;

    public ConditionalSubTask(final TaskManager taskManager, TaskChain chain, final Condition condition)
    {
        super(taskManager, chain);

        this.condition = condition;
    }

    public ConditionalSubTask onFail(final boolean sync, final Runnable run)
    {
        this.failRunnable = run;
        this.failSynced = sync;
        return this;
    }

    @Override
    public void run()
    {
        postRunnable(() ->
        {
            if(chainSubTask != null && condition.test())
            {
                chainSubTask.run();
            }
            else if(failRunnable != null)
            {
                postRunnable(failSynced, failRunnable);
            }
        });
    }
}
