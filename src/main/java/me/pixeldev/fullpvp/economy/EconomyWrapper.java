package me.pixeldev.fullpvp.economy;

import net.milkbowl.vault.economy.Economy;

public interface EconomyWrapper {

    Economy get();

    void setup();

}