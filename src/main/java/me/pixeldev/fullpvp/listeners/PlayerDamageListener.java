package me.pixeldev.fullpvp.listeners;

import me.pixeldev.fullpvp.Cache;
import me.pixeldev.fullpvp.combatlog.CombatLogAnnouncer;
import me.pixeldev.fullpvp.files.FileCreator;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import team.unnamed.inject.Inject;
import team.unnamed.inject.name.Named;

import java.util.UUID;

public class PlayerDamageListener implements Listener {

    @Inject
    @Named("combat")
    private Cache<UUID, Integer> combatLogCache;

    @Inject
    @Named("config")
    private FileCreator config;

    @Inject
    private CombatLogAnnouncer combatLogAnnouncer;

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        Entity target = event.getEntity();

        if (!(damager instanceof Player) || !(target instanceof Player)) {
            return;
        }

        int duration = config.getInt("combatlog.duration");

        combatLogCache.add(damager.getUniqueId(), duration);
        combatLogCache.add(target.getUniqueId(), duration);

        combatLogAnnouncer.sendPlayerLog((Player) damager);
        combatLogAnnouncer.sendPlayerLog((Player) target);
    }

}
