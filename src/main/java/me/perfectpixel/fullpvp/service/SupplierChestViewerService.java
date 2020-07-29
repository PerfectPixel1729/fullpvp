package me.perfectpixel.fullpvp.service;

import me.perfectpixel.fullpvp.Storage;
import me.perfectpixel.fullpvp.chest.viewer.UserViewer;

import me.yushust.inject.Inject;

import java.util.UUID;

public final class SupplierChestViewerService implements Service {

    @Inject
    private Storage<UUID, UserViewer> userViewerStorage;

    @Override
    public void start() {
        userViewerStorage.loadAll();
    }

    @Override
    public void stop() {
        userViewerStorage.saveAll();
    }

}