package me.pixeldev.fullpvp.kit;

import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Optional;

public interface Kit {

    Optional<List<ItemStack>> getArmorContents();

    List<ItemStack> getContents();

}