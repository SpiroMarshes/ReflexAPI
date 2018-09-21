package me.parozzz.reflex.tools.task.subtask;

import me.parozzz.reflex.tools.task.TaskChain;
import me.parozzz.reflex.tools.task.TaskManager;

@SuppressWarnings("rawtypes")
class DelayedSubTask extends ChainSubTask
{
    private final long tickDelay;

    public DelayedSubTask(final TaskManager taskManager, final TaskChain chain, final long tickDelay)
    {
        super(taskManager, chain);

        this.tickDelay = tickDelay;
    }

    @Override
    public void run()
    {
        if(chainSubTask == null)
        {
            return;
        }

        if(isSync())
        {
            taskManager.syncLater(chainSubTask, tickDelay);
        }
        else
        {
            taskManager.asyncLater(chainSubTask, tickDelay);
        }
    }

}
