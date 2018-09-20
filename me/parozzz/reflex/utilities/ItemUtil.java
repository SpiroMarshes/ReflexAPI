/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.parozzz.reflex.utilities;

import me.parozzz.reflex.MCVersion;
import me.parozzz.reflex.tools.Validator;
import me.parozzz.reflex.utilities.Util.ColorEnum;
import org.apache.commons.lang3.Validate;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Paros
 */
public class ItemUtil
{
    public static boolean nonNull(final ItemStack item)
    {
        return item != null && item.getType() != Material.AIR;
    }

    private static Predicate<ItemStack> isUnbreakable;

    public static boolean isUnbreakable(final ItemStack item)
    {
        return Optional.ofNullable(isUnbreakable).orElseGet(() ->
        {
            if(MCVersion.V1_8.isEqual())
            {
                isUnbreakable = i -> false;
            }
            else if(MCVersion.contains(MCVersion.V1_9, MCVersion.V1_10))
            {
                isUnbreakable = i -> i.getItemMeta().spigot().isUnbreakable();
            }
            else
            {
                isUnbreakable = i -> i.getItemMeta().isUnbreakable();
            }
            return isUnbreakable;
        }).test(item);
    }

    private static BiConsumer<ItemMeta, Boolean> setUnbreakable;

    public static void setUnbreakable(final ItemMeta meta, final boolean unbreakable)
    {
        Optional.ofNullable(setUnbreakable).orElseGet(() ->
        {
            if(MCVersion.V1_8.isEqual())
            {
                setUnbreakable = (m, b) ->
                {
                };
            }
            else if(MCVersion.contains(MCVersion.V1_9, MCVersion.V1_10))
            {
                setUnbreakable = (m, b) -> m.spigot().setUnbreakable(b);
            }
            else
            {
                setUnbreakable = (m, b) -> m.setUnbreakable(b);
            }

            return setUnbreakable;
        }).accept(meta, unbreakable);
    }

    public static ItemStack getItemByPath(final ConfigurationSection path)
    {
        return getItemByPath(null, path);
    }

    public static ItemStack getItemByPath(@Nullable Material type, final ConfigurationSection path)
    {
        Validate.notNull(type == null && path == null, "Something went really wrong while create a new ItemStack. Please report this.");

        if(type == null)
        {
            Validate.isTrue(path.contains("material") && path.isString("material"),
                    "You cannot create an ItemStack without the 'material' path. Or is the material has invalid format?");

            type = Validator.validateEnum(path.getString("material"), Material.class);
            Validate.notNull(type, "Trying to create an ItemStack with an invalid 'material' path. Wrong name?");
        }

        ItemStack itemStack = new ItemStack(type);
        if(path == null)
        {
            return itemStack;
        }

        if(path.contains("amount"))
        {
            Validate.isTrue(path.isInt("amount"), "The 'amount' path has wrong format");

            itemStack.setAmount(path.getInt("amount"));
        }

        ItemMeta itemMeta = itemStack.getItemMeta();

        if(itemMeta instanceof SkullMeta)
        {
            SkullMeta skullMeta = (SkullMeta) itemMeta;

            if(path.contains("url"))
            {
                Validate.isTrue(path.isString("url"), "The 'url' path has wrong format.");

                HeadUtil.setTexture(skullMeta, path.getString("url"));
            }
        }
        else if(itemMeta instanceof PotionMeta)
        {
            PotionMeta potionMeta = (PotionMeta) itemMeta;

            if(path.contains("color"))
            {
                Validate.isTrue(path.isString("color"), "The 'color' path has wrong format.");

                ColorEnum color = Validator.validateEnum(path.getString("color"), ColorEnum.class);
                Validate.notNull(color, "The 'color' path is not valid. The color you choosed doesn't exists?");

                potionMeta.setColor(color.getBukkitColor());
            }

            if(path.contains("effects"))
            {
                Validate.isTrue(path.isList("effects"), "The effects path has wrong format");

                Iterator<String[]> it = path.getStringList("effects").stream().map(str -> str.split(":")).iterator();
                while(it.hasNext())
                {
                    String[] array = it.next();
                    Validate.isTrue(array.length == 3, "One of the value in the 'effects' has wrong format.");

                    PotionEffectType pet = PotionEffectType.getByName(array[0].toUpperCase());

                    int duration = Validator.parseInt(array[1]);
                    int level = Validator.parseInt(array[2]);

                    int minValue = Integer.MIN_VALUE;
                    Validate.isTrue(pet != null && duration != minValue && level != minValue,
                            "One of the value in the 'effects' has wrong format. Invalid numbers or potion effect?");

                    potionMeta.addCustomEffect(new PotionEffect(pet, duration, level), true);
                }
            }
        }
        else if(itemMeta instanceof LeatherArmorMeta)
        {
            LeatherArmorMeta armorMeta = (LeatherArmorMeta) itemMeta;

            if(path.contains("color"))
            {
                Validate.isTrue(path.isString("color"), "The 'color' path has wrong format.");

                ColorEnum color = Validator.validateEnum(path.getString("color"), ColorEnum.class);
                Validate.notNull(color, "The 'color' path is not valid. The color you choosed doesn't exists?");

                armorMeta.setColor(color.getBukkitColor());
            }
        }

        if(itemMeta instanceof Damageable)
        {
            Damageable damageable = (Damageable) itemMeta;
            if(path.contains("damage"))
            {
                Validate.isTrue(path.isInt("damage"), "The 'durability' path has wrong format.");
                damageable.setDamage(path.getInt("damage"));
            }
        }

        if(itemMeta instanceof Repairable)
        {
            Repairable repairable = (Repairable) itemMeta;

            if(path.contains("repair_cost"))
            {
                Validate.isTrue(path.isInt("repair_cost"), "The 'repair_cost' path has wrong format.");
                repairable.setRepairCost(path.getInt("repair_cost"));
            }
        }

        if(path.contains("name"))
        {
            Validate.isTrue(path.isString("name"), "The 'name' path has wrong format.");

            itemMeta.setDisplayName(Util.cc(path.getString("name")));
        }

        if(path.contains("lore"))
        {
            Validate.isTrue(path.isList("lore"), "The 'lore' path has wrong format.");

            List<String> loreList = path.getStringList("lore").stream().map(Util::cc).collect(Collectors.toList());
            itemMeta.setLore(loreList);
        }

        if(path.contains("item_flags"))
        {
            Validate.isTrue(path.isList("item_flags"), "The 'item_flags' path has wrong format.");

            for(String value : path.getStringList("item_flags"))
            {
                ItemFlag flag = Validator.validateEnum(value, ItemFlag.class);
                Validate.notNull(flag, "One of the value of 'item_flags' is not valid. Value " + value);

                itemMeta.addItemFlags(flag);
            }
        }

        if(path.contains("unbreakable"))
        {
            Validate.isTrue(path.isBoolean("unbreakable"), "The 'unbreakable' path has wrong format.");

            itemMeta.setUnbreakable(path.getBoolean("unbreakable"));
        }

        if(path.contains("enchants"))
        {
            Validate.isTrue(path.isList("enchants"), "The 'enchants' path has wrong format.");

            for(Map.Entry entry : path.getMapList("enchants").stream().map(Map::entrySet).flatMap(Set::stream).collect(Collectors.toList()))
            {
                Validate.isTrue(entry.getKey() instanceof String && entry.getValue() instanceof Integer,
                        "One of the values in the 'enchants' path is not valid");

                Enchantment ench = Enchantment.getByKey(NamespacedKey.minecraft(entry.getKey().toString()));
                Validate.notNull("The enchantment value inside a 'enchants' path is not valid. Value: " + entry.getKey());

                itemMeta.addEnchant(ench, (Integer) entry.getValue(), true);
            }
        }

        itemStack.setItemMeta(itemMeta);
        return itemStack;

            /*
            NMSStackCompound stack = new NMSStackCompound(itemStack);
            NMSStack nbt = new NMSStack(item);
            NBTCompound compound = nbt.getTag();

            new SimpleMapList(path.getMapList("tag")).getView().forEach((key, value) -> compound.setString(key, value.get(0)));

            new SimpleMapList(path.getMapList("adventure")).getView().forEach((key, list) ->
            {
                AdventureTag tag = Debug.validateEnum(key, AdventureTag.class);
                NMSStack.setAdventureFlag(compound, tag, Stream.of(list.get(0).split(",")).map(str -> Debug.validateEnum(str, Material.class)).toArray(Material[]::new));
            });

            if(path.contains("Attribute"))
            {
                ItemAttributeModifier modifier = new ItemAttributeModifier();
                new ComplexMapList(path.getMapList("Attribute")).getView().forEach((attr, list) ->
                {
                    ItemAttributeModifier.ItemAttribute attribute = Debug.validateEnum(attr, ItemAttributeModifier.ItemAttribute.class);

                    MapArray map = list.get(0);
                    double value = map.getValue("value", Double::valueOf);
                    ItemAttributeModifier.Operation op = ItemAttributeModifier.Operation.getById(map.getValue("operation", Integer::valueOf));
                    ItemAttributeModifier.AttributeSlot slot = Debug.validateEnum(Optional.ofNullable(map.getValue("slot", Function.identity())).orElse("MAINHAND"),
                            ItemAttributeModifier.AttributeSlot.class);

                    modifier.addModifier(slot, attribute, op, value);
                });
                modifier.apply(compound);
            }

            return nbt.setTag(compound).getBukkitItem();*/
    }

    public static void parseItemVariable(final ItemStack item, final String placeholder, final Object replace)
    {
        ItemMeta meta = item.getItemMeta();
        parseMetaVariable(meta, placeholder, replace);
        item.setItemMeta(meta);
    }

    public static void parseMetaVariable(final ItemMeta meta, final String placeholder, final Object replace)
    {
        if(meta.hasDisplayName())
        {
            meta.setDisplayName(Util.cc(meta.getDisplayName().replace(placeholder, replace.toString())));
        }

        if(meta.hasLore())
        {
            meta.setLore(meta.getLore().stream().map(lore -> lore.replace(placeholder, replace.toString())).map(Util::cc).collect(Collectors.toList()));
        }
    }

    public static void decreaseItemStack(final ItemStack item, final Player p, final Inventory i)
    {
        decreaseItemStack(item, i);
        p.updateInventory();
    }

    public static void decreaseItemStack(final ItemStack item, final Inventory i)
    {
        if(item.getAmount() == 1)
        {
            i.removeItem(item);
            return;
        }

        item.setAmount(item.getAmount() - 1);
    }

    public static String getItemStackName(final ItemStack item)
    {
        if(item == null || !item.hasItemMeta())
        {
            return Material.AIR.name();
        }

        return getMetaName(item.getType(), item.getItemMeta());
    }

    public static String getMetaName(final Material type, final ItemMeta meta)
    {
        return meta.hasDisplayName() ? meta.getDisplayName() : type.name();
    }

}
