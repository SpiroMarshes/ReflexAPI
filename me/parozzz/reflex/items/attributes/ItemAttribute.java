/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.parozzz.reflex.items.attributes;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Paros
 */
public enum ItemAttribute
{
    MAX_HEALTH("generic.maxHealth"),
    KNOCKBACK_RESISTANCE("generic.knockbackResistance"),
    MOVEMENT_SPEED("generic.movementSpeed"),
    ATTACK_DAMAGE("generic.attackDamage"),
    ATTACK_SPEED("generic.attackSpeed"),
    ARMOR("generic.armor"),
    ARMOR_TOUGHNESS("generic.armorToughness"),
    LUCK("generic.luck");

    private final static Map<String, ItemAttribute> ATTRIBUTE_NAMES = Stream.of(ItemAttribute.values())
            .collect(Collectors.toMap(i -> i.getAttributeName(), Function.identity()));

    public static ItemAttribute getByAttributeName(final String name)
    {
        return ItemAttribute.ATTRIBUTE_NAMES.get(name);
    }

    private final String name;

    ItemAttribute(final String name)
    {
        this.name = name;
    }

    public String getAttributeName()
    {
        return name;
    }
}
