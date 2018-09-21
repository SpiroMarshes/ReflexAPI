/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.parozzz.reflex.items;

import me.parozzz.reflex.nms.nbt.NBTCompound;
import me.parozzz.reflex.nms.nbt.NBTType;
import me.parozzz.reflex.tools.Validator;
import me.parozzz.reflex.utilities.ReflectionUtil;
import me.parozzz.reflex.utilities.Util;
import net.minecraft.server.v1_13_R2.*;
import org.apache.commons.lang3.Validate;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_13_R2.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_13_R2.util.CraftMagicNumbers;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author Paros
 */
public class NMSStackCompound extends NBTCompound implements Itemable
{
    private final static Field getHandle = ReflectionUtil.getField(CraftItemStack.class, "handle");

    //private final static Field CAN_DESTROY_BLOCK_SET = ReflectionUtil.getField(ItemTool.class, "e");

    private final org.bukkit.Material bukkitMaterial;
    private final ItemStack nmsStack;
    private final boolean isUpdateNeeded;

    public NMSStackCompound(final org.bukkit.Material type)
    {
        this(new ItemStack(CraftMagicNumbers.getItem(type)));
    }

    public NMSStackCompound(final org.bukkit.inventory.ItemStack itemStack)
    {
        if(itemStack == null)
        {
            bukkitMaterial = Material.AIR;

            nmsStack = ItemStack.a;
            isUpdateNeeded = false;
            return;
        }
        else if((bukkitMaterial = itemStack.getType()) == org.bukkit.Material.AIR)
        {
            nmsStack = ItemStack.a;
            isUpdateNeeded = false;
            return;
        }

        isUpdateNeeded = !(itemStack instanceof CraftItemStack);

        nmsStack = isUpdateNeeded ? CraftItemStack.asNMSCopy(itemStack) : getHandle((CraftItemStack) itemStack);
        setCompound(nmsStack.getOrCreateTag());
    }

    public NMSStackCompound(final NBTCompound serializedItemStack)
    {
        this(ItemStack.a(serializedItemStack.getBase()));
    }

    public NMSStackCompound(final ItemStack nmsStack)
    {
        this(CraftMagicNumbers.getMaterial(nmsStack.getItem()), nmsStack);
    }

    protected NMSStackCompound(final org.bukkit.Material bukkitMaterial, final ItemStack nmsStack)
    {
        this.nmsStack = nmsStack;
        this.bukkitMaterial = bukkitMaterial;

        setCompound(nmsStack.getOrCreateTag());

        isUpdateNeeded = false;
    }

    private static ItemStack getHandle(final CraftItemStack craftItemStack)
    {
        return (ItemStack) Validator.validateField(getHandle, craftItemStack);
    }

    public final org.bukkit.Material getMaterial()
    {
        return bukkitMaterial;
    }

    public final int getDurability()
    {
        return nmsStack.getDamage();
    }

    public final void setDurability(final int durability)
    {
        nmsStack.setDamage(durability);
    }

    public final int getAmount()
    {
        return nmsStack.getCount();
    }

    public final void setAmount(final int amount)
    {
        nmsStack.setCount(amount);
    }

    public void addMetaDisplay(final ItemMeta meta)
    {
        if(meta == null)
        {
            return;
        }

        NBTCompound display = getWrappedCompound("display");

        if(meta.hasLore())
        {
            NBTTagList tagList = new NBTTagList();
            meta.getLore().stream().map(NBTTagString::new).forEach(tagList::add);
            display.set("Lore", tagList);
        }

        if(meta.hasDisplayName())
        {
            display.setString("Name", meta.getDisplayName());
        }

    }

    public NMSStackCompound parseDisplayPlaceholder(final String[] array)
    {
        NBTTagCompound compound = getCompound("display");
        if(compound.hasKey("name"))
        {
            String displayName = compound.getString("name");
            for(int j = 0; j < array.length && j + 1 < array.length; j += 2)
            {
                displayName = displayName.replace(array[j], array[j + 1]);
            }
            compound.setString("name", displayName);
        }

        if(compound.hasKey("Lore"))
        {
            NBTTagList nbtList = compound.getList("Lore", NBTType.STRING.getId());
            for(int x = 0; x < nbtList.size(); x++)
            {
                String str = nbtList.getString(x);
                for(int j = 0; j < array.length && j + 1 < array.length; j += 2)
                {
                    str = str.replace(array[j], array[j + 1]);
                }
                nbtList.a(x, new NBTTagString(Util.cc(str)));
            }
        }

        return this;
    }

    public NMSStackCompound parseDisplayPlaceholder(final Map<String, String> map)
    {
        Set<Map.Entry<String, String>> entrySet = map.entrySet();

        NBTTagCompound compound = getCompound("display");
        if(compound.hasKey("name"))
        {
            String displayName = entrySet.stream().reduce(compound.getString("name"), (name, entry) -> name.replace(entry.getValue(), entry.getKey()), (p, q) -> p + q);
            compound.setString("name", displayName);
        }

        if(compound.hasKey("Lore"))
        {
            NBTTagList nbtList = compound.getList("Lore", NBTType.STRING.getId());
            for(int x = 0; x < nbtList.size(); x++)
            {
                String str = entrySet.stream().reduce(nbtList.getString(x), (name, entry) -> name.replace(entry.getValue(), entry.getKey()), (p, q) -> p + q);
                nbtList.a(x, new NBTTagString(Util.cc(str)));
            }
        }

        return this;
    }

    public boolean hasLore()
    {
        return getCompound("display").hasKey("Lore");
    }

    public List<String> getLore()
    {
        NBTTagList nbtLoreList = getCompound("display").getList("Lore", NBTType.STRING.getId());
        return IntStream.range(0, nbtLoreList.size()).mapToObj(nbtLoreList::getString).collect(Collectors.toList());
    }

    public void setLore(final List<String> loreList)
    {
        getWrappedCompound("display").set("Lore", getLoreTagList(loreList));
    }

    public String getDisplayName()
    {
        return getCompound("display").getString("Name");
    }

    public void setDisplayName(final String displayName)
    {
        getWrappedCompound("display").setString("Name", displayName);
    }

    private NBTTagList getLoreTagList(final List<String> loreList)
    {
        NBTTagList tagList = new NBTTagList();
        if(loreList == null || loreList.isEmpty())
        {
            return tagList;
        }
        loreList.stream().map(NBTTagString::new).forEach(tagList::add);
        return tagList;
    }

    public void setDisplay(final String displayName, final List<String> loreList)
    {
        NBTCompound wrapper = getWrappedCompound("display");
        wrapper.setString("Name", displayName);
        wrapper.set("Lore", getLoreTagList(loreList));
    }

    // Copied from nms ItemStack
    public void addEnchant(final org.bukkit.enchantments.Enchantment enchantment, final int level)
    {
        NBTTagList enchantList;
        if(!hasKeyOfType("ench", NBTType.LIST))
        {
            enchantList = new NBTTagList();
            set("ench", enchantList);
        }
        else
        {
            enchantList = getList("ench", NBTType.COMPOUND);
        }

        for(int j = 0; j < enchantList.size(); j++)
        {
            NBTTagCompound loopCompound = enchantList.getCompound(j);
            if(loopCompound.getString("id").equalsIgnoreCase(enchantment.getKey().toString()))
            {
                loopCompound.setShort("lvl", (short) level);
                return;
            }
        }

        NBTTagCompound localEnchantCompound = new NBTTagCompound();
        localEnchantCompound.setString("id", enchantment.getKey().toString());
        localEnchantCompound.setShort("lvl", (short) level);
        enchantList.add(localEnchantCompound);
    }

    // Copied from nms ItemStack
    public Map<org.bukkit.enchantments.Enchantment, Integer> getEnchantments()
    {
        Map<org.bukkit.enchantments.Enchantment, Integer> map = new HashMap<>();
        if(!hasKeyOfType("ench", NBTType.LIST))
        {
            return map;
        }

        NBTTagList enchantList = getList("ench", NBTType.COMPOUND);
        for(int j = 0; j < enchantList.size(); j++)
        {
            NBTTagCompound loopCompound = enchantList.getCompound(j);

            if(loopCompound.hasKey("id") && loopCompound.hasKey("lvl"))
            {
                NamespacedKey enchantmentKey = NamespacedKey.minecraft(loopCompound.getString("id"));

                org.bukkit.enchantments.Enchantment ench = org.bukkit.enchantments.Enchantment.getByKey(enchantmentKey);
                int level = loopCompound.getInt("level");

                map.put(ench, level);
            }
        }

        return map;
    }

    // Copied from nms ItemStack
    public void removeEnchant(final org.bukkit.enchantments.Enchantment enchantment)
    {
        if(!hasKeyOfType("ench", NBTType.LIST))
        {
            return;
        }

        NBTTagList enchantList = getList("ench", NBTType.COMPOUND);

        for(int j = 0; j < enchantList.size(); j++)
        {
            NBTTagCompound loopCompound = enchantList.getCompound(j);
            if(loopCompound.getString("id").equalsIgnoreCase(enchantment.getKey().toString()))
            {
                enchantList.remove(j);
                return;
            }
        }
    }

    public void addItemFlags(final ItemFlag... itemFlags)
    {
        int hideFlags = getHideFlags();
        for(final ItemFlag flag : itemFlags)
        {
            hideFlags |= getItemFlagModifier(flag);
        }
        setHideFlags(hideFlags);
    }

    public void removeItemFlags(final ItemFlag... itemFlags)
    {
        int hideFlags = getHideFlags();
        for(final ItemFlag flag : itemFlags)
        {
            hideFlags &= ~getItemFlagModifier(flag);
        }
        setHideFlags(hideFlags);
    }

    public Set<ItemFlag> getItemFlags()
    {
        int hideFlags = getHideFlags();
        return Stream.of(ItemFlag.values()).filter(flag -> hasItemFlag(flag, hideFlags)).collect(Collectors.toSet());
    }

    public boolean hasItemFlag(final ItemFlag flag)
    {
        return this.hasItemFlag(flag, getHideFlags());
    }

    private boolean hasItemFlag(final ItemFlag flag, int hideFlags)
    {
        int bitModifier = getItemFlagModifier(flag);
        return (hideFlags & bitModifier) == bitModifier;
    }

    private int getHideFlags()
    {
        return getInt("HideFlags");
    }

    private void setHideFlags(final int hideFlags)
    {
        setInt("HideFlags", hideFlags);
    }

    private byte getItemFlagModifier(final ItemFlag itemFlag)
    {
        return (byte) (1 << itemFlag.ordinal());
    }

    public int getRepairCost()
    {
        return getInt("RepairCost");
    }

    public void setRepairCost(final int cost)
    {
        setInt("RepairCost", cost);
    }

    public boolean isUnbreakable()
    {
        return getBoolean("Unbreakable");
    }

    public void setUnbreakable(final boolean bln)
    {
        setBoolean("Unbreakable", bln);
    }

    public boolean isTool()
    {
        Item item = nmsStack.getItem();
        return item instanceof ItemTool || item instanceof ItemSword;
    }

    public boolean isArmor()
    {
        return nmsStack.getItem() instanceof ItemArmor;
    }

    public boolean isBreakable()
    {
        return nmsStack.f(); // Checks if the item is breakable or not.
    }

    public boolean isEnchantable()
    {
        Item item = nmsStack.getItem();
        return item instanceof ItemTool
                || item instanceof ItemArmor || item instanceof ItemShears || item instanceof ItemSword || item instanceof ItemBow || item instanceof ItemFishingRod
                || item instanceof ItemFlintAndSteel || item instanceof ItemShield || item instanceof ItemCarrotStick;
    }

    public boolean isUpdateNeeded()
    {
        return isUpdateNeeded;
    }

    public ItemMeta getItemMeta()
    {
        return CraftItemStack.getItemMeta(nmsStack);
    }

    public void setItemMeta(final ItemMeta meta)
    {
        CraftItemStack.setItemMeta(nmsStack, meta);
        setCompound(nmsStack.getOrCreateTag());
    }

    @Override
    public org.bukkit.inventory.ItemStack getItemStack()
    {
        return CraftItemStack.asCraftMirror(nmsStack);
    }

    public void updateItemStack(final org.bukkit.inventory.ItemStack itemStack)
    {
        itemStack.setItemMeta(getItemMeta());
    }

    public boolean isAir()
    {
        return bukkitMaterial == org.bukkit.Material.AIR;
    }

    public ItemStack getNMSStack()
    {
        return nmsStack;
    }

    public NBTCompound saveNMSItemStack(final NBTCompound compound)
    {
        Validate.notNull(compound);

        if(!isAir())
        {
            nmsStack.save(compound.getBase());
        }
        return compound;
    }

    public NBTCompound saveNMSItemStack()
    {
        return saveNMSItemStack(NBTCompound.getNew());
    }

    @Override
    public boolean isEmpty()
    {
        return isAir() || nmsStack.isEmpty() || super.isEmpty();
    }

    @Override
    public NMSStackCompound clone()
    {
        return new NMSStackCompound(bukkitMaterial, nmsStack.cloneItemStack());
    }

    @Override
    public String toString()
    {
        return getBase().toString();
    }
}
