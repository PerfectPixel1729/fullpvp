package me.pixeldev.fullpvp.backpack.user;

import me.pixeldev.fullpvp.backpack.Backpack;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SimpleBackpackUser implements BackpackUser {

    private final Map<Integer, Backpack> backpacks;

    public SimpleBackpackUser() {
        backpacks = new HashMap<>();
    }

    public SimpleBackpackUser(Map<Integer, Backpack> backpacks) {
        this.backpacks = backpacks;
    }

    @Override
    public Map<Integer, Backpack> getBackpacks() {
        return backpacks;
    }

    @Override
    public void addBackpack(int position, Backpack backpack) {
        backpacks.put(position, backpack);
    }

    @Override
    public void removeBackpack(int position) {
        backpacks.remove(position);
    }

    @Override
    public Optional<Backpack> getBackpack(int position) {
        return Optional.ofNullable(backpacks.get(position));
    }

}