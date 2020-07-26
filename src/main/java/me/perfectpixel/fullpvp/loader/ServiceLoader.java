package me.perfectpixel.fullpvp.loader;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.perfectpixel.fullpvp.Storage;
import me.perfectpixel.fullpvp.chest.SupplierChest;
import me.yushust.inject.Inject;
import me.yushust.inject.name.Named;
import org.bukkit.Location;

public class ServiceLoader implements Loader {

    @Inject
    private EventsLoader eventsLoader;

    @Inject
    private CommandsLoader commandsLoader;

    @Inject
    private TickLoader tickLoader;

    @Inject
    @Named("chests")
    private Storage<SupplierChest, Location> supplierChestStorage;

    @Inject
    private PlaceholderExpansion placeholderExpansion;

    @Override
    public void load() {
        tickLoader.load();
        commandsLoader.load();
        eventsLoader.load();

        placeholderExpansion.register();
        supplierChestStorage.loadAll();
    }

}