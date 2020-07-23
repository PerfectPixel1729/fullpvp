package me.perfectpixel.fullpvp.chest;

import me.perfectpixel.fullpvp.Storage;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class SupplierChestStorageManager implements Storage<SupplierChest, String> {

    private final Set<SupplierChest> supplierChests = new HashSet<>();

    @Override
    public Set<SupplierChest> users() {
        return supplierChests;
    }

    @Override
    public Optional<SupplierChest> find(String s) {
        return Optional.empty();
    }

    @Override
    public Optional<SupplierChest> findFromData(String s) {
        return Optional.empty();
    }

    @Override
    public void save(String s) {

    }

    @Override
    public void remove(String s) {

    }

    @Override
    public void add(SupplierChest supplierChest) {

    }

}