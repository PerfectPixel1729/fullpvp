package me.perfectpixel.fullpvp.service;

import me.perfectpixel.fullpvp.Storage;
import me.perfectpixel.fullpvp.clans.Clan;

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