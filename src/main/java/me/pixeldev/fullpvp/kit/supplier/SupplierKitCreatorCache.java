package me.pixeldev.fullpvp.kit.supplier;

import me.pixeldev.fullpvp.BasicManager;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class SupplierKitCreatorCache implements BasicManager<UUID>{

    private final Set<UUID> kitSupplierCreators = new HashSet<>();

    @Override
    public Set<UUID> get() {
        return kitSupplierCreators;
    }

}