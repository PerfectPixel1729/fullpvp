package me.perfectpixel.fullpvp.modules;

import me.perfectpixel.fullpvp.Cache;
import me.perfectpixel.fullpvp.Storage;
import me.perfectpixel.fullpvp.chest.SupplierChest;
import me.perfectpixel.fullpvp.chest.SupplierChestStorageManager;
import me.perfectpixel.fullpvp.chest.creator.SupplierChestCreatorCache;
import me.perfectpixel.fullpvp.chest.creator.UserCreator;
import me.perfectpixel.fullpvp.chest.editor.SupplierChestEditorCache;
import me.perfectpixel.fullpvp.chest.viewer.SupplierChestViewerStorageManager;
import me.perfectpixel.fullpvp.chest.viewer.UserViewer;
import me.perfectpixel.fullpvp.clans.Clan;
import me.perfectpixel.fullpvp.clans.ClanStorageManager;
import me.perfectpixel.fullpvp.clans.request.ClanRequest;
import me.perfectpixel.fullpvp.clans.request.ClanRequestCache;
import me.perfectpixel.fullpvp.pearl.PearlCountdownCache;
import me.perfectpixel.fullpvp.user.User;
import me.perfectpixel.fullpvp.user.UserStorageManager;

import me.yushust.inject.bind.AbstractModule;
import me.yushust.inject.identity.Key;

import org.bukkit.Location;

import java.util.UUID;

public class StorageModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(new Key<Storage<UUID, User>>() {}).to(UserStorageManager.class).singleton();
        bind(new Key<Storage<Location, SupplierChest>>() {}).to(SupplierChestStorageManager.class).singleton();
        bind(new Key<Storage<UUID, UserViewer>>() {}).to(SupplierChestViewerStorageManager.class).singleton();
        bind(new Key<Storage<String, Clan>>() {}).to(ClanStorageManager.class).singleton();

        bind(new Key<Cache<UUID, SupplierChest>>() {}).to(SupplierChestEditorCache.class).singleton();
        bind(new Key<Cache<UUID, UserCreator>>() {}).to(SupplierChestCreatorCache.class).singleton();
        bind(new Key<Cache<UUID, Integer>>() {}).to(PearlCountdownCache.class).singleton();
        bind(new Key<Cache<UUID, ClanRequest>>() {}).to(ClanRequestCache.class).singleton();
    }

}