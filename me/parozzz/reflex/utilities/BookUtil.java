/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.parozzz.reflex.utilities;

import net.minecraft.server.v1_13_R2.EntityPlayer;
import net.minecraft.server.v1_13_R2.EnumHand;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_13_R2.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Paros
 */
public class BookUtil 
{
    public static void openBook(final ItemStack book, final Player player)
    {
        ItemStack oldHand = player.getInventory().getItemInMainHand();

        player.getInventory().setItemInMainHand(book);

        EntityPlayer entityPlayer = ((CraftPlayer)player).getHandle();
        entityPlayer.a(CraftItemStack.asNMSCopy(book), EnumHand.MAIN_HAND);

        player.getInventory().setItemInMainHand(oldHand);
    }
}
