package me.perfectpixel.fullpvp.chest;

import me.perfectpixel.fullpvp.Storage;
import me.perfectpixel.fullpvp.files.FileManager;

import me.perfectpixel.fullpvp.utils.LocationSerializable;
import me.yushust.inject.Inject;
import me.yushust.inject.name.Named;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class SupplierChestStorageManager implements Storage<SupplierChest, String> {

    @Inject
    @Named("chests")
    private FileManager chests;

    private final Map<String, SupplierChest> supplierChests = new HashMap<>();


    @Override
    public Map<String, SupplierChest> get() {
        return supplierChests;
    }

    @Override
    public Optional<SupplierChest> find(String s) {
        return Optional.ofNullable(supplierChests.get(s));
    }

    @Override
    public Optional<SupplierChest> findFromData(String s) {
        if (!chests.contains("chests." + s)) {
            return Optional.empty();
        }

        return Optional.of(new DefaultSupplierChest((Map<Integer, ItemStack>) chests.get("chests." + s + ".items"), LocationSerializable.fromString(chests.getString("chests." + s + ".location"))));
    }

    @Override
    public void save(String s) {
        find(s).ifPresent(supplierChest -> chests.set("chests." + s, supplierChest.serialize()));

        remove(s);
    }

    @Override
    public void remove(String s) {
        supplierChests.remove(s);
    }

    @Override
    public void add(String s, SupplierChest supplierChest) {
        supplierChests.put(s, supplierChest);
    }

    @Override
    public void saveAll() {
        supplierChests.forEach((name, supplierChest) -> save(name));

        chests.save();
    }

    @Override
    public void loadAll() {
        if (!chests.contains("chests")) {
            return;
        }

        chests.getConfigurationSection("chests").getKeys(false).forEach(name -> findFromData(name).ifPresent(supplierChest -> supplierChests.put(name, supplierChest)));
    }

}