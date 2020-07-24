package me.perfectpixel.fullpvp.chest;

import lombok.AllArgsConstructor;
import lombok.Getter;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public class DefaultSupplierChest implements SupplierChest {

    private final String name;
    private final Map<Integer, ItemStack> items;
    private final Location location;

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> supplierChestMap = new HashMap<>();

        supplierChestMap.put("location", fromBukkit());
        items.forEach((slot, item) -> supplierChestMap.put("items." + slot, item.serialize()));

        return supplierChestMap;
    }

}