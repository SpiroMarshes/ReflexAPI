/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.parozzz.reflex.messages;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import me.parozzz.reflex.ReflexAPI;
import me.parozzz.reflex.utilities.Util;
import net.minecraft.server.v1_13_R2.EnumHand;
import org.apache.commons.lang3.Validate;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;

import javax.annotation.Nullable;

/**
 *
 * @author Paros
 */
public class MessageManager
{
    private final Map<String, String> messageMap;
    /**
     * Convert the configuration section composed of a Map<String, String> to an organized converted map with transled color codes. If any of the subKey of the ConfigurationSection is not a String, it will be skipped. Internally call the loadSection method.
     * @param section The path of the config
     */
    public MessageManager(final ConfigurationSection section)
    {
        messageMap = new HashMap<>();
        if(section != null)
        {
            this.loadSection(section);
        }
    }

    public MessageManager()
    {
        messageMap = new HashMap<>();
    }
    
    /**
     * Load the section
     * @param section The section to load
     */
    public final void loadSection(final ConfigurationSection section)
    {
        Validate.notNull(section, "Trying to load a MessageManager with an null section.");

        messageMap.clear(); //Clearing the map in case already has some values in it
        for(String key : section.getKeys(false))
        {
            if(!section.isString(key))
            {
                ReflexAPI.logger().log(Level.WARNING, "One of the value is not a String [{0}]. Skipping.", key);
                continue;
            }
            
            String message = Util.cc(section.getString(key));
            this.messageMap.put(key.toLowerCase(), message);
        }
    }
    
    /**
     * Return the message to the associated key (Is automatically lowerCased)
     * @param key The key of the message.
     * @return The message if the key is present, null otherwise.
     */
    @Nullable
    public String getString(String key)
    {
        Validate.notNull(key, "Trying to get String from a MessageManager with a null key.");

        String msg = messageMap.get(key.toLowerCase());
        return msg == null || msg.isEmpty() ? null : msg;
    }

    /**
     * {@link me.parozzz.reflex.messages.MessageManager#getString(String)}
     */
    @Nullable
    public String getString(Enum messageEnum)
    {
        return getString(messageEnum.name());
    }

    /**
     * Get an instance of Message class of the message registered to the passed key.
     * @param key The key of the message the get.
     * @return A new Message with the string, null if no key available.
     * */
    @Nullable
    public Message getMessage(String key)
    {
        String msg = getString(key);
        return msg == null ? null : new Message(msg);
    }

    /**
     * {@link me.parozzz.reflex.messages.MessageManager#getMessage(String)}
     */
    @Nullable
    public Message getMessage(Enum messageEnum)
    {
        return getMessage(messageEnum.name());
    }

    /**
     * Send the message associated with the key to the CommandSender paramenter
     * @param cs The CommandSender to send the message to.
     * @param key The key associated with the message.
     * @return True if the message has been sent, false otherwise.
     */
    public boolean sendMessage(CommandSender cs, String key)
    {
        Validate.isTrue(cs != null && key != null, "Trying to send message with something invalid.");

        String message = getString(key);
        if(message != null)
        {
            cs.sendMessage(message);
            return true;
        }
        return false;
    }

    /**
     * {@link me.parozzz.reflex.messages.MessageManager#sendMessage(CommandSender, String)}
     */
    public boolean sendMessage(CommandSender cs, Enum messageEnum)
    {
        return sendMessage(cs, messageEnum.name());
    }
    
    /**
     * Iterate through all the pair key-value inside the main map
     * @param consumer The consumer to execute for each pair
     */
    public void forEach(final BiConsumer<String, String> consumer)
    {
        messageMap.forEach(consumer);
    }
}
