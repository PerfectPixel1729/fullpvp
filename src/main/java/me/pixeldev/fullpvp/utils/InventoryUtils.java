package me.pixeldev.fullpvp.utils;

import me.pixeldev.fullpvp.chest.SupplierChest;
import team.unnamed.inject.process.annotation.Singleton;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class InventoryUtils {

    public boolean hasSpace(Player player, int spaces) {
        int i = 0;

        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null) {
                continue;
            }

            i++;
        }

        return i >= spaces;
    }

    public void addItemsToPlayer(Player player, SupplierChest supplierChest) {
        List<ItemStack> items = new ArrayList<>(supplierChest.getItems().values());

        int index = items.size();

        for (ItemStack item : items) {
            index -= 1;

            if (hasSpace(player, index)) {
                player.getInventory().addItem(item);
            } else {
                player.getWorld().dropItemNaturally(player.getLocation(), item);
            }
        }
    }

    public List<ItemStack> getContents(Player player) {
        List<ItemStack> items = new ArrayList<>();

        for (ItemStack item : player.getInventory().getContents()) {
            if (item == null) {
                continue;
            }

            items.add(item);
        }

        return items;
    }

    public void removeItems(Player player) {
        for (int i = 0; i < player.getInventory().getContents().length; i++) {
            ItemStack item = player.getInventory().getItem(i);

            if (item == null) {
                continue;
            }

            player.getInventory().clear(i);
        }

        player.updateInventory();
    }

}