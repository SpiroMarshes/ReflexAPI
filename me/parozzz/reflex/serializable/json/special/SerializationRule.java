package me.parozzz.reflex.serializable.json.special;

public abstract class SerializationRule<T>
{
    private final Class<T> clazz;

    public SerializationRule(final Class<T> clazz)
    {
        this.clazz = clazz;
    }

    public Class<?> getSerializationClass()
    {
        return clazz;
    }

    public abstract Object serialize(T t);

    public abstract T deserialize(Object obj);
}
