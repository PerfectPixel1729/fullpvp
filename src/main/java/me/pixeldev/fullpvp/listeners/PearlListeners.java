package me.pixeldev.fullpvp.listeners;

import me.pixeldev.fullpvp.Cache;
import me.pixeldev.fullpvp.event.FullPVPTickEvent;
import me.pixeldev.fullpvp.files.FileCreator;
import me.pixeldev.fullpvp.message.Message;
import me.pixeldev.fullpvp.packets.ActionbarMessenger;
import me.pixeldev.fullpvp.utils.TickCause;
import me.pixeldev.fullpvp.utils.TimeFormat;

import me.yushust.inject.Inject;

import me.yushust.inject.name.Named;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class PearlListeners implements Listener {

    @Inject
    private Cache<UUID, Integer> pearlsCache;

    @Inject
    private TimeFormat timeFormat;

    @Inject
    private Message message;

    @Inject
    private ActionbarMessenger actionbarMessenger;

    @Inject
    @Named("config")
    private FileCreator config;

    @EventHandler
    public void onServerTick(FullPVPTickEvent event) {
        if (event.getCause() != TickCause.SECOND) {
            return;
        }

        pearlsCache.get().forEach((uuid, time) -> {
            int newTime = time - 1;

            pearlsCache.get().put(uuid, newTime);

            Player player = Bukkit.getPlayer(uuid);

            actionbarMessenger.sendActionbar(player, message.getMessage(player, "pearl.actionbar")
                    .replace("%time%", timeFormat.format(newTime * 1000))
            );

            if (newTime == 0) {
                actionbarMessenger.sendActionbar(player, message.getMessage(player, "pearl.actionbar-finish"));

                pearlsCache.remove(uuid);
            }
        });
    }

    @EventHandler
    public void onLaunchPearl(ProjectileLaunchEvent event) {
        if (!(event.getEntity() instanceof EnderPearl)) {
            return;
        }

        Projectile projectile = event.getEntity();

        if (!(projectile.getShooter() instanceof Player)) {
            return;
        }

        Player player = (Player) projectile.getShooter();

        if (pearlsCache.find(player.getUniqueId()).isPresent()) {
            event.setCancelled(true);

            player.getInventory().addItem(new ItemStack(Material.ENDER_PEARL));

            player.sendMessage(message.getMessage(player, "pearl.already-cooldown")
                    .replace("%time%", timeFormat.format(pearlsCache.get().get(player.getUniqueId()) * 1000))
            );

            return;
        }

        int countdown = config.getInt("pearl.cooldown");

        pearlsCache.add(player.getUniqueId(), countdown);

        actionbarMessenger.sendActionbar(player, message.getMessage(player, "pearl.actionbar")
                .replace("%time%", timeFormat.format(countdown * 1000))
        );
    }

}