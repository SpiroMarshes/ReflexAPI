package me.parozzz.reflex.serializable.json;

import me.parozzz.reflex.serializable.json.JSONSerializable;
import org.json.simple.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class JSONMap<V extends JSONSerializable & JSONKeyable> extends ConcurrentHashMap<String, V> implements JSONSerializable
{
    public abstract void put(final String key, final JSONObject json) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException;

    public final void add(final V v)
    {
        this.put(v.getJSONKey(), v);
    }

    @SuppressWarnings("unchecked")
    @Override
    public JSONObject toJSON() throws IllegalArgumentException, IllegalAccessException
    {
        JSONObject json = new JSONObject();
        for(Entry<String, V> entry : this.entrySet())
        {
            Object value = entry.getValue();
            if(value instanceof JSONSerializable)
            {
                json.put(entry.getKey(), ((JSONSerializable) value).toJSON());
            }
        }
        return json;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void loadJSON(JSONObject json) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException
    {
        Map<Object, Object> map = json;
        for(Entry<Object, Object> entry : map.entrySet())
        {
            String key = entry.getKey().toString();

            Object value = entry.getValue();
            if(value instanceof JSONObject)
            {
                put(key, (JSONObject) value);
            }
        }
    }
}
