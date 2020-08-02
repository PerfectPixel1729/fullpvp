package me.pixeldev.fullpvp.service;

import me.pixeldev.fullpvp.Storage;
import me.pixeldev.fullpvp.chest.SupplierChest;

import me.yushust.inject.Inject;

import org.bukkit.Location;

public final class SupplierChestService implements Service {

    @Inject
    private Storage<Location, SupplierChest> supplierChestStorage;

    @Override
    public void start() {
        supplierChestStorage.loadAll();
    }

    @Override
    public void stop() {
        supplierChestStorage.saveAll();
    }

}