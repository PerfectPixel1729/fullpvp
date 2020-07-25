package me.perfectpixel.fullpvp.chest.creator;

import org.bukkit.Location;

public interface UserCreator {

    UserCreatorInventory getSavedInventory();

    Location getChestLocation();

    void setChestLocation(Location location);

}