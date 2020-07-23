package me.perfectpixel.fullpvp.chest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.inventory.Inventory;

@Getter
@AllArgsConstructor
public class DefaultSupplierChest implements SupplierChest {

    private final String name;
    private final Inventory inventory;
    private final Location location;

}