/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.parozzz.reflex.events.wrappers.wrappers.combat;

import me.parozzz.reflex.events.wrappers.PlayerEventWrapper;
import me.parozzz.reflex.items.NMSStackCompound;
import me.parozzz.reflex.utilities.InventoryUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.PlayerInventory;

import javax.annotation.Nullable;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * @param <T>
 * @author Paros
 */
public abstract class PlayerCombatEvent<T extends EntityDamageEvent> extends PlayerEventWrapper<T> implements Iterable<NMSStackCompound>
{
    private final Map<EquipmentSlot, NMSStackCompound> equipment;
    private final Player player;

    public PlayerCombatEvent(final T wrapped, Player player)
    {
        super(wrapped);

        this.player = player;
        equipment = new EnumMap<>(EquipmentSlot.class);

        PlayerInventory inventory = player.getInventory();
        Stream.of(EquipmentSlot.values()).forEach(slot ->
        {
            NMSStackCompound stack = new NMSStackCompound(InventoryUtil.getItem(inventory, slot));
            equipment.put(slot, stack);
        });
    }

    @Override
    public Player getPlayer()
    {
        return player;
    }

    @Nullable
    public NMSStackCompound getEquipment(final EquipmentSlot slot)
    {
        return equipment.get(slot);
    }

    @Override
    public Iterator<NMSStackCompound> iterator()
    {
        return equipment.values().iterator();
    }

    @Override
    public void forEach(Consumer<? super NMSStackCompound> cnsmr)
    {
        equipment.values().forEach(cnsmr);
    }

    public void forEachEquipment(final BiConsumer<EquipmentSlot, NMSStackCompound> consumer)
    {
        equipment.forEach(consumer);
    }
}
