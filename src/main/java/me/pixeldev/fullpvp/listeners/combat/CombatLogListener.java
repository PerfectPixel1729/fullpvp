package me.pixeldev.fullpvp.listeners.combat;

import me.pixeldev.fullpvp.Cache;
import me.pixeldev.fullpvp.combatlog.CombatLogAnnouncer;
import me.pixeldev.fullpvp.event.FullPVPTickEvent;
import me.pixeldev.fullpvp.utils.TickCause;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import team.unnamed.inject.Inject;
import team.unnamed.inject.name.Named;

import java.util.UUID;

public class CombatLogListener implements Listener {

    @Inject
    @Named("combat")
    private Cache<UUID, Integer> combatLogCache;

    @Inject
    private CombatLogAnnouncer combatLogAnnouncer;

    @EventHandler
    public void onServerTick(FullPVPTickEvent event) {
        if (event.getCause() != TickCause.SECOND) {
            return;
        }

        combatLogCache.get().forEach((uuid, time) -> {
            Player player = Bukkit.getPlayer(uuid);

            int newTime = time - 1;

            combatLogCache.get().put(uuid, newTime);

            if (newTime == 0) {
                combatLogAnnouncer.sendPlayerFinishLog(player);

                combatLogCache.remove(uuid);
            }
        });
    }

    @EventHandler
    public void onServerTwoSeconds(FullPVPTickEvent event) {
        if (event.getCause() != TickCause.TWO_SECONDS) {
            return;
        }

        combatLogCache.get().keySet().forEach(uuid -> combatLogAnnouncer.sendPlayerLog(Bukkit.getPlayer(uuid)));
    }

}