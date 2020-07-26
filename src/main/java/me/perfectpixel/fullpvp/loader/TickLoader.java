package me.perfectpixel.fullpvp.loader;

import me.perfectpixel.fullpvp.FullPVP;
import me.perfectpixel.fullpvp.event.FullPVPTickEvent;
import me.perfectpixel.fullpvp.utils.TickCause;

import me.yushust.inject.Inject;

import org.bukkit.Bukkit;

public class TickLoader implements Loader {

    @Inject
    private FullPVP fullPVP;

    private int time = 0;

    @Override
    public void load() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(fullPVP, runnable(), 0L, 1L);
    }

    private Runnable runnable() {
        return () -> {
            time++;

            Bukkit.getPluginManager().callEvent(new FullPVPTickEvent(TickCause.MILLISECOND));

            if(time >= 20) {
                time = 0;
                Bukkit.getPluginManager().callEvent(new FullPVPTickEvent(TickCause.SECOND));
            }
        };
    }

}