package me.parozzz.reflex.database.json;

import me.parozzz.reflex.database.DatabaseCore;
import me.parozzz.reflex.serializable.json.JSONKeyable;
import me.parozzz.reflex.serializable.json.JSONSerializable;
import me.parozzz.reflex.utilities.general.SimpleBoolean;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class SimpleJSONSQLTable<T extends JSONSerializable & JSONKeyable> extends JSONSQLTable<T>
{
    public SimpleJSONSQLTable(DatabaseCore core, String name)
    {
        super(core, name);

        this.executeUpdate("CREATE TABLE IF NOT EXISTS " + name + " (id TEXT UNIQUE, data TEXT, PRIMARY KEY(id));");
    }

    @Override
    protected String getUpdateSQL()
    {
        return "INSERT OR REPLACE INTO " + getName() + " (id, data) VALUES(?,?);";
    }

    @Override
    protected void populateUpdateStatement(T serializable, PreparedStatement statement) throws SQLException, IllegalAccessException
    {
        statement.setString(1, serializable.getJSONKey());
        statement.setString(2, serializable.toJSON().toJSONString());
    }

    @Override
    protected String getDeleteSQL()
    {
        return "DELETE FROM " + getName() + " WHERE id = ?;";
    }

    @Override
    protected void populateDeleteStatement(T serializable, PreparedStatement statement) throws SQLException
    {
        statement.setString(1, serializable.getJSONKey());
    }

    @Override
    public boolean loadData(final String key, final T serializable)
    {
        SimpleBoolean loaded = new SimpleBoolean(false);
        this.executeQuery("SELECT data FROM " + getName() + " WHERE id = ?;", stm -> stm.setString(1, key), set ->
        {
            if(set.next())
            {
                String data = set.getString("data");

                try
                {
                    Object obj = new JSONParser().parse(data);
                    if(obj instanceof JSONObject)
                    {
                        serializable.loadJSON((JSONObject) obj);
                        loaded.set(true);
                    }
                }
                catch(ParseException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e)
                {
                    e.printStackTrace();
                }

            }
        });
        return loaded.get();
    }
}
