package me.perfectpixel.fullpvp.modules;

import me.perfectpixel.fullpvp.Storage;
import me.perfectpixel.fullpvp.chest.SupplierChest;
import me.perfectpixel.fullpvp.chest.SupplierChestStorageManager;
import me.perfectpixel.fullpvp.user.User;
import me.perfectpixel.fullpvp.user.UserStorageManager;

import me.yushust.inject.bind.AbstractModule;
import me.yushust.inject.identity.Key;
import me.yushust.inject.identity.type.TypeReference;
import me.yushust.inject.name.Names;

import org.bukkit.Location;

import java.util.UUID;

public class StorageModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Key.of(new TypeReference<Storage<User, UUID>>() {}, (Names.named("users")))).to(UserStorageManager.class).singleton();
        bind(Key.of(new TypeReference<Storage<SupplierChest, String>>() {}, (Names.named("chests")))).to(SupplierChestStorageManager.class).singleton();
    }

}