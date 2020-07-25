package me.perfectpixel.fullpvp.chest.creator;

import lombok.Getter;
import lombok.Setter;

import org.bukkit.Location;

@Getter
public class SimpleUserCreator implements UserCreator {

    private final CreatorInventory savedInventory;

    @Setter
    private Location chestLocation;

    public SimpleUserCreator(UserCreatorInventory savedInventory) {
        this.savedInventory = savedInventory;
        chestLocation = null;
    }

}