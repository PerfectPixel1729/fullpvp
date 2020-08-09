package me.pixeldev.fullpvp.backpack;

import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class SimpleBackpack implements Backpack {

    private Map<Integer, ItemStack> contents;

    private int rows;

    public SimpleBackpack() {
        contents = new HashMap<>();
        rows = 1;
    }

    public SimpleBackpack(Map<Integer, ItemStack> contents, int rows) {
        this.contents = contents;
        this.rows = rows;
    }

    @Override
    public int getRows() {
        return rows;
    }

    @Override
    public void addRows() {
        rows += 1;
    }

    @Override
    public Map<Integer, ItemStack> getContents() {
        return contents;
    }

    @Override
    public void setContents(Map<Integer, ItemStack> contents) {
        this.contents = contents;
    }

}