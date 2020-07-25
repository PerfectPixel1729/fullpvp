package me.perfectpixel.fullpvp.loader;

import me.perfectpixel.fullpvp.FullPVP;
import me.perfectpixel.fullpvp.listeners.PlayerDeathListener;
import me.perfectpixel.fullpvp.listeners.PlayerJoinListener;
import me.perfectpixel.fullpvp.listeners.PlayerQuitListener;

import me.yushust.inject.Inject;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import team.unnamed.gui.listeners.MenuListeners;

public class EventsLoader implements Loader {

    @Inject
    private FullPVP fullPVP;

    @Inject private PlayerJoinListener playerJoinListener;
    @Inject private PlayerDeathListener playerDeathListener;
    @Inject private PlayerQuitListener playerQuitListener;

    @Override
    public void load() {
        registerListeners(
                playerJoinListener,
                playerQuitListener,
                playerDeathListener,
                new MenuListeners()
        );
    }

    private void registerListeners(Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getServer().getPluginManager().registerEvents(listener, fullPVP);
        }
    }

}