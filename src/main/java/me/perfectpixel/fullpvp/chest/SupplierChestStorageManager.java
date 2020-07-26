package me.perfectpixel.fullpvp.chest;

import me.perfectpixel.fullpvp.Storage;
import me.perfectpixel.fullpvp.files.FileManager;
import me.perfectpixel.fullpvp.utils.ItemSerializable;
import me.perfectpixel.fullpvp.utils.LocationSerializable;

import me.yushust.inject.Inject;
import me.yushust.inject.name.Named;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class SupplierChestStorageManager implements Storage<SupplierChest, Location> {

    @Inject
    @Named("chests")
    private FileManager chests;

    private static final ConcurrentHashMap<Location, SupplierChest> SUPPLIER_CHESTS = new ConcurrentHashMap<>();

    @Override
    public Map<Location, SupplierChest> get() {
        return SUPPLIER_CHESTS;
    }

    @Override
    public Optional<SupplierChest> find(Location location) {
        for (Map.Entry<Location, SupplierChest> supplierEntry : SUPPLIER_CHESTS.entrySet()) {
            if (location.equals(supplierEntry.getKey())) {
                return Optional.of(supplierEntry.getValue());
            }
        }

        return Optional.empty();
    }

    @Override
    public Optional<SupplierChest> findFromData(Location location) {
        return Optional.empty();
    }

    public static boolean containsChest(String name) {
        return SUPPLIER_CHESTS.values().stream().anyMatch(supplierChest -> supplierChest.getName().equals(name));
    }

    @Override
    public void save(Location location) {
        find(location).ifPresent(supplierChest -> {
            chests.set("chests." + supplierChest.getName() + ".items", supplierChest.serialize());

            chests.set("chests." + supplierChest.getName() + ".location", LocationSerializable.fromBukkit(location));
        });

        remove(location);
    }

    @Override
    public void remove(Location location) {
        SUPPLIER_CHESTS.remove(location);
    }

    @Override
    public void add(Location location, SupplierChest supplierChest) {
        SUPPLIER_CHESTS.put(location, supplierChest);
    }

    @Override
    public void saveAll() {
        SUPPLIER_CHESTS.forEach((name, supplierChest) -> save(name));

        chests.save();
    }

    @Override
    public void loadAll() {
        if (!chests.contains("chests")) {
            return;
        }

        chests.getConfigurationSection("chests").getKeys(false).forEach(name -> {
            Map<Integer, ItemStack> items = new HashMap<>();

            chests.getConfigurationSection("chests." + name + ".items").getKeys(false).forEach(s ->
                    items.put(
                            Integer.parseInt(s),
                            ItemSerializable.decodeItem(chests.getString("chests." + name + ".items." + s))
                    )
            );

            SUPPLIER_CHESTS.put(
                    LocationSerializable.fromString(chests.getString("chests." + name + ".location")),
                    new DefaultSupplierChest(
                            items,
                            name
                    )
            );
        });
    }

}