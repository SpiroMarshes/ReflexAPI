/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.parozzz.reflex.random;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import me.parozzz.reflex.items.NMSStackCompound;
import me.parozzz.reflex.nms.nbt.NBTCompound;
import me.parozzz.reflex.nms.nbt.NBTList;
import me.parozzz.reflex.nms.nbt.NBTType;
import me.parozzz.reflex.utilities.Util;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Paros
 */
public class RouletteItem 
{/*
    private static boolean REGISTERED = false;
    public static void registerListener()
    {
        REGISTERED = true;
        Bukkit.getPluginManager().registerEvents(new Listener()
        {
            @EventHandler(ignoreCancelled = false, priority = EventPriority.HIGHEST)
            private void onInteract(final PlayerInteractEvent e)
            {
                Optional.ofNullable(e.getItem()).map(RouletteItem::new).filter(RouletteItem::isValid).ifPresent(random -> 
                {
                    if(e.getItem().getAmount() > 1)
                    {
                        ItemStack giveBack = e.getItem().clone();
                        giveBack.setAmount(giveBack.getAmount() - 1);
                        if(e.getPlayer().getInventory().firstEmpty() != -1)
                        {
                            e.getPlayer().getInventory().setItem(e.getPlayer().getInventory().firstEmpty(), giveBack);
                            e.getItem().setAmount(1);
                            random.start(e.getPlayer());
                        }
                    }
                    else
                    {
                        random.start(e.getPlayer());
                    }
                    
                    e.setCancelled(true);
                });
            }
        }, JavaPlugin.getProvidingPlugin(RouletteItem.class));
    }
    
    public static final String COMPOUND = JavaPlugin.getProvidingPlugin(RouletteItem.class).getName() + "Utilities.Random";
    public static final String UID = JavaPlugin.getProvidingPlugin(RouletteItem.class).getName() + "Utilities.UUID";
    
    private final ItemStack item;
    
    private final NBTCompound tag;
    public RouletteItem(final ItemStack item)
    {
        Util.ifCheck(!REGISTERED, () -> registerListener());
        
        this.item = item;
        tag = new NMSStackCompound(item);
    }
    
    public boolean isValid()
    {
        return tag.hasKey(COMPOUND);
    }
    
    public void start(final Player p)
    {
        NBTCompound compound = tag.getWrappedCompound(COMPOUND);
        
        UUID u = UUID.randomUUID();

        new RouletteInstance(p, u, item, compound.keySet().stream().filter(compound::hasKey).map(compound::getWrappedCompound).flatMap(random ->
        {
            NMSStackCompound stack = new NMSStackCompound(random.getWrappedCompound(ITEM));
            stack.setString(UID, u.toString());
            return Collections.nCopies(random.getInt(CHANCE), stack.getItemStack()).stream();
        }).collect(Collectors.toList()));
    }
    
    private static final String CHANCE = "Chance";
    private static final String ITEM = "Item";
    
    public static ItemStack createRandom(final ItemStack item, final Map<ItemStack, Integer> map)
    {
        NMSStackCompound stack = new NMSStackCompound(item);

        NBTList randomList = stack.getWrappedList(COMPOUND, NBTType.COMPOUND);

        int key = 0;
        for(Map.Entry<ItemStack, Integer> e : map.entrySet())
        {
            NBTCompound compound = NBTCompound.getNew();
            compound.setInt(CHANCE, e.getValue());

            compound.setNBT(ITEM,  new NMSStackCompound(e.getKey()).saveNMSItemStack());

            randomList.addNBT(compound);
        }
        
        return stack.getItemStack();
    }*/
}
