package me.pixeldev.fullpvp.economy;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

import team.unnamed.inject.Inject;

public class DefaultEconomyWrapper implements EconomyWrapper {

    @Inject
    private Plugin plugin;

    private Economy economy;

    @Override
    public Economy get() {
        return economy;
    }

    @Override
    public void setup() {
        if (plugin.getServer().getPluginManager().getPlugin("Vault") == null) {
            plugin.getLogger().severe("§cVAULT NO FUE ENCONTRADO, POR FAVOR COLOCA EL PLUGIN.");

            return;
        }

        RegisteredServiceProvider<Economy> registeredServiceProvider = plugin.getServer().getServicesManager().getRegistration(Economy.class);

        if (registeredServiceProvider == null) {
            plugin.getLogger().severe("§cALGO INESPERADO A SUCEDIDO CON VAULT");

            return;
        }

        economy = registeredServiceProvider.getProvider();
    }
}