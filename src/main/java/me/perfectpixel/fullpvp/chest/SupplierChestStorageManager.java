package me.perfectpixel.fullpvp.chest;

import me.perfectpixel.fullpvp.Storage;
import me.perfectpixel.fullpvp.files.FileManager;

import me.perfectpixel.fullpvp.utils.LocationSerializable;
import me.yushust.inject.Inject;
import me.yushust.inject.name.Named;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class SupplierChestStorageManager implements Storage<SupplierChest, Location> {

    @Inject @Named("chests") private FileManager chests;

    private final Set<SupplierChest> supplierChests = new HashSet<>();

    @Override
    public Set<SupplierChest> get() {
        return supplierChests;
    }

    @Override
    public Optional<SupplierChest> find(Location location) {
        SupplierChest supplierChest = null;

        for (SupplierChest findingSupplierChest : supplierChests) {
            if (findingSupplierChest.getLocation().equals(location)) {
                supplierChest = findingSupplierChest;
            }
        }

        return Optional.ofNullable(supplierChest);
    }

    @Override
    public Optional<SupplierChest> findFromData(Location location) {
        if (!chests.contains("chests")) {
            return Optional.empty();
        }

        SupplierChest supplierChest = null;

        for (String name : chests.getConfigurationSection("chests").getKeys(false)) {
            Location chestLocation = LocationSerializable.fromString(chests.getString("chests." + name + ".location"));

            if (chestLocation.equals(location)) {
                supplierChest = new DefaultSupplierChest(name, (Map<Integer, ItemStack>) chests.get("chests." + name + ".items"), chestLocation);
            }
        }

        return Optional.ofNullable(supplierChest);
    }

    @Override
    public void save(Location location) {
        find(location).ifPresent(supplierChest -> chests.set("chests." + supplierChest.getName(), supplierChest.serialize()));

        remove(location);
    }

    @Override
    public void remove(Location location) {
        find(location).ifPresent(supplierChests::remove);
    }

    @Override
    public void add(SupplierChest supplierChest) {
        supplierChests.add(supplierChest);
    }

    @Override
    public void saveAll() {
        supplierChests.forEach(supplierChest -> chests.set("chests." + supplierChest, supplierChest.serialize()));

        chests.save();
    }

    @Override
    public void loadAll() {
        if (!chests.contains("chests")) {
            return;
        }

        chests.getConfigurationSection("chests").getKeys(false).forEach(name ->
                supplierChests.add(
                        new DefaultSupplierChest(
                                name,
                                (Map<Integer, ItemStack>) chests.get("chests." + name + ".items"),
                                LocationSerializable.fromString(chests.getString("chests." + name + ".location"))
                        )
                )
        );
    }

}