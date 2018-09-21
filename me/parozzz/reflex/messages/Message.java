package me.parozzz.reflex.messages;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public final class Message
{
    private String msg;

    protected Message(String msg)
    {
        this.msg = msg;
    }

    public Message parse(String placeholder, Object parser)
    {
        msg = msg.replace(placeholder, parser.toString());
        return this;
    }

    public Message parse(final String[] array)
    {
        for(int j = 0; j + 1 < array.length; j += 2)
        {
            msg = msg.replace(array[j], array[j + 1]);
        }
        return this;
    }

    public Message parse(final Map<String, String> map)
    {
        for(Map.Entry<String, String> entry : map.entrySet())
        {
            msg = msg.replace(entry.getKey(), entry.getValue());
        }
        return this;
    }

    public void send(CommandSender cs)
    {
        cs.sendMessage(msg);
    }

    public void sendActionBar(Player player)
    {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(msg));
    }

    @Override
    public Message clone()
    {
        return new Message(msg);
    }

    @Override
    public String toString()
    {
        return msg;
    }
}
