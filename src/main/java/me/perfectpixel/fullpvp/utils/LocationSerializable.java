package me.perfectpixel.fullpvp.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public interface LocationSerializable {

    Location getLocation();

    default Location fromString(String location) {
        String[] split = location.split(";");

        return new Location(Bukkit.getWorld(split[0]), Double.parseDouble(split[1]), Double.parseDouble(split[2]), Double.parseDouble(split[3]));
    }

    default String fromBukkit() {
        Location location = getLocation();

        return location.getWorld().getName() + ";" + location.getX() + ";" + location.getY() + ";" + location.getZ();
    }

}