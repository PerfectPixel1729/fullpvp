package me.pixeldev.fullpvp.service;

import me.pixeldev.fullpvp.BasicManager;
import me.pixeldev.fullpvp.Storage;
import me.pixeldev.fullpvp.kit.Kit;

import org.bukkit.Location;

import team.unnamed.inject.InjectAll;

@InjectAll
public final class KitService implements Service {

    private Storage<Integer, Kit> kitStorage;
    private BasicManager<Location> supplierKitManager;

    @Override
    public void start() {
        kitStorage.loadAll();
        supplierKitManager.loadAll();
    }

    @Override
    public void stop() {
        kitStorage.saveAll();
        supplierKitManager.saveAll();
    }

}