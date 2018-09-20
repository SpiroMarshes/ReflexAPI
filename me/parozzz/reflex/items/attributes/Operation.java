/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.parozzz.reflex.items.attributes;

/**
 * @author Paros
 */
public enum Operation
{
    ADD(0),
    PERCENTAGE_ADD(1),
    PERCENTAGE_MULTIPLY(2);

    public static Operation getById(final int id)
    {
        switch(id)
        {
            case 0:
                return ADD;
            case 1:
                return PERCENTAGE_ADD;
            case 2:
                return PERCENTAGE_MULTIPLY;
            default:
                return null;
        }
    }

    private final int modifier;

    Operation(final int modifier)
    {
        this.modifier = modifier;
    }

    public int getModifier()
    {
        return modifier;
    }
}
