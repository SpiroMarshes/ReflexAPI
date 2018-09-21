package me.parozzz.reflex.tools.task;

import me.parozzz.reflex.tools.task.subtask.SubTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class TaskChain<V> extends SubTask
{
    private Map<String, V> sharedMap;
    private List<V> sharedList;
    private V value;

    protected TaskChain(TaskManager manager)
    {
        super(manager);
    }

    public Map<String, V> getSharedMap()
    {
        return sharedMap == null ? (sharedMap = new HashMap<>()) : sharedMap;
    }

    public List<V> getSharedList()
    {
        return sharedList == null ? (sharedList = new ArrayList<>()) : sharedList;
    }

    public boolean isValueNotNull()
    {
        return value != null;
    }

    public V getValue()
    {
        return value;
    }

    public V setValue(final V value)
    {
        this.value = value;
        return value;
    }

}
