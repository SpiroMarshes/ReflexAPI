/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.parozzz.reflex.serializable.compound;

import me.parozzz.reflex.nms.nbt.NBTCompound;

/**
 * @author Paros
 */
public interface CompoundSerializable
{
    NBTCompound saveToCompound(NBTCompound compound);

    void loadFromCompound(NBTCompound compound);
}
