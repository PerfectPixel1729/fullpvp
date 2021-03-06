package me.pixeldev.fullpvp.loader;

import me.pixeldev.fullpvp.FullPVP;
import me.pixeldev.fullpvp.event.FullPVPTickEvent;
import me.pixeldev.fullpvp.utils.TickCause;

import team.unnamed.inject.Inject;

import org.bukkit.Bukkit;

public final class TickLoader implements Loader {

    @Inject
    private FullPVP fullPVP;

    private int time = 20;
    private int two_seconds = 40;
    private int minute = 1200;

    @Override
    public void load() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(fullPVP, runnable(), 0L, 1L);
    }

    private Runnable runnable() {
        return () -> {
            time--;
            two_seconds--;
            minute--;

            Bukkit.getPluginManager().callEvent(new FullPVPTickEvent(TickCause.MILLISECOND));

            if(time == 0) {
                time = 20;

                Bukkit.getPluginManager().callEvent(new FullPVPTickEvent(TickCause.SECOND));
            }

            if (two_seconds == 0) {
                two_seconds = 40;

                Bukkit.getPluginManager().callEvent(new FullPVPTickEvent(TickCause.TWO_SECONDS));
            }

            if (minute == 0) {
                minute = 1200;

                Bukkit.getPluginManager().callEvent(new FullPVPTickEvent(TickCause.MINUTE));
            }
        };
    }

}