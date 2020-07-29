package me.perfectpixel.fullpvp.loader;

import me.perfectpixel.fullpvp.FullPVP;
import me.perfectpixel.fullpvp.listeners.*;
import me.perfectpixel.fullpvp.listeners.chest.SupplierChestListeners;
import me.perfectpixel.fullpvp.listeners.clan.ClanListeners;
import me.yushust.inject.Inject;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import team.unnamed.gui.listeners.MenuListeners;

public final class EventsLoader implements Loader {

    @Inject
    private FullPVP fullPVP;

    @Inject
    private PlayerJoinListener playerJoinListener;

    @Inject
    private PlayerDeathListener playerDeathListener;

    @Inject
    private PlayerQuitListener playerQuitListener;

    @Inject
    private PlayerInteractListener playerInteractListener;

    @Inject
    private SupplierChestListeners supplierChestListeners;

    @Inject
    private PearlListeners pearlListeners;

    @Inject
    private ClanListeners clanListeners;

    @Override
    public void load() {
        registerListeners(
                playerJoinListener,
                playerQuitListener,
                playerDeathListener,
                playerInteractListener,
                supplierChestListeners,
                pearlListeners,
                clanListeners,
                new MenuListeners()
        );
    }

    private void registerListeners(Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getServer().getPluginManager().registerEvents(listener, fullPVP);
        }
    }

}