package me.parozzz.reflex.serializable.json.enums;

import me.parozzz.reflex.serializable.json.JSONSerializable;
import org.json.simple.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class JSONEnumMap<E extends Enum, V extends JSONSerializable & JSONEnumKeyable<E>> extends ConcurrentHashMap<E, V> implements JSONSerializable
{
    private final Class<E> enumClass;

    public JSONEnumMap(final Class<E> enumClass)
    {
        this.enumClass = enumClass;
    }

    public abstract void put(final E key, final JSONObject json) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException;

    public final void add(final V v)
    {
        this.put(v.getKey(), v);
    }

    @SuppressWarnings("unchecked")
    @Override
    public JSONObject toJSON() throws IllegalArgumentException, IllegalAccessException
    {
        JSONObject json = new JSONObject();
        for(Entry<E, V> entry : this.entrySet())
        {
            json.put(entry.getKey().name(), entry.getValue().toJSON());
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
            Object value = entry.getValue();
            if(value instanceof JSONObject)
            {
                E enumKey = (E) Enum.valueOf(enumClass, entry.getKey().toString());
                put(enumKey, (JSONObject) value);
            }
        }
    }
}
