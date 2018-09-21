package me.parozzz.reflex.serializable.json.special.general;

import me.parozzz.reflex.serializable.json.special.SerializationRule;
import org.bukkit.ChatColor;

public class ChatColorSerializationRule extends SerializationRule<ChatColor>
{
    public ChatColorSerializationRule()
    {
        super(ChatColor.class);
    }

    @Override
    public Object serialize(ChatColor chatColor)
    {
        return chatColor.name();
    }

    @Override
    public ChatColor deserialize(Object obj)
    {
        return ChatColor.valueOf(obj.toString());
    }
}
