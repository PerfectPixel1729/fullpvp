package me.pixeldev.fullpvp.chest;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public interface SupplierChest extends ConfigurationSerializable {

    String getName();

    Map<Integer, ItemStack> getItems();

    void setItems(Map<Integer, ItemStack> items);

}