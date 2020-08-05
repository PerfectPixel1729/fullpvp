package me.pixeldev.fullpvp.loader;

import me.pixeldev.fullpvp.FullPVP;
import me.pixeldev.fullpvp.listeners.*;
import me.pixeldev.fullpvp.listeners.chest.SupplierChestListeners;
import me.pixeldev.fullpvp.listeners.clan.ClanListeners;
import me.pixeldev.fullpvp.listeners.combat.CombatLogListener;
import me.pixeldev.fullpvp.listeners.fake.FakeCommandListener;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import team.unnamed.gui.listeners.MenuListeners;
import team.unnamed.inject.InjectAll;

@InjectAll
public final class EventsLoader implements Loader {

    private FullPVP fullPVP;
    private PlayerJoinListener playerJoinListener;
    private PlayerDeathListener playerDeathListener;
    private PlayerQuitListener playerQuitListener;
    private PlayerInteractListener playerInteractListener;
    private AsyncPlayerChatListener asyncPlayerChatListener;
    private SupplierChestListeners supplierChestListeners;
    private PearlListeners pearlListeners;
    private ClanListeners clanListeners;
    private FakeCommandListener fakeCommandListener;
    private CombatLogListener combatlogListener;

    @Override
    public void load() {
        registerListeners(
                playerJoinListener,
                playerQuitListener,
                playerDeathListener,
                playerInteractListener,
                asyncPlayerChatListener,
                supplierChestListeners,
                pearlListeners,
                clanListeners,
                combatlogListener,
                fakeCommandListener,
                new MenuListeners()
        );
    }

    private void registerListeners(Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getServer().getPluginManager().registerEvents(listener, fullPVP);
        }
    }

}