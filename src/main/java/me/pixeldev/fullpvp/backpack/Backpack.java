package me.pixeldev.fullpvp.backpack;

import me.pixeldev.fullpvp.utils.ItemSerializable;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public interface Backpack extends ConfigurationSerializable {

    int getRows();

    void addRows();

    Map<Integer, ItemStack> getContents();

    void setContents(Map<Integer, ItemStack> contents);

    @Override
    default Map<String, Object> serialize() {
        Map<String, Object> backpackSerialized = new HashMap<>();

        getContents().forEach((slot, item) -> backpackSerialized.put(slot + "", ItemSerializable.encodeItem(item)));

        return backpackSerialized;
    }

}