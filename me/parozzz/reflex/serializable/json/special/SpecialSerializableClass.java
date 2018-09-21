package me.parozzz.reflex.serializable.json.special;

import me.parozzz.reflex.serializable.json.special.general.ChatColorSerializationRule;
import me.parozzz.reflex.serializable.json.special.general.LocationSerializationRule;
import me.parozzz.reflex.serializable.json.special.general.UUIDSerializationRule;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class SpecialSerializableClass
{
    private static final Map<Class<?>, SerializationRule<?>> jsonRuleMap = new HashMap<>();

    public static void addJSONConversionRule(final SerializationRule<?> rule)
    {
        jsonRuleMap.put(rule.getSerializationClass(), rule);
    }

    public static <T> SerializationRule<T> getJSONRule(final Class<T> clazz)
    {
        return (SerializationRule<T>) jsonRuleMap.get(clazz);
    }

    static
    {
        Stream.of(new LocationSerializationRule(), new UUIDSerializationRule(), new ChatColorSerializationRule())
                .forEach(SpecialSerializableClass::addJSONConversionRule);
    }

}
