package me.perfectpixel.fullpvp.chest;

import lombok.AllArgsConstructor;
import lombok.Getter;

import me.perfectpixel.fullpvp.utils.ItemSerializable;

import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public class DefaultSupplierChest implements SupplierChest {

    private final Map<Integer, ItemStack> items;

    private final String name;

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> supplierChestMap = new HashMap<>();

        items.forEach((slot, item) -> supplierChestMap.put(slot + "", ItemSerializable.encodeItem(item)));

        return supplierChestMap;
    }

}