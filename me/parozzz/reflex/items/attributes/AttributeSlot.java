/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.parozzz.reflex.items.attributes;

import org.bukkit.Material;

import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author Paros
 */
public enum AttributeSlot
{
    MAINHAND,
    OFFHAND,
    FEET,
    LEGS,
    CHEST,
    HEAD;

    private final static Map<Material, AttributeSlot> EQUIPMENT = new EnumMap<>(Material.class);

    public static AttributeSlot matchMaterial(final Material m)
    {
        if(m == null)
        {
            return AttributeSlot.MAINHAND;
        }

        return m == Material.SHIELD ? OFFHAND : AttributeSlot.EQUIPMENT.getOrDefault(m, MAINHAND);
    }

    static
    {
        Stream.of(Material.LEATHER_BOOTS, Material.GOLDEN_BOOTS, Material.CHAINMAIL_BOOTS, Material.IRON_BOOTS, Material.DIAMOND_BOOTS)
                .forEach(m -> AttributeSlot.EQUIPMENT.put(m, AttributeSlot.FEET));
        Stream.of(Material.LEATHER_LEGGINGS, Material.GOLDEN_LEGGINGS, Material.CHAINMAIL_LEGGINGS, Material.IRON_LEGGINGS, Material.DIAMOND_LEGGINGS)
                .forEach(m -> AttributeSlot.EQUIPMENT.put(m, AttributeSlot.LEGS));
        Stream.of(Material.LEATHER_CHESTPLATE, Material.GOLDEN_CHESTPLATE, Material.CHAINMAIL_CHESTPLATE, Material.IRON_CHESTPLATE, Material.DIAMOND_CHESTPLATE)
                .forEach(m -> AttributeSlot.EQUIPMENT.put(m, AttributeSlot.CHEST));
        Stream.of(Material.LEATHER_HELMET, Material.GOLDEN_HELMET, Material.CHAINMAIL_HELMET, Material.IRON_HELMET, Material.DIAMOND_HELMET)
                .forEach(m -> AttributeSlot.EQUIPMENT.put(m, AttributeSlot.HEAD));
    }
}
