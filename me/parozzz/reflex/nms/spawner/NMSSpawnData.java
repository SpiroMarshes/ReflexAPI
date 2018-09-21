/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.parozzz.reflex.nms.spawner;

import me.parozzz.reflex.items.NMSStackCompound;
import me.parozzz.reflex.nms.nbt.NBTCompound;
import me.parozzz.reflex.nms.nbt.NBTList;
import me.parozzz.reflex.nms.nbt.NBTType;
import net.minecraft.server.v1_13_R2.MobSpawnerData;
import org.bukkit.entity.EntityType;

import javax.annotation.Nullable;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author Paros
 */
public final class NMSSpawnData
{
    private final NBTCompound compound;
    private final NBTCompound entityCompound;

    /*
    protected NMSSpawnData(final NBTTagCompound nmsCompound)
    {
        this(new NBTCompound(nmsCompound));
    }*/

    public NMSSpawnData(final NBTCompound compound)
    {
        this.compound = compound;

        entityCompound = compound.getWrappedCompound("Entity");
    }

    public NMSSpawnData()
    {
        this(NBTCompound.getNew());
        setEntityType(EntityType.PIG);
        setWeight(1);
    }

    public NMSSpawnData(final String name, final int weight)
    {
        this(NBTCompound.getNew());
        setEntityName(name).setWeight(weight);
    }
/*
    public NMSSpawnData(final CreatureType ct, final int weight)
    {
        this(ct.getTypeName(), weight);
    }
*/
    public NMSSpawnData(final EntityType et, final int weight)
    {
        this(et.getName(), weight);
    }


    public NMSSpawnData setSilent(final boolean silent)
    {
        entityCompound.setBoolean("Silent", silent);
        return this;
    }

    public NMSSpawnData setGlowing(final boolean glow)
    {
        entityCompound.setBoolean("Glowing", glow);
        return this;
    }

    public NMSSpawnData setFireTicks(final int ticks)
    {
        entityCompound.setShort("Fire", (short) ticks);
        return this;
    }

    public NMSSpawnData setAi(final boolean ai)
    {
        entityCompound.setBoolean("NoAI", !ai);
        return this;
    }

    public NMSSpawnData setEntityName(final String name)
    {
        entityCompound.setString("id", "minecraft:" + name);
        return this;
    }
/*
    public CreatureType getCreatureType()
    {
        return CreatureType.getByEntityType(getEntityType());
    }

    public NMSSpawnData setCreatureType(final CreatureType type)
    {
        return setEntityName(type.getTypeName());
    }
*/
    public EntityType getEntityType()
    {
        return EntityType.fromName(entityCompound.getString("id").replace("minecraft:", ""));
    }

    public NMSSpawnData setEntityType(final EntityType et)
    {
        return setEntityName(et.getName());
    }

    public NMSSpawnData setLeftHanded(final boolean isLeft)
    {
        entityCompound.setBoolean("LeftHanded", isLeft);
        return this;
    }

    public int getWeight()
    {
        return compound.getInt("Weight");
    }

    public NMSSpawnData setWeight(final int weight)
    {
        compound.setInt("Weight", weight);
        return this;
    }

    /**
     * Set the armor items of the mob
     *
     * @param items 0 = FEET. 1 = LEGS. 2 = CHEST. 3 = HEAD. Input an empty vararg for remove all the equipment or add a null value in the array
     * @return
     */
    public NMSSpawnData setArmorItems(final org.bukkit.inventory.ItemStack... items)
    {
        NBTList armorList = entityCompound.getWrappedList("ArmorItems", NBTType.COMPOUND);
        IntStream.range(0, 4).forEach(x ->
        {
            NMSStackCompound stack = new NMSStackCompound(items.length >= x + 1 ? items[x] : null);
            armorList.addNBT(stack.saveNMSItemStack());
        });
        return this;
    }

    /**
     * Set the hand items of the mob
     *
     * @param mainHand The item in the main hand
     * @param offHand  The item in the off hand
     * @return
     */
    public NMSSpawnData setHandItems(final @Nullable org.bukkit.inventory.ItemStack mainHand, final @Nullable org.bukkit.inventory.ItemStack offHand)
    {
        NBTList handList = entityCompound.getWrappedList("HandItems", NBTType.COMPOUND);
        handList.clear();

        Stream.of(mainHand, offHand).map(NMSStackCompound::new).map(NMSStackCompound::saveNMSItemStack).forEach(handList::addNBT);
        return this;
    }

    public NBTCompound getCompound()
    {
        return compound;
    }

    public NBTCompound getEntityCompound()
    {
        return entityCompound;
    }

    protected MobSpawnerData getMobSpawnerData()
    {
        return new MobSpawnerData(compound.getBase());
    }

    @Override
    public String toString()
    {
        return compound.toString();
    }
}
