package me.perfectpixel.fullpvp;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;

import me.perfectpixel.fullpvp.chest.SupplierChest;
import me.perfectpixel.fullpvp.loader.CommandsLoader;
import me.perfectpixel.fullpvp.loader.EventsLoader;
import me.perfectpixel.fullpvp.modules.MainModule;

import me.yushust.inject.Inject;
import me.yushust.inject.Injector;
import me.yushust.inject.InjectorFactory;
import me.yushust.inject.name.Named;

import org.bukkit.plugin.java.JavaPlugin;

public class FullPVP extends JavaPlugin {

    @Inject
    @Named("chests")
    private Storage<SupplierChest, String> supplierChestStorage;

    @Inject
    private EventsLoader eventsLoader;

    @Inject
    private CommandsLoader commandsLoader;

    @Inject
    private PlaceholderExpansion placeholderExpansion;

    public void onEnable() {
        Injector injector = InjectorFactory.create(new MainModule(this));
        injector.injectMembers(this);

        placeholderExpansion.register();

        commandsLoader.load();
        eventsLoader.load();

        supplierChestStorage.loadAll();
    }

    public void onDisable() {
        supplierChestStorage.saveAll();
    }

}