package me.perfectpixel.fullpvp;

import me.perfectpixel.fullpvp.chest.SupplierChest;
import me.perfectpixel.fullpvp.chest.SupplierChestStorageManager;
import me.perfectpixel.fullpvp.user.User;
import me.perfectpixel.fullpvp.user.UserStorageManager;

import me.yushust.inject.bind.AbstractModule;
import me.yushust.inject.identity.Key;
import org.bukkit.Location;

import java.util.UUID;

public class StorageModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(new Key<Storage<User, UUID>>() {}).to(UserStorageManager.class).singleton();
        bind(new Key<Storage<SupplierChest, Location>>() {}).to(SupplierChestStorageManager.class).singleton();
    }

}