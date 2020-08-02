package me.pixeldev.fullpvp.chest.creator;

import org.bukkit.Location;

public interface UserCreator {

    CreatorInventory getSavedInventory();

    Location getChestLocation();

    void setChestLocation(Location location);

}