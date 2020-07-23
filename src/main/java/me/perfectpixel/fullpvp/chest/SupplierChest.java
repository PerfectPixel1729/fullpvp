package me.perfectpixel.fullpvp.chest;

import me.perfectpixel.fullpvp.utils.LocationSerializable;
import org.bukkit.Location;
import org.bukkit.inventory.Inventory;

public interface SupplierChest extends LocationSerializable {

    String getName();

    Inventory getInventory();

    Location getLocation();

}