package me.perfectpixel.fullpvp.service;

import me.perfectpixel.fullpvp.Storage;
import me.perfectpixel.fullpvp.user.User;

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