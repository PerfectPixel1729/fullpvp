package me.perfectpixel.fullpvp.utils;

import me.yushust.inject.process.annotation.Singleton;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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

}