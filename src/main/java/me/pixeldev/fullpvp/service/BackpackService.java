package me.pixeldev.fullpvp.service;

import me.pixeldev.fullpvp.Storage;
import me.pixeldev.fullpvp.backpack.user.BackpackUser;

import team.unnamed.inject.Inject;

import java.util.UUID;

public class BackpackService implements Service {

    @Inject
    private Storage<UUID, BackpackUser> backpackUserStorage;

    @Override
    public void start() {
        backpackUserStorage.loadAll();
    }

    @Override
    public void stop() {
        backpackUserStorage.saveAll();
    }

}