package me.parozzz.reflex.serializable.json.special.general;

import me.parozzz.reflex.serializable.json.special.SerializationRule;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.json.simple.JSONObject;

public class LocationSerializationRule extends SerializationRule<Location>
{
    public LocationSerializationRule()
    {
        super(Location.class);
    }

    @Override
    public Object serialize(Location loc)
    {
        JSONObject json = new JSONObject();

        json.put("World", loc.getWorld().getName());
        json.put("X", loc.getX());
        json.put("Y", loc.getY());
        json.put("Z", loc.getZ());

        return json;
    }

    @Override
    public Location deserialize(Object obj)
    {
        if(!(obj instanceof JSONObject))
        {
            return null;
        }

        JSONObject json = (JSONObject) obj;

        World world = Bukkit.getWorld(json.get("World").toString());
        double x = (double) json.get("X");
        double y = (double) json.get("Y");
        double z = (double) json.get("Z");

        return new Location(world, x, y, z);
    }
}
