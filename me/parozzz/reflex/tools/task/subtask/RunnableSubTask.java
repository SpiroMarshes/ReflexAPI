package me.parozzz.reflex.tools.task.subtask;

import me.parozzz.reflex.tools.task.TaskChain;
import me.parozzz.reflex.tools.task.TaskManager;

@SuppressWarnings("rawtypes")
class RunnableSubTask extends ChainSubTask
{
    private final Runnable runnable;

    public RunnableSubTask(TaskManager taskManager, TaskChain chain, Runnable runnable)
    {
        super(taskManager, chain);

        this.runnable = runnable;
    }

    @Override
    public void run()
    {
        super.postRunnable(() ->
        {
            runnable.run();

            if(chainSubTask != null)
            {
                chainSubTask.run();
            }
        });
    }

}
