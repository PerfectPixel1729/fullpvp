package me.pixeldev.fullpvp.service;

import me.pixeldev.fullpvp.Storage;
import me.pixeldev.fullpvp.kit.Kit;

import team.unnamed.inject.Inject;

public final class KitService implements Service {

    @Inject
    private Storage<Integer, Kit> kitStorage;

    @Override
    public void start() {
        kitStorage.loadAll();
    }

    @Override
    public void stop() {
        kitStorage.saveAll();
    }

}