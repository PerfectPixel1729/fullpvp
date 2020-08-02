package me.pixeldev.fullpvp.listeners.chest;

import me.pixeldev.fullpvp.Cache;
import me.pixeldev.fullpvp.Storage;
import me.pixeldev.fullpvp.chest.SupplierChest;
import me.pixeldev.fullpvp.chest.viewer.SimpleUserViewer;
import me.pixeldev.fullpvp.chest.viewer.UserViewer;
import me.pixeldev.fullpvp.event.FullPVPTickEvent;
import me.pixeldev.fullpvp.event.chest.SupplierChestEditEvent;
import me.pixeldev.fullpvp.event.chest.SupplierChestOpenEvent;
import me.pixeldev.fullpvp.event.chest.SupplierChestPreCreateEvent;
import me.pixeldev.fullpvp.files.FileCreator;
import me.pixeldev.fullpvp.menus.Menu;
import me.pixeldev.fullpvp.message.Message;
import me.pixeldev.fullpvp.utils.InventoryUtils;
import me.pixeldev.fullpvp.utils.TickCause;
import me.pixeldev.fullpvp.utils.TimeFormat;

import me.yushust.inject.Inject;
import me.yushust.inject.name.Named;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

import java.util.Optional;
import java.util.UUID;

public class SupplierChestListeners implements Listener {

    @Inject
    private Storage<UUID, UserViewer> userViewerStorage;

    @Inject
    private Cache<UUID, SupplierChest> userEditorCache;

    @Inject
    @Named("config")
    private FileCreator config;

    @Inject
    @Named("chest-creator")
    private Menu chestCreatorMenu;

    @Inject
    private TimeFormat timeFormat;

    @Inject
    private InventoryUtils inventoryUtils;

    @EventHandler
    public void onServerTick(FullPVPTickEvent event) {
        if (event.getCause() != TickCause.SECOND) {
            return;
        }

        userViewerStorage.get().forEach(((uuid, userViewer) -> userViewer.getViewed().forEach((supplierChest, time) -> {
            userViewer.getViewed().put(supplierChest, time - 1);

            if (userViewer.getViewed().get(supplierChest) <= 0) {
                userViewer.getViewed().remove(supplierChest);
            }
        })));
    }

    @EventHandler
    public void onOpen(SupplierChestOpenEvent event) {
        Player player = event.getPlayer();
        Optional<UserViewer> userViewerOptional = event.getUserViewerOptional();
        SupplierChest supplierChest = event.getSupplierChest();
        Message message = event.getMessage();

        if (userViewerOptional.isPresent()) {
            UserViewer userViewer = userViewerOptional.get();

            if (userViewer.getViewed().containsKey(supplierChest)) {
                player.sendMessage(message.getMessage(player, "chest.already-view")
                        .replace("%time%", timeFormat.format(userViewer.getViewed().get(supplierChest) * 1000))
                );
            } else {
                inventoryUtils.addItemsToPlayer(player, supplierChest);
                userViewer.getViewed().put(supplierChest, config.getInt("chests.cooldown"));

                player.playSound(player.getLocation(), Sound.CHEST_OPEN, 1, 1);
            }
        } else {
            UserViewer userViewer = new SimpleUserViewer();

            userViewer.getViewed().put(supplierChest, config.getInt("chests.cooldown"));

            userViewerStorage.add(player.getUniqueId(), userViewer);
            inventoryUtils.addItemsToPlayer(player, supplierChest);

            player.playSound(player.getLocation(), Sound.CHEST_OPEN, 1, 1);
        }
    }

    @EventHandler
    public void onPreCreate(SupplierChestPreCreateEvent event) {
        Player player = event.getPlayer();
        Message message = event.getMessage();

        player.playSound(player.getLocation(), Sound.NOTE_PLING, 1, 2);

        player.sendMessage(message.getMessage(player, "chest.open-chest-inventory"));

        event.getUserCreator().setChestLocation(event.getLocation());

        player.openInventory(chestCreatorMenu.build(player).build());
    }

    @EventHandler
    public void onEdit(SupplierChestEditEvent event) {
        Player player = event.getPlayer();
        SupplierChest supplierChest = event.getSupplierChest();
        Message message = event.getMessage();

        Inventory inventory = chestCreatorMenu.build(player).build();

        userEditorCache.add(player.getUniqueId(), supplierChest);
        supplierChest.getItems().forEach(inventory::setItem);

        player.playSound(player.getLocation(), Sound.NOTE_PLING, 1, 2);
        player.openInventory(inventory);
        player.sendMessage(message.getMessage(player, "chest.edit-chest-inventory"));
    }

}