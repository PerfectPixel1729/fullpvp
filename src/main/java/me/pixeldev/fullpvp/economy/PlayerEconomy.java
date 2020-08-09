package me.pixeldev.fullpvp.economy;

import org.bukkit.entity.Player;

import team.unnamed.inject.Inject;
import team.unnamed.inject.process.annotation.Singleton;

@Singleton
public class PlayerEconomy {

    @Inject
    private EconomyWrapper economyWrapper;

    public double getMoney(Player player) {
        return economyWrapper.get().getBalance(player);
    }

    public boolean hasEnoughMoney(Player player, int amount) {
        return economyWrapper.get().getBalance(player) >= amount;
    }

    public boolean hasMoney(Player player) {
        return economyWrapper.get().getBalance(player) > 0;
    }

    public boolean depositMoney(Player player, double amount) {
        return economyWrapper.get().depositPlayer(player, amount).transactionSuccess();
    }

    public boolean withdrawMoney(Player player, double amount) {
        return economyWrapper.get().withdrawPlayer(player, amount).transactionSuccess();
    }

}