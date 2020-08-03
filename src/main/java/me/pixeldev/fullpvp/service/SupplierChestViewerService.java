package me.pixeldev.fullpvp.service;

import me.pixeldev.fullpvp.Storage;
import me.pixeldev.fullpvp.chest.viewer.UserViewer;

import team.unnamed.inject.Inject;

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