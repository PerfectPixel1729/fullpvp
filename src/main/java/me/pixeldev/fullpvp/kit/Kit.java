package me.pixeldev.fullpvp.kit;

import me.pixeldev.fullpvp.utils.ItemSerializable;

import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface Kit {

    List<ItemStack> getArmorContents();

    List<ItemStack> getContents();

    default Map<String, Object> serializeArmor() {
        Map<String, Object> armorMap = new HashMap<>();

        for (int i = 0; i < getArmorContents().size(); i++) {
            armorMap.put(i + "", ItemSerializable.encodeItem(getArmorContents().get(i)));
        }

        return armorMap;
    }

    default Map<String, Object> serializeContents() {
        Map<String, Object> contentsMap = new HashMap<>();

        for (int i = 0; i < getContents().size(); i++) {
            contentsMap.put(i + "", ItemSerializable.encodeItem(getContents().get(i)));
        }

        return contentsMap;
    }

}