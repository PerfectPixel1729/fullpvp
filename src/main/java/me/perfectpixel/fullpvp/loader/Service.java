package me.perfectpixel.fullpvp.loader;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;

import me.perfectpixel.fullpvp.Storage;
import me.perfectpixel.fullpvp.chest.SupplierChest;

import me.perfectpixel.fullpvp.chest.viewer.UserViewer;
import me.yushust.inject.Inject;

import org.bukkit.Location;

import java.util.UUID;

public class Service implements Loader {

    @Inject
    private EventsLoader eventsLoader;

    @Inject
    private CommandsLoader commandsLoader;

    @Inject
    private TickLoader tickLoader;

    @Inject
    private Storage<Location, SupplierChest> supplierChestStorage;

    @Inject
    private Storage<UUID, UserViewer> userViewerStorage;

    @Inject
    private PlaceholderExpansion placeholderExpansion;

    @Override
    public void load() {
        tickLoader.load();
        commandsLoader.load();
        eventsLoader.load();

        placeholderExpansion.register();
        supplierChestStorage.loadAll();

        userViewerStorage.loadAll();
    }

    public void stop() {
        supplierChestStorage.saveAll();

        userViewerStorage.saveAll();
    }

}