package me.perfectpixel.fullpvp;

import me.perfectpixel.fullpvp.listeners.PlayerDeathListener;
import me.perfectpixel.fullpvp.listeners.PlayerJoinListener;
import me.perfectpixel.fullpvp.listeners.PlayerQuitListener;

import me.yushust.inject.Inject;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public class EventsLoader {

    @Inject
    private FullPVP fullPVP;

    @Inject private PlayerJoinListener playerJoinListener;
    @Inject private PlayerDeathListener playerDeathListener;
    @Inject private PlayerQuitListener playerQuitListener;

    public void register() {
        registerListeners(
                playerJoinListener,
                playerQuitListener,
                playerDeathListener
        );
    }

    private void registerListeners(Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getServer().getPluginManager().registerEvents(listener, fullPVP);
        }
    }

}