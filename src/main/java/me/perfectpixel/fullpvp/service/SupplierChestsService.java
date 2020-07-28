package me.perfectpixel.fullpvp.service;

import me.perfectpixel.fullpvp.Storage;
import me.perfectpixel.fullpvp.chest.SupplierChest;

import me.yushust.inject.Inject;

import org.bukkit.Location;

public final class SupplierChestsService implements Service {

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