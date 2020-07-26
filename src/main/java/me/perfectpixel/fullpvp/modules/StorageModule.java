package me.perfectpixel.fullpvp.modules;

import me.perfectpixel.fullpvp.Storage;
import me.perfectpixel.fullpvp.chest.SupplierChest;
import me.perfectpixel.fullpvp.chest.SupplierChestStorageManager;
import me.perfectpixel.fullpvp.chest.creator.SupplierChestCreatorCache;
import me.perfectpixel.fullpvp.chest.creator.UserCreator;
import me.perfectpixel.fullpvp.chest.viewer.SupplierChestViewerCache;
import me.perfectpixel.fullpvp.chest.viewer.UserViewer;
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
        bind(Key.of(new TypeReference<Storage<UserCreator, UUID>>() {}, (Names.named("chests-creators")))).to(SupplierChestCreatorCache.class).singleton();
        bind(Key.of(new TypeReference<Storage<SupplierChest, Location>>() {}, (Names.named("chests")))).to(SupplierChestStorageManager.class).singleton();
        bind(Key.of(new TypeReference<Storage<UserViewer, UUID>>() {}, (Names.named("chests-viewers")))).to(SupplierChestViewerCache.class).singleton();
    }

}