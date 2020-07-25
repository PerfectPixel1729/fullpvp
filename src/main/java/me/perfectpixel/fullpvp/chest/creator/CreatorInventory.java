package me.perfectpixel.fullpvp.chest.creator;

import org.bukkit.GameMode;
import org.bukkit.inventory.ItemStack;

public interface CreatorInventory {

    ItemStack[] getContents();

    ItemStack[] getArmor();

    GameMode getGameMode();

}