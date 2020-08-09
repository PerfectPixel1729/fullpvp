package me.pixeldev.fullpvp.backpack.user;

import me.pixeldev.fullpvp.backpack.Backpack;

import java.util.Map;
import java.util.Optional;

public interface BackpackUser {

    Map<Integer, Backpack> getBackpacks();

    void addBackpack(int position, Backpack backpack);

    void removeBackpack(int position);

    Optional<Backpack> getBackpack(int position);

}