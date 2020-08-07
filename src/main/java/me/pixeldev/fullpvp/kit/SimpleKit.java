package me.pixeldev.fullpvp.kit;

import lombok.AllArgsConstructor;
import lombok.Getter;

import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter
@AllArgsConstructor
public class SimpleKit implements Kit {

    private final List<ItemStack> armorContents;
    private final List<ItemStack> contents;

}