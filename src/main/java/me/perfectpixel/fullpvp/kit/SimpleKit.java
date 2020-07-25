package me.perfectpixel.fullpvp.kit;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SimpleKit implements Kit {

    private final List<ItemStack> armorContents = new ArrayList<>();
    private final List<ItemStack> contents = new ArrayList<>();

    @Override
    public Optional<List<ItemStack>> getArmorContents() {
        return Optional.of(armorContents);
    }

    @Override
    public List<ItemStack> getContents() {
        return contents;
    }

}