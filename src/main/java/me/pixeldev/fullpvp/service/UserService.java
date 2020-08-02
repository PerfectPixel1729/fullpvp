package me.pixeldev.fullpvp.service;

import me.pixeldev.fullpvp.Storage;
import me.pixeldev.fullpvp.user.User;

import me.yushust.inject.Inject;

import java.util.UUID;

public final class UserService implements Service {

    @Inject
    private Storage<UUID, User> userStorage;

    @Override
    public void start() {
        userStorage.loadAll();
    }

    @Override
    public void stop() {
        userStorage.saveAll();
    }

}