package me.pixeldev.fullpvp.service;

import me.pixeldev.fullpvp.Storage;
import me.pixeldev.fullpvp.clans.Clan;

import me.yushust.inject.Inject;

public final class ClanService implements Service {

    @Inject
    private Storage<String, Clan> clanStorage;

    @Override
    public void start() {
        clanStorage.loadAll();
    }

    @Override
    public void stop() {
        clanStorage.saveAll();
    }

}