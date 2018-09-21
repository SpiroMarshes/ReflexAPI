package me.parozzz.reflex.tools.chat;

import me.parozzz.reflex.tools.chat.ChatInput;
import me.parozzz.reflex.tools.task.TaskManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public final class ChatInputManager implements Listener
{
    private final TaskManager taskManager;
    private final Map<UUID, ChatInput> inputMap;

    public ChatInputManager(TaskManager taskManager)
    {
        this.taskManager = taskManager;

        inputMap = new ConcurrentHashMap<>();
    }

    /**
     * Create a new ChatInput instance if the player is not in one already
     *
     * @param player   The player to lister for
     * @param consumer The action to be executed on input
     * @return The new ChatInput instance or null if one already exists.
     */
    public @Nullable
    ChatInput createInput(final Player player, final Consumer<String> consumer)
    {
        return inputMap.computeIfAbsent(player.getUniqueId(), uuid -> new ChatInput(uuid, consumer));
    }

    public @Nullable
    ChatInput getInput(final Player player)
    {
        return inputMap.get(player.getUniqueId());
    }

    public boolean removeInput(ChatInput input)
    {
        return inputMap.remove(input.getUUID(), input);
    }

    public boolean hasInputSession(final Player player)
    {
        return inputMap.containsKey(player.getUniqueId());
    }

    public boolean hasInputSession(final UUID uuid)
    {
        return inputMap.containsKey(uuid);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    private void onChat(final AsyncPlayerChatEvent e)
    {
        ChatInput input = this.removeInput(e.getPlayer());
        if(input != null)
        {
            e.setCancelled(true);

            String msg = e.getMessage();
            taskManager.sync(() -> input.execute(msg));
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    private void onMove(final PlayerMoveEvent e)
    {
        ChatInput input = this.getInput(e.getPlayer());
        if(input != null)
        {
            input.executeMoveEvent(e);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    private void onQuit(final PlayerQuitEvent e)
    {
        this.removeInput(e.getPlayer());
    }

    private ChatInput removeInput(final Player player)
    {
        return inputMap.remove(player.getUniqueId());
    }
}
