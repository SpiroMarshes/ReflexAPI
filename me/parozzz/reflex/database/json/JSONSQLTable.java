package me.parozzz.reflex.database.json;

import me.parozzz.reflex.ReflexAPI;
import me.parozzz.reflex.database.DatabaseCore;
import me.parozzz.reflex.database.SQLTable;
import me.parozzz.reflex.serializable.json.JSONSerializable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public abstract class JSONSQLTable<T extends JSONSerializable> extends SQLTable
{
    private final Set<T> watchedObjectSet;
    private final Set<T> forceUpdateSet;
    private final Set<T> deleteObjectSet;

    public JSONSQLTable(DatabaseCore core, String name)
    {
        super(core, name);

        watchedObjectSet = ConcurrentHashMap.newKeySet();
        forceUpdateSet = ConcurrentHashMap.newKeySet();
        deleteObjectSet = ConcurrentHashMap.newKeySet();
    }

    public boolean startWatching(T serializable)
    {
        if(watchedObjectSet.add(serializable))
        {
            deleteObjectSet.remove(serializable);
            return true;
        }
        return true;
    }

    public boolean stopWatching(T serializable)
    {
        return watchedObjectSet.remove(serializable) && forceUpdateSet.add(serializable);
    }

    public boolean deleteWatched(T serializable)
    {
        return watchedObjectSet.remove(serializable) && deleteObjectSet.add(serializable);
    }

    public void refreshData()
    {
        try
        {
            PopulateConsumer<T> updateConsumer = this::populateUpdateStatement;
            String updateSQL = this.getUpdateSQL();

            this.updateSet(watchedObjectSet, updateSQL, updateConsumer, false);
            this.updateSet(forceUpdateSet, updateSQL, updateConsumer, true);

            PopulateConsumer<T> deleteConsumer = this::populateDeleteStatement;
            String deleteSQL = this.getDeleteSQL();

            this.updateSet(deleteObjectSet, deleteSQL, deleteConsumer, true);
        }
        catch(SQLException | IllegalAccessException ex)
        {
            ex.printStackTrace();
        }
    }

    private void updateSet(final Set<T> set, final String sql, final PopulateConsumer populateConsumer, final boolean deleteAll) throws SQLException, IllegalAccessException
    {
        try(Connection con = super.getConnection(); PreparedStatement stm = con.prepareStatement(sql))
        {
            Iterator<T> it = set.iterator();
            while(it.hasNext())
            {
                populateConsumer.populate(it.next(), stm);
                stm.addBatch();

                if(deleteAll)
                {
                    it.remove();
                }
            }

            stm.executeBatch();
        }
        catch(final SQLException | IllegalAccessException ex)
        {
            ReflexAPI.logger().warning("An object failed to be serialized during a JSONSQLTable update.");
            ex.printStackTrace();
        }
    }

    protected abstract String getUpdateSQL();

    protected abstract void populateUpdateStatement(T serializable, PreparedStatement statement) throws SQLException, IllegalAccessException;

    protected abstract String getDeleteSQL();

    protected abstract void populateDeleteStatement(T serializable, PreparedStatement statement) throws SQLException, IllegalAccessException;

    /*
    protected abstract void updateData(final T serializable) throws IllegalAccessException;

    protected abstract void deleteData(final T serializable);
    */
    public abstract boolean loadData(final String key, final T serializable);

    @FunctionalInterface
    private interface PopulateConsumer<T>
    {
        void populate(T t, PreparedStatement stm) throws SQLException, IllegalAccessException;
    }


}

        
        /*
        TaskChain<JSONEntry> chain = taskManager.newChain(JSONEntry.class);
        SubTask lastSubTask = chain.sync();
        for(List<T> list : CollectionUtil.splitCollection(watchedObjectSet, watchedObjectSet.size() / 10))
        {
            lastSubTask = lastSubTask.run(() ->
            {
                for(JSONSerializable sr : list)
                {
                    try
                    {
                        chain.getSharedList()
                            .add(new JSONEntry(sr));
                    }
                    catch(IllegalAccessException e)
                    {
                        Core.logger()
                            .warning("An object failed to be serialized during a JSONSQLTable update.");
                        e.printStackTrace();
                    }
                }
            }).delay(3);
        }
        
        lastSubTask.async().run(() -> chain.getSharedList().forEach(this::updateData))
            .condition(() -> forceUpdateSet.isEmpty())
            .sync().run(() ->
        {
            chain.getSharedList().clear();
            
            Iterator<T> it = forceUpdateSet.iterator();
            while(it.hasNext())
            {
                try
                {
                    chain.getSharedList().add(new JSONEntry(it.next()));
                    it.remove();
                }
                catch(IllegalAccessException e)
                {
                    Core.logger().warning("An object failed to be serialized during a JSONSQLTable update.");
                    e.printStackTrace();
                }
            }
        }).async().run(() -> chain.getSharedList().forEach(this::updateData));
        
        if(!forceUpdateSet.isEmpty())
        {
            lastSubTask = lastSubTask.sync().run(() ->
            {
                Iterator<T> it = forceUpdateSet.iterator();
                while(it.hasNext())
                {
                    try
                    {
                        chain.getSharedList()
                            .add(new JSONEntry(it.next()));
                        it.remove();
                    }
                    catch(IllegalAccessException e)
                    {
                        Core.logger()
                            .warning("An object failed to be serialized during a JSONSQLTable update.");
                        e.printStackTrace();
                    }
                }
            })
                              .async()
                              .run(() -> chain.getSharedList()
                                             .forEach(this::updateData));
        }
        
        lastSubTask.execute();
        
        
    private class JSONEntry
    {
        private final String jsonKey;
        private final JSONObject json;
        
        private JSONEntry(JSONSerializable sr) throws IllegalAccessException
        {
            this.jsonKey = sr.getJSONKey();
            this.json = sr.toJSON();
        }
        
        private JSONEntry(final String jsonKey, final JSONObject json)
        {
            this.jsonKey = jsonKey;
            this.json = json;
        }
    }*/
