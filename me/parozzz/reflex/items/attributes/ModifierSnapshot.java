/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.parozzz.reflex.items.attributes;

/**
 * @author Paros
 */
public class ModifierSnapshot
{
    private final ItemAttribute attribute;
    private final Operation operation;
    private final String name;
    private final double amount;
    private final AttributeSlot slot;

    public ModifierSnapshot(
            final ItemAttribute attr, final String name, final double amount, final Operation operation,
            final AttributeSlot slot)
    {
        attribute = attr;
        this.name = name;
        this.amount = amount;
        this.operation = operation;
        this.slot = slot;
    }

    public String getName()
    {
        return name;
    }

    public ItemAttribute getType()
    {
        return attribute;
    }

    public double getAmount()
    {
        return amount;
    }

    public Operation getOperation()
    {
        return operation;
    }

    public AttributeSlot getSlot()
    {
        return slot;
    }
}
