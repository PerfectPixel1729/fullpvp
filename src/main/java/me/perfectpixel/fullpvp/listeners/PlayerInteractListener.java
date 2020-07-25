package me.perfectpixel.fullpvp.listeners;

import me.perfectpixel.fullpvp.Storage;
import me.perfectpixel.fullpvp.chest.SupplierChest;
import me.perfectpixel.fullpvp.chest.creator.UserCreator;
import me.perfectpixel.fullpvp.menus.Menu;
import me.perfectpixel.fullpvp.message.Message;

import me.yushust.inject.Inject;
import me.yushust.inject.name.Named;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.util.Optional;
import java.util.UUID;

public class PlayerInteractListener implements Listener {

    @Inject
    @Named("chests-creators")
    private Storage<UserCreator, UUID> userCreatorStorage;

    @Inject
    @Named("chests")
    private Storage<SupplierChest, Location> supplierChestStorage;

    @Inject
    @Named("chest-creator")
    private Menu chestCreatorMenu;

    @Inject
    private Message message;

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();

        if (block == null) {
            return;
        }

        if (block.getType() != Material.CHEST) {
            return;
        }

        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        event.setCancelled(true);

        Optional<UserCreator> userCreator = userCreatorStorage.find(player.getUniqueId());

        if (userCreator.isPresent()) {
            player.playSound(player.getLocation(), Sound.NOTE_PLING, 1, 2);

            player.sendMessage(message.getMessage(player, "chest.open-chest-inventory"));

            userCreator.get().setChestLocation(block.getLocation());

            player.openInventory(chestCreatorMenu.build().build());

            return;
        }

        supplierChestStorage.find(block.getLocation()).ifPresent(supplierChest -> {
            Inventory inventory = Bukkit.createInventory(null, 9 * 3, supplierChest.getName());

            supplierChest.getItems().forEach(inventory::setItem);

            player.openInventory(inventory);
        });
    }

}