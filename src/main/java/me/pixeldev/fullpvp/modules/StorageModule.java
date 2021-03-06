package me.pixeldev.fullpvp.modules;

import me.pixeldev.fullpvp.BasicManager;
import me.pixeldev.fullpvp.Cache;
import me.pixeldev.fullpvp.Storage;
import me.pixeldev.fullpvp.backpack.Backpack;
import me.pixeldev.fullpvp.backpack.BackpackEditorCache;
import me.pixeldev.fullpvp.backpack.file.BackpackFileCache;
import me.pixeldev.fullpvp.backpack.file.BackpackStorageManager;
import me.pixeldev.fullpvp.backpack.user.BackpackUser;
import me.pixeldev.fullpvp.chest.SupplierChest;
import me.pixeldev.fullpvp.chest.SupplierChestStorageManager;
import me.pixeldev.fullpvp.chest.creator.SupplierChestCreatorCache;
import me.pixeldev.fullpvp.chest.creator.UserCreator;
import me.pixeldev.fullpvp.chest.editor.SupplierChestEditorCache;
import me.pixeldev.fullpvp.chest.viewer.SupplierChestViewerStorageManager;
import me.pixeldev.fullpvp.chest.viewer.UserViewer;
import me.pixeldev.fullpvp.clans.Clan;
import me.pixeldev.fullpvp.clans.ClanEditMessagesCache;
import me.pixeldev.fullpvp.clans.ClanStorageManager;
import me.pixeldev.fullpvp.clans.request.ClanRequest;
import me.pixeldev.fullpvp.clans.request.ClanRequestCache;
import me.pixeldev.fullpvp.combatlog.CombatLogCache;
import me.pixeldev.fullpvp.files.FileCreator;
import me.pixeldev.fullpvp.kit.Kit;
import me.pixeldev.fullpvp.kit.KitCreatorCache;
import me.pixeldev.fullpvp.kit.KitStorageManager;
import me.pixeldev.fullpvp.kit.supplier.SupplierKitCreatorCache;
import me.pixeldev.fullpvp.kit.supplier.SupplierKitManager;
import me.pixeldev.fullpvp.pearl.PearlCountdownCache;
import me.pixeldev.fullpvp.user.User;
import me.pixeldev.fullpvp.user.UserStorageManager;
import me.pixeldev.fullpvp.utils.fake.ActionData;
import me.pixeldev.fullpvp.utils.fake.FakeCommandCache;

import team.unnamed.inject.bind.AbstractModule;
import team.unnamed.inject.identity.Key;
import team.unnamed.inject.identity.type.TypeReference;
import team.unnamed.inject.name.Names;

import org.bukkit.Location;

import java.util.UUID;

public class StorageModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(new Key<Storage<UUID, User>>() {}).to(UserStorageManager.class).singleton();
        bind(new Key<Storage<Location, SupplierChest>>() {}).to(SupplierChestStorageManager.class).singleton();
        bind(new Key<Storage<UUID, UserViewer>>() {}).to(SupplierChestViewerStorageManager.class).singleton();
        bind(new Key<Storage<Integer, Kit>>() {}).to(KitStorageManager.class).singleton();
        bind(new Key<Storage<String, Clan>>() {}).to(ClanStorageManager.class).singleton();
        bind(new Key<Storage<UUID, BackpackUser>>() {}).to(BackpackStorageManager.class).singleton();

        bind(new Key<Cache<UUID, SupplierChest>>() {}).to(SupplierChestEditorCache.class).singleton();
        bind(new Key<Cache<UUID, UserCreator>>() {}).to(SupplierChestCreatorCache.class).singleton();
        bind(new Key<Cache<UUID, ClanRequest>>() {}).to(ClanRequestCache.class).singleton();
        bind(new Key<Cache<UUID, ActionData>>() {}).to(FakeCommandCache.class).singleton();
        bind(new Key<Cache<UUID, Clan>>() {}).to(ClanEditMessagesCache.class).singleton();
        bind(new Key<Cache<UUID, FileCreator>>() {}).to(BackpackFileCache.class).singleton();
        bind(new Key<Cache<UUID, Backpack>>() {}).to(BackpackEditorCache.class).singleton();

        bind(Key.of(new TypeReference<Cache<UUID, Integer>>() {}, Names.named("pearls"))).to(PearlCountdownCache.class).singleton();
        bind(Key.of(new TypeReference<Cache<UUID, Integer>>() {}, Names.named("combat"))).to(CombatLogCache.class).singleton();
        bind(Key.of(new TypeReference<Cache<UUID, Integer>>() {}, Names.named("kits"))).to(KitCreatorCache.class).singleton();

        bind(Key.of(new TypeReference<BasicManager<UUID>>() {})).to(SupplierKitCreatorCache.class).singleton();
        bind(Key.of(new TypeReference<BasicManager<Location>>() {})).to(SupplierKitManager.class).singleton();
    }

}