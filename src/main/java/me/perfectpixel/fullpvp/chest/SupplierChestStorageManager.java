package me.perfectpixel.fullpvp.chest;

import me.perfectpixel.fullpvp.Storage;
import me.perfectpixel.fullpvp.files.FileManager;
import me.perfectpixel.fullpvp.utils.LocationSerializable;

import me.yushust.inject.Inject;
import me.yushust.inject.name.Named;

import org.bukkit.Location;

import java.util.*;

public class SupplierChestStorageManager implements Storage<SupplierChest, Location> {

    @Inject
    @Named("chests")
    private FileManager chests;

    private static final Map<Location, SupplierChest> SUPPLIER_CHESTS = new HashMap<>();
    
    @Override
    public Map<Location, SupplierChest> get() {
        return SUPPLIER_CHESTS;
    }

    @Override
    public Optional<SupplierChest> find(Location location) {
        return Optional.ofNullable(SUPPLIER_CHESTS.get(location));
    }

    @Override
    public Optional<SupplierChest> findFromData(Location location) { return Optional.empty(); }

    public static boolean containsChest(String name) {
        return SUPPLIER_CHESTS.values().stream().anyMatch(supplierChest -> supplierChest.getName().equals(name));
    }

    @Override
    public void save(Location location) {
        find(location).ifPresent(supplierChest -> chests.set("chests." + location, supplierChest.serialize()));

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

        chests.getConfigurationSection("chests").getKeys(false).forEach(location -> findFromData(LocationSerializable.fromString(location)).ifPresent(supplierChest -> SUPPLIER_CHESTS.put(LocationSerializable.fromString(location), supplierChest)));
    }

}