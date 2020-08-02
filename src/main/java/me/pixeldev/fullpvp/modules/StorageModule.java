package me.pixeldev.fullpvp.modules;

import me.pixeldev.fullpvp.Cache;
import me.pixeldev.fullpvp.Storage;
import me.pixeldev.fullpvp.chest.SupplierChest;
import me.pixeldev.fullpvp.chest.SupplierChestStorageManager;
import me.pixeldev.fullpvp.chest.creator.SupplierChestCreatorCache;
import me.pixeldev.fullpvp.chest.creator.UserCreator;
import me.pixeldev.fullpvp.chest.editor.SupplierChestEditorCache;
import me.pixeldev.fullpvp.chest.viewer.SupplierChestViewerStorageManager;
import me.pixeldev.fullpvp.chest.viewer.UserViewer;
import me.pixeldev.fullpvp.clans.Clan;
import me.pixeldev.fullpvp.clans.ClanStorageManager;
import me.pixeldev.fullpvp.clans.request.ClanRequest;
import me.pixeldev.fullpvp.clans.request.ClanRequestCache;
import me.pixeldev.fullpvp.pearl.PearlCountdownCache;
import me.pixeldev.fullpvp.user.User;
import me.pixeldev.fullpvp.user.UserStorageManager;
import me.pixeldev.fullpvp.utils.fake.ActionData;
import me.pixeldev.fullpvp.utils.fake.FakeCommandCache;

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
        bind(new Key<Cache<UUID, ActionData>>() {}).to(FakeCommandCache.class).singleton();
    }

}