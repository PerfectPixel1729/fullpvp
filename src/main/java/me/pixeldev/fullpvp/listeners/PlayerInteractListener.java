package me.pixeldev.fullpvp.listeners;

import me.pixeldev.fullpvp.BasicManager;
import me.pixeldev.fullpvp.Cache;
import me.pixeldev.fullpvp.Storage;
import me.pixeldev.fullpvp.chest.SupplierChest;
import me.pixeldev.fullpvp.chest.creator.UserCreator;
import me.pixeldev.fullpvp.chest.viewer.UserViewer;
import me.pixeldev.fullpvp.event.SupplierKitReceiveEvent;
import me.pixeldev.fullpvp.event.chest.SupplierChestEditEvent;
import me.pixeldev.fullpvp.event.chest.SupplierChestOpenEvent;
import me.pixeldev.fullpvp.event.chest.SupplierChestPreCreateEvent;
import me.pixeldev.fullpvp.message.Message;

import team.unnamed.inject.InjectAll;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Optional;
import java.util.UUID;

@InjectAll
public class PlayerInteractListener implements Listener {

    private BasicManager<Location> supplierKitManager;
    private BasicManager<UUID> supplierKitCreatorCache;

    private Cache<UUID, UserCreator> userCreatorCache;
    private Storage<Location, SupplierChest> supplierChestStorage;
    private Storage<UUID, UserViewer> userViewerStorage;
    private Message message;

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();

        if (block == null) {
            return;
        }

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (block.getType() == Material.CHEST) {
                if (supplierKitCreatorCache.exists(player.getUniqueId())) {
                    event.setCancelled(true);

                    if (!supplierKitManager.exists(block.getLocation())) {
                        supplierKitManager.add(block.getLocation());
                        player.sendMessage(message.getMessage(player, "kit.supplier.successfully-creation"));
                    } else {
                        player.sendMessage(message.getMessage(player, "kit.supplier.already-location"));
                    }

                    return;
                }

                if (supplierKitManager.exists(block.getLocation())) {
                    event.setCancelled(true);

                    Bukkit.getPluginManager().callEvent(new SupplierKitReceiveEvent(player));

                    return;
                }

                Optional<UserCreator> userCreatorOptional = userCreatorCache.find(player.getUniqueId());
                Optional<SupplierChest> supplierChestOptional = supplierChestStorage.find(block.getLocation());

                if (supplierChestOptional.isPresent()) {
                    event.setCancelled(true);

                    if (userCreatorOptional.isPresent()) {
                        Bukkit.getPluginManager().callEvent(new SupplierChestEditEvent(player, supplierChestOptional.get(), message));
                    } else {
                        Bukkit.getPluginManager().callEvent(new SupplierChestOpenEvent(player, supplierChestOptional.get(), message, userViewerStorage.find(player.getUniqueId())));
                    }
                } else {
                    userCreatorOptional.ifPresent(userCreator -> {
                        event.setCancelled(true);

                        Bukkit.getPluginManager().callEvent(new SupplierChestPreCreateEvent(player, userCreator, message, block.getLocation()));
                    });
                }
            }
        }
    }

}