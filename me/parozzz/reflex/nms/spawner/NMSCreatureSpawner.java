package me.parozzz.reflex.nms.spawner;

import me.parozzz.reflex.nms.nbt.NBTCompound;
import net.minecraft.server.v1_13_R2.NBTTagCompound;
import net.minecraft.server.v1_13_R2.TileEntityMobSpawner;
import org.bukkit.block.CreatureSpawner;

public class NMSCreatureSpawner implements NMSSpawner
{
    public static NMSCreatureSpawner of(CreatureSpawner spawner)
    {
        TileEntityMobSpawner mobSpawner = NMSSpawner.getTileEntitySpawner(spawner);
        return mobSpawner != null ? new NMSCreatureSpawner(mobSpawner) : null;
    }

    private final TileEntityMobSpawner mobSpawner;
    private final NBTCompound spawnerCompound;

    private NMSCreatureSpawner(final TileEntityMobSpawner mobSpawner)
    {
        this.mobSpawner = mobSpawner;

        NBTTagCompound nmsCompound = mobSpawner.getSpawner().b(new NBTTagCompound());
        nmsCompound.remove("Delay"); // Remove the delay of the spawning. Allows spawners to stack properly
        spawnerCompound = new NBTCompound(nmsCompound);//Save data into a new NBTTagCompound and returns it.
    }

    public void update()
    {
        mobSpawner.getSpawner().a(spawnerCompound.getBase()); //Save the compound to the Spawner. Needed, since b#NBTTagCompound return a compound copy.
    }

    @Override
    public NBTCompound getWrappedSnapshot()
    {
        return spawnerCompound;
    }

}
