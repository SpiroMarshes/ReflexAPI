/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.parozzz.reflex.utilities;

import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Paros
 */
public class EntityUtil 
{
    public static void setExp(final Player p, final int exp)
    {
        p.setTotalExperience(0);
        p.setLevel(0);
        p.setExp(0);
        
        p.giveExp(exp);
    }
    
    public static void addItem(final EntityEquipment equip, final EquipmentSlot slot, final ItemStack item)
    {
        addItem(equip, slot, item, null);
    }

    public static void addItem(final EntityEquipment equip, final EquipmentSlot slot, final ItemStack item, final Float dropChance)
    {
        if(slot == null)
        {
            return;
        }

        switch(slot)
        {
            case HEAD:
                equip.setHelmet(item);
                if(dropChance!=null)
                {
                    equip.setHelmetDropChance(dropChance);
                }
                break;
            case CHEST:
                equip.setChestplate(item);
                if(dropChance!=null)
                {
                    equip.setChestplateDropChance(dropChance);
                }
                break;
            case LEGS:
                equip.setLeggings(item);
                if(dropChance!=null)
                {
                    equip.setLeggingsDropChance(dropChance);
                }
                break;
            case FEET:
                equip.setBoots(item);
                if(dropChance!=null)
                {
                    equip.setBootsDropChance(dropChance);
                }
                break;
            case HAND:
                equip.setItemInMainHand(item);
                if(dropChance!=null)
                {
                    equip.setItemInMainHandDropChance(dropChance);
                }
                break;
            case OFF_HAND:
                equip.setItemInOffHand(item);
                if(dropChance!=null)
                {
                    equip.setItemInOffHandDropChance(dropChance);
                }
                break;
        }
    }
}
