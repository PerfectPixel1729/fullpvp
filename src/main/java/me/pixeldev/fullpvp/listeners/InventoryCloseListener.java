package me.pixeldev.fullpvp.listeners;

import me.pixeldev.fullpvp.Cache;
import me.pixeldev.fullpvp.backpack.Backpack;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import team.unnamed.inject.InjectAll;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@InjectAll
public class InventoryCloseListener implements Listener {

    private Cache<UUID, Backpack> backpackEditorCache;

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getPlayer();
        Inventory inventory = event.getInventory();

        backpackEditorCache.find(player.getUniqueId()).ifPresent(backpack -> {
            Map<Integer, ItemStack> newContents = new HashMap<>();

            for (int i = 0; i < inventory.getContents().length; i++) {
                ItemStack item = inventory.getItem(i);

                if (item == null || item.getType() == null) {
                    continue;
                }

                newContents.put(i, item);
            }

            backpack.setContents(newContents);
            backpackEditorCache.remove(player.getUniqueId());
        });
    }

}