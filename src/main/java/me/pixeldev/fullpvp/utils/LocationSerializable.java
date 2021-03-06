package me.pixeldev.fullpvp.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationSerializable {

    public static Location fromString(String location) {
        String[] split = location.split(";");

        return new Location(Bukkit.getWorld(split[0]), Double.parseDouble(split[1]), Double.parseDouble(split[2]), Double.parseDouble(split[3]));
    }

    public static String fromBukkit(Location location) {
        return location.getWorld().getName() + ";" + location.getX() + ";" + location.getY() + ";" + location.getZ();
    }

}