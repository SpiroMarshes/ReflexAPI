package me.parozzz.reflex.nms.spawner;

import me.parozzz.reflex.items.NMSStackCompound;
import me.parozzz.reflex.nms.nbt.NBTCompound;
import org.bukkit.block.CreatureSpawner;

public class NMSSpawnerStack implements NMSSpawner
{
    private final NBTCompound spawnerCompound;

    public NMSSpawnerStack(final NMSStackCompound stack)
    {
        spawnerCompound = stack.getWrappedCompound("BlockEntityTag");

        if(!spawnerCompound.getString("id").contains("mob_spawner")) //Checks if id has "mob_spawner" in it. (If it doesn't have the id, return an empty String)
        {
            spawnerCompound.setString("id", "minecraft:mob_spawner");
        }
    }

    public NMSSpawnerStack()
    {
        spawnerCompound = NBTCompound.getNew();
        spawnerCompound.setString("id", "minecraft:mob_spawner");
    }

    public void loadSpawner(final CreatureSpawner spawner)
    {
        NMSSpawner.getTileEntitySpawner(spawner).getSpawner().b(spawnerCompound.getBase());
        spawnerCompound.removeKey("Delay"); //Removing the Delay tag, useless here.
    }

    public void updateSpawner(final CreatureSpawner spawner)
    {
        NMSSpawner.getTileEntitySpawner(spawner).getSpawner().a(spawnerCompound.getBase());
    }

    @Override
    public NBTCompound getWrappedSnapshot()
    {
        return spawnerCompound;
    }
}
