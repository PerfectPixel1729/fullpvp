package me.pixeldev.fullpvp.kit.supplier;

import me.pixeldev.fullpvp.BasicManager;
import me.pixeldev.fullpvp.files.FileCreator;
import me.pixeldev.fullpvp.utils.LocationSerializable;

import org.bukkit.Location;

import team.unnamed.inject.Inject;
import team.unnamed.inject.name.Named;

import java.util.*;

public class SupplierKitManager implements BasicManager<Location> {

    @Inject
    @Named("kits")
    private FileCreator kits;

    private final Set<Location> kitSuppliers = new HashSet<>();

    @Override
    public Set<Location> get() {
        return kitSuppliers;
    }

    @Override
    public void remove(Location location) { kitSuppliers.remove(location); }

    @Override
    public void add(Location location) {
        kitSuppliers.add(location);
    }

    @Override
    public boolean exists(Location key) {
        for (Location location : kitSuppliers) {
            if (key.equals(location)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void saveAll() {
        List<String> locations = new ArrayList<>();

        kitSuppliers.forEach(location -> locations.add(LocationSerializable.fromBukkit(location)));

        kits.set("kits-supplier", locations);

        kits.save();
    }

    @Override
    public void loadAll() {
        if (!kits.contains("kits-supplier")) {
            return;
        }

        List<String> locations = kits.getStringList("kits-supplier");

        locations.forEach(s -> kitSuppliers.add(LocationSerializable.fromString(s)));
    }

}