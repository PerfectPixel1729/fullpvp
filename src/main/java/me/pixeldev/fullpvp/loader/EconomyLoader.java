package me.pixeldev.fullpvp.loader;

import me.pixeldev.fullpvp.economy.EconomyWrapper;

import team.unnamed.inject.Inject;

public final class EconomyLoader implements Loader {

    @Inject
    EconomyWrapper economyWrapper;

    @Override
    public void load() {
        economyWrapper.setup();
    }

}