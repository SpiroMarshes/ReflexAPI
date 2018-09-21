/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.parozzz.reflex.items.attributes;

import me.parozzz.reflex.nms.nbt.NBTCompound;
import me.parozzz.reflex.nms.nbt.NBTList;
import me.parozzz.reflex.nms.nbt.NBTType;
import me.parozzz.reflex.items.NMSStackCompound;

import javax.annotation.Nullable;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Paros
 */
public final class ItemAttributeModifier
{
    private final NBTList attributeList;

    public ItemAttributeModifier()
    {
        attributeList = NBTList.createNew();
    }

    public ItemAttributeModifier(final NMSStackCompound stack)
    {
        attributeList = stack.getWrapperList("AttributeModifiers", NBTType.COMPOUND);
    }

    public Set<ModifierSnapshot> getModifiers(final ItemAttribute attribute)
    {
        return IntStream.range(0, attributeList.size()).mapToObj(attributeList::getCompound)
                .filter(compound -> compound.getString("AttributeName").equals(attribute.getAttributeName()))
                .map(compound -> new ModifierSnapshot(attribute,
                        compound.getString("Name"),
                        compound.getDouble("Amount"),
                        Operation.getById(compound.getInt("Operation")),
                        AttributeSlot.valueOf(compound.getString("Slot").toUpperCase())))
                .collect(Collectors.toSet());
    }

    public Set<ModifierSnapshot> getModifiers()
    {
        return IntStream.range(0, attributeList.size()).mapToObj(attributeList::getCompound)
                .map(compound -> new ModifierSnapshot(ItemAttribute.getByAttributeName(compound.getString("AttributeName")),
                        compound.getString("Name"), compound
                        .getDouble("Amount"), Operation.getById(compound.getInt("Operation")),
                        AttributeSlot.valueOf(compound.getString("Slot").toUpperCase())))
                .collect(Collectors.toSet());
    }

    public ItemAttributeModifier removeModifiers(final @Nullable ItemAttribute attribute, final @Nullable String specificName)
    {
        for(int j = 0; j < attributeList.size(); j++)
        {
            NBTCompound compound = attributeList.removeCompound(j);
            if(compound == null)
            {
                continue;
            }

            if(attribute == null || compound.getString("AttributeName").equals(attribute.getAttributeName()))
            {
                String name = compound.getString("Name");
                if(specificName == null || name.equals(specificName))
                {
                    attributeList.remove(j);
                }
            }
        }

        return this;
    }

    public ItemAttributeModifier removeModifiers(final @Nullable ItemAttribute attribute)
    {
        return removeModifiers(attribute, null);
    }

    public ItemAttributeModifier removeModifiers(final @Nullable String specificName)
    {
        return removeModifiers(null, specificName);
    }

    public ItemAttributeModifier addSnapshot(final ModifierSnapshot snap)
    {
        return addModifier(snap.getSlot(), snap.getName(), snap.getType(), snap.getOperation(), snap.getAmount());
    }

    public ItemAttributeModifier addModifier(AttributeSlot slot,ItemAttribute attribute,Operation op, double value)
    {
        return addModifier(slot, null, attribute, op, value);
    }

    public ItemAttributeModifier addModifier(AttributeSlot slot,String name,ItemAttribute attribute,Operation op, double value)
    {
        NBTCompound modifierCompound = NBTCompound.getNew();

        modifierCompound.setString("Name", name == null ? attribute.getAttributeName() : name);
        modifierCompound.setString("AttributeName", attribute.getAttributeName());
        modifierCompound.setDouble("Amount", value);
        modifierCompound.setInt("Operation", op.getModifier());
        modifierCompound.setUUID("UUID", UUID.randomUUID());
        modifierCompound.setString("Slot", slot.name().toLowerCase());

        attributeList.addNBT(modifierCompound);
        return this;
    }

    public void addTo(final NMSStackCompound stack)
    {
        stack.setNBT("AttributeModifiers", attributeList);
    }
}
