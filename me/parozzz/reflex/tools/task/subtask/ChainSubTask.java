package me.parozzz.reflex.tools.task.subtask;

import me.parozzz.reflex.tools.task.TaskChain;
import me.parozzz.reflex.tools.task.TaskManager;

@SuppressWarnings("rawtypes")
abstract class ChainSubTask extends SubTask implements Runnable
{
    public ChainSubTask(TaskManager taskManager, TaskChain chain)
    {
        super(taskManager, chain);
    }

}
