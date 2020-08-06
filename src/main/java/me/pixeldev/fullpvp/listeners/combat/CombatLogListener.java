package me.pixeldev.fullpvp.listeners.combat;

import me.pixeldev.fullpvp.Cache;
import me.pixeldev.fullpvp.Storage;
import me.pixeldev.fullpvp.clans.Clan;
import me.pixeldev.fullpvp.combatlog.announcer.CombatLogAnnouncer;
import me.pixeldev.fullpvp.event.FullPVPTickEvent;
import me.pixeldev.fullpvp.files.FileCreator;
import me.pixeldev.fullpvp.message.Message;
import me.pixeldev.fullpvp.user.User;
import me.pixeldev.fullpvp.utils.InventoryUtils;
import me.pixeldev.fullpvp.utils.TickCause;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.projectiles.ProjectileSource;

import team.unnamed.inject.InjectAll;
import team.unnamed.inject.name.Named;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@InjectAll
public class CombatLogListener implements Listener {

    private Storage<UUID, User> userStorage;
    private Storage<String, Clan> clanStorage;
    private Message message;
    private InventoryUtils inventoryUtils;
    private CombatLogAnnouncer combatLogAnnouncer;

    @Named("combat")
    private Cache<UUID, Integer> combatLogCache;

    @Named("config")
    private FileCreator config;

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

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (combatLogCache.exists(player.getUniqueId())) {
            combatLogCache.remove(player.getUniqueId());

            combatLogAnnouncer.sendPlayerDisconnect(player);

            player.setHealth(0);
        }
    }

    @EventHandler
    public void onPreProcessCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();

        if (combatLogCache.exists(player.getUniqueId())) {
            String command = event.getMessage().split("/")[1];

            List<String> blockedCommands = config.getStringList("combatlog.block-commands");

            if (blockedCommands.contains(command.toLowerCase())) {
                event.setCancelled(true);

                player.sendMessage(message.getMessage(player, "combatlog.can-not-execute-command"));
            }
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        Entity entitySender = event.getDamager();
        Entity entityTarget = event.getEntity();

        if (!(entityTarget instanceof Player)) {
            return;
        }

        int duration = config.getInt("combatlog.duration");

        Player playerSender = null;
        Player playerTarget = (Player) entityTarget;

        if (entitySender instanceof Projectile) {
            Projectile projectile = (Projectile) entitySender;

            ProjectileSource shooter = projectile.getShooter();

            if (shooter instanceof Player) {
                playerSender = (Player) shooter;
            }
        } else if (entitySender instanceof Player) {
            playerSender = (Player) entitySender;
        }

        if (playerSender != null) {
            UUID uuidSender = playerSender.getUniqueId();
            UUID uuidTarget = playerTarget.getUniqueId();

            if (uuidSender.equals(uuidTarget)) {
                return;
            }

            Optional<User> userOptional = userStorage.find(uuidSender);

            if (userOptional.isPresent()) {
                User user = userOptional.get();

                Optional<String> clanNameOptional = user.getClanName();
                if (clanNameOptional.isPresent()) {
                    Optional<Clan> clanOptional = clanStorage.find(clanNameOptional.get());

                    if (clanOptional.isPresent()) {
                        Clan clan = clanOptional.get();

                        if (clan.getMembers().contains(uuidTarget) || clan.getCreator().equals(uuidTarget)) {
                            event.setCancelled(!clan.getProperties().isAllowedDamage());

                            return;
                        }
                    }
                }
            }

            combatLogCache.add(uuidSender, duration);
            combatLogAnnouncer.sendPlayerLog(playerSender);

            disableFly(playerSender);
            disableFly(playerTarget);

            combatLogCache.add(uuidTarget, duration);
            combatLogAnnouncer.sendPlayerLog(playerTarget);
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();

        if (combatLogCache.exists(player.getUniqueId())) {
            event.setCancelled(true);
        }
    }

    private void disableFly(Player player) {
        player.setAllowFlight(false);
        player.setFlying(false);
    }

}