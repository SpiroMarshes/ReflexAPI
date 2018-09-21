/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.parozzz.reflex.nms.spawner;

import me.parozzz.reflex.nms.nbt.NBTCompound;
import me.parozzz.reflex.nms.nbt.NBTList;
import me.parozzz.reflex.nms.nbt.NBTType;
import me.parozzz.reflex.tools.Validator;
import me.parozzz.reflex.utilities.ReflectionUtil;
import net.minecraft.server.v1_13_R2.NBTTagCompound;
import net.minecraft.server.v1_13_R2.TileEntityMobSpawner;
import org.apache.commons.lang3.Validate;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.craftbukkit.v1_13_R2.block.CraftBlockEntityState;

import javax.annotation.Nullable;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Paros
 */
public interface NMSSpawner
{
    enum SpawnerDataEnum
    {
        PLACE_DELAY("Delay"),
        SPAWN_RANGE("SpawnRange"),
        REQUIRED_PLAYER_RANGE("RequiredPlayerRange"),
        MAX_NEARBY_ENTITIES("MaxNearbyEntities"),
        MIN_SPAWN_DELAY("MinSpawnDelay"),
        MAX_SPAWN_DELAY("MaxSpawnDelay"),
        SPAWN_COUNT("SpawnCount");

        private final String nmsTag;

        SpawnerDataEnum(final String nmsTag)
        {
            this.nmsTag = nmsTag;
        }

        public String getTag()
        {
            return nmsTag;
        }
    }

    Method getSpawnerTileEntityMethod = ReflectionUtil.getMethod(CraftBlockEntityState.class, "getTileEntity");
    static TileEntityMobSpawner getTileEntitySpawner(final CreatureSpawner spawner)
    {
        return Validator.validateMethod(getSpawnerTileEntityMethod, spawner, TileEntityMobSpawner.class);
    }

    /*
        private final NBTTagCompound snapshot;

        public NMSSpawner()
        {
            snapshot = new NBTTagCompound();
            snapshot.setString("id", "minecraft:mob_spawner");
        }

        public NMSSpawner(final CreatureSpawner cs)
        {
            this();

            var mobSpawner = NMSSpawner.getTileEntitySpawner(cs);
            //(TileEntityMobSpawner) ((CraftWorld) cs.getWorld()).getHandle().getTileEntity(new BlockPosition(cs.getX(), cs.getY(), cs.getZ()));
            mobSpawner.getSpawner().b(snapshot);
            snapshot.remove("Delay"); // Remove the delay of the spawning. Allows spawners to stack properly
        }

        public NMSSpawner(final NMSStackCompound stack)
        {
            snapshot = stack.getWrappedCompound("BlockEntityTag").getBase();
            if (!snapshot.getString("id").contains("mob_spawner")) //Checks if id has "mob_spawner" in it. (If it doesn't have the id, return an empty String)
            {
                snapshot.setString("id", "minecraft:mob_spawner");
            }
        }

        public NMSSpawner(final org.bukkit.inventory.ItemStack itemStack)
        {
            this(new NMSStackCompound(itemStack));
        }
    */
    default short getData(final SpawnerDataEnum data)
    {
        return getSnapshot().getShort(data.getTag());
    }

    default void setData(final SpawnerDataEnum data, final short value)
    {
        getSnapshot().setShort(data.getTag(), value);
    }

    default NMSSpawnData getSpawnData()
    {
        return new NMSSpawnData(getWrappedSnapshot().getWrappedCompound("SpawnData"));
    }

    default void setSpawnData(final NMSSpawnData data)
    {
        getWrappedSnapshot().setNBT("SpawnData", data.getEntityCompound());
    }

    default void addSpawnPotential(final NMSSpawnData newData)
    {
        Validate.notNull(newData);

        NBTList spawnDataList = getWrappedSnapshot().getWrappedList("SpawnPotentials", NBTType.COMPOUND);
        spawnDataList.addNBT(newData.getCompound());
    }

    default List<NMSSpawnData> getSpawnPotentials()
    {
        NBTList potentialList = getWrappedSnapshot().getWrappedList("SpawnPotentials", NBTType.COMPOUND); // Compound id = 10
        return IntStream.range(0, potentialList.size())
                .mapToObj(potentialList::getWrappedCompound)
                .map(NMSSpawnData::new)
                .collect(Collectors.toList());
    }

    default @Nullable NMSSpawnData removePotentialSpawnDataAt(final int index)
    {
        if(index < 0)
        {
            return null;
        }

        NBTList potentialList = getWrappedSnapshot().getWrappedList("SpawnPotentials", NBTType.COMPOUND); // Compound id = 10
        if(index > potentialList.size())
        {
            return null;
        }

        NMSSpawnData removedSpawnData = new NMSSpawnData(potentialList.getWrappedCompound(index));
        potentialList.remove(index);

        return removedSpawnData;
    }

    default NMSSpawnData getPotentialSpawnDataAt(final int index)
    {
        if(index < 0)
        {
            return null;
        }

        NBTList potentialList = getWrappedSnapshot().getWrappedList("SpawnPotentials", NBTType.COMPOUND); // Compound id = 10
        if(index > potentialList.size())
        {
            return null;
        }

        return new NMSSpawnData(potentialList.getWrappedCompound(index));
    }

    default void setSpawnPotentials(final List<NMSSpawnData> spawnDataList)
    {
        NBTCompound wrappedSnapshot = getWrappedSnapshot();
        if(spawnDataList == null || spawnDataList.isEmpty())
        {
            wrappedSnapshot.removeKey("SpawnPotentials");
            return;
        }

        NBTList potentialList = wrappedSnapshot.getWrappedList("SpawnPotentials", NBTType.COMPOUND);
        potentialList.clear();

        spawnDataList.stream().map(NMSSpawnData::getCompound).forEach(potentialList::addNBT);
    }

    default int getSpawnPotentialsSize()
    {
        return getWrappedSnapshot().getWrappedList("SpawnPotentials", NBTType.COMPOUND).size();
    }

    default void clearSpawnPotentials()
    {
        getWrappedSnapshot().removeKey("SpawnPotentials");
    }

    NBTCompound getWrappedSnapshot();

    default NBTTagCompound getSnapshot()
    {
        return getWrappedSnapshot().getBase();
    }
}
