package me.parozzz.reflex.serializable.json.special.general;

import me.parozzz.reflex.serializable.json.special.SerializationRule;

import java.util.UUID;

public class UUIDSerializationRule extends SerializationRule<UUID>
{
    public UUIDSerializationRule()
    {
        super(UUID.class);
    }

    public Object serialize(UUID uuid)
    {
        return uuid.toString();
    }

    @Override
    public UUID deserialize(Object obj)
    {
        return UUID.fromString(obj.toString());
    }
}
