package me.perfectpixel.fullpvp.chest;

import me.perfectpixel.fullpvp.utils.LocationSerializable;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public interface SupplierChest extends LocationSerializable, ConfigurationSerializable {

    Map<Integer, ItemStack> getItems();

    Location getLocation();

}