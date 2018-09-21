package me.parozzz.reflex.serializable.json;

import com.google.common.collect.Lists;
import me.parozzz.reflex.serializable.SerializableField;
import me.parozzz.reflex.serializable.json.special.SerializationRule;
import me.parozzz.reflex.serializable.json.special.SpecialSerializableClass;
import org.json.simple.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.logging.Logger;

public interface JSONSerializable
{
    static List<Field> getValidFields(Class<? extends JSONSerializable> clazz)
    {
        List<Field> list = Lists.newArrayList();

        for(Field field : clazz.getDeclaredFields())
        {
            if(field.isAnnotationPresent(SerializableField.class))
            {
                int fieldModifiers = field.getModifiers();
                if(!Modifier.isVolatile(fieldModifiers) && !Modifier.isFinal(fieldModifiers))
                {
                    Logger.getLogger(clazz.getSimpleName()).warning("Found a SerializableField that is not volatile or final.");
                    continue;
                }

                field.setAccessible(true);
                list.add(field);
            }
        }

        return list;
    }

    static boolean invokeSetter(SerializableField an, JSONSerializable serializable, Object data)
            throws InvocationTargetException, IllegalAccessException, NoSuchMethodException
    {
        String methodName = an.deserializeMethod();
        if(methodName != null && !methodName.isEmpty())
        {
            Method setter = serializable.getClass().getDeclaredMethod(methodName, data.getClass());
            if(setter != null)
            {
                setter.invoke(serializable, data);
            }
            else
            {
                Logger.getLogger(serializable.getClass().getSimpleName()).warning(
                        "The setter method named " + methodName + " with a single parameter with class " + data.getClass().getSimpleName() + " was not found.");
            }
            return true;
        }
        return false;
    }

    static void setOrInvokeSetter(SerializableField an, JSONSerializable serializable, Field field, Object data)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException
    {
        if(invokeSetter(an, serializable, data))
        {
            return;
        }
        else
        {
            try
            {
                field.set(serializable, data);
            }
            catch(IllegalAccessException ex)
            {
                Logger.getLogger(serializable.getClass().getSimpleName())
                        .warning("Trying to deserialize a field with class " + field.getType().getSimpleName() + ", in class " + serializable.getClass().getSimpleName() +
                                ", but something went wrong.");
            }
        }
    }

    @SuppressWarnings("unchecked")
    default JSONObject toJSON() throws IllegalArgumentException, IllegalAccessException
    {
        JSONObject json = new JSONObject();
        for(Field field : getValidFields(getClass()))
        {
            Object obj = field.get(this);
            if(obj == null)
            {
                continue;
            }

            SerializationRule specialRule = SpecialSerializableClass.getJSONRule(field.getType());
            if(specialRule != null)
            {
                obj = specialRule.serialize(obj);
            }
            else if(obj instanceof JSONSerializable)
            {
                obj = ((JSONSerializable) obj).toJSON();
            }

            SerializableField an = field.getAnnotation(SerializableField.class);
            json.put(an.name(), obj);
        }

        return json;
    }

    default void loadJSON(JSONObject json) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
    {
        Class<? extends JSONSerializable> clazz = this.getClass();
        for(Field field : getValidFields(clazz))
        {
            final SerializableField an = field.getAnnotation(SerializableField.class);

            final Object data = json.get(an.name());
            if(data == null)
            {
                continue;
            }

            SerializationRule specialRule = SpecialSerializableClass.getJSONRule(field.getType());

            Object fieldValue;
            if(specialRule == null)
            {
                if((fieldValue = field.get(this)) == null)
                {
                    setOrInvokeSetter(an, this, field, data);
                    continue;
                }
            }
            else
            {
                setOrInvokeSetter(an, this, field, specialRule.deserialize(data));
                continue;
            }

            if(fieldValue instanceof JSONSerializable)
            {
                if(data instanceof JSONObject)
                {
                    ((JSONSerializable) fieldValue).loadJSON((JSONObject) data);
                }
                else
                {
                    Logger.getLogger(clazz.getSimpleName()).warning("Trying to load a JSONSerializable inside another but the value was not a JSONObject");
                }
                continue;
            }

            setOrInvokeSetter(an, this, field, data);
        }
    }

    default String toJSONString() throws IllegalArgumentException, IllegalAccessException
    {
        return toJSON().toJSONString();
    }
}
