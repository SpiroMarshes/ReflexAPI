/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.parozzz.reflex.tools.chat;

import me.parozzz.reflex.utilities.Util;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.UUID;
import java.util.function.Consumer;

/**
 * @author Paros
 */
public final class ChatInput
{
    private final UUID uuid;
    private final Consumer<String> consumer;
    @SuppressWarnings("unchecked") private Consumer<PlayerMoveEvent> onMoveConsumer = Util.EMPTY_CONSUMER;

    protected ChatInput(final UUID uuid, Consumer<String> consumer)
    {
        this.uuid = uuid;
        this.consumer = consumer;
    }

    public UUID getUUID()
    {
        return uuid;
    }

    public ChatInput addMoveEvent(final Consumer<PlayerMoveEvent> consumer)
    {
        onMoveConsumer = consumer;
        return this;
    }

    protected void executeMoveEvent(final PlayerMoveEvent e)
    {
        onMoveConsumer.accept(e);
    }

    protected void execute(final String input)
    {
        consumer.accept(input);
    }
}
