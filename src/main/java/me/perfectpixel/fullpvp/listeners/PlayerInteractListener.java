package me.perfectpixel.fullpvp.listeners;

import me.perfectpixel.fullpvp.Cache;
import me.perfectpixel.fullpvp.Storage;
import me.perfectpixel.fullpvp.chest.SupplierChest;
import me.perfectpixel.fullpvp.chest.creator.UserCreator;
import me.perfectpixel.fullpvp.chest.viewer.SimpleUserViewer;
import me.perfectpixel.fullpvp.chest.viewer.UserViewer;
import me.perfectpixel.fullpvp.event.FullPVPTickEvent;
import me.perfectpixel.fullpvp.files.FileManager;
import me.perfectpixel.fullpvp.menus.Menu;
import me.perfectpixel.fullpvp.message.Message;
import me.perfectpixel.fullpvp.utils.InventoryUtils;
import me.perfectpixel.fullpvp.utils.TickCause;
import me.perfectpixel.fullpvp.utils.TimeFormat;

import me.yushust.inject.Inject;
import me.yushust.inject.name.Named;

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
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PlayerInteractListener implements Listener {

    @Inject
    private Cache<UUID, UserCreator> userCreatorCache;

    @Inject
    private Cache<UUID, SupplierChest> userEditorCache;

    @Inject
    private Storage<Location, SupplierChest> supplierChestStorage;

    @Inject
    private Storage<UUID, UserViewer> userViewerStorage;

    @Inject
    @Named("chest-creator")
    private Menu chestCreatorMenu;

    @Inject
    private Message message;

    @Inject
    private TimeFormat timeFormat;

    @Inject
    @Named("config")
    private FileManager config;

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

        Optional<UserCreator> userCreator = userCreatorCache.find(player.getUniqueId());

        Optional<SupplierChest> supplierChestOptional = supplierChestStorage.find(block.getLocation());

        if (userCreator.isPresent()) {
            event.setCancelled(true);

            if (supplierChestOptional.isPresent()) {
                Inventory inventory = chestCreatorMenu.build().build();

                userEditorCache.add(player.getUniqueId(), supplierChestOptional.get());
                supplierChestOptional.get().getItems().forEach(inventory::setItem);

                player.playSound(player.getLocation(), Sound.NOTE_PLING, 1, 2);
                player.openInventory(inventory);
                player.sendMessage(message.getMessage(player, "chest.edit-chest-inventory"));

                return;
            }

            player.playSound(player.getLocation(), Sound.NOTE_PLING, 1, 2);

            player.sendMessage(message.getMessage(player, "chest.open-chest-inventory"));

            userCreator.get().setChestLocation(block.getLocation());

            player.openInventory(chestCreatorMenu.build().build());

            return;
        }

        if (supplierChestOptional.isPresent()) {
            SupplierChest supplierChest = supplierChestOptional.get();

            event.setCancelled(true);

            Optional<UserViewer> optionalUserViewer = userViewerStorage.find(player.getUniqueId());

            if (optionalUserViewer.isPresent()) {
                UserViewer userViewer = optionalUserViewer.get();

                if (userViewer.getViewed().containsKey(supplierChest)) {
                    player.sendMessage(message.getMessage(player, "chest.already-view")
                            .replace("%time%", timeFormat.format(userViewer.getViewed().get(supplierChest) * 1000))
                    );
                } else {
                    addItemsToPlayer(player, supplierChest);
                    userViewer.getViewed().put(supplierChest, config.getInt("chests.cooldown"));
                }
            } else {
                UserViewer userViewer = new SimpleUserViewer();

                userViewer.getViewed().put(supplierChest, config.getInt("chests.cooldown"));

                userViewerStorage.add(player.getUniqueId(), userViewer);
                addItemsToPlayer(player, supplierChest);
            }
        }
    }

    private void addItemsToPlayer(Player player, SupplierChest supplierChest) {
        List<ItemStack> items = new ArrayList<>(supplierChest.getItems().values());

        int index = items.size();

        for (ItemStack item : items) {
            index -= 1;

            if (inventoryUtils.hasSpace(player, index)) {
                player.getInventory().addItem(item);
            } else {
                player.getWorld().dropItemNaturally(player.getLocation(), item);
            }
        }
    }

}