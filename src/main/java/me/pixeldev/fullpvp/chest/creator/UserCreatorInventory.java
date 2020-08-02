package me.pixeldev.fullpvp.chest.creator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.GameMode;
import org.bukkit.inventory.ItemStack;

@Getter
@AllArgsConstructor
public class UserCreatorInventory implements CreatorInventory {

    private final ItemStack[] contents;
    private final ItemStack[] armor;
    private final GameMode gameMode;

}