package me.perfectpixel.fullpvp.menus.clans;

import me.perfectpixel.fullpvp.Delegates;
import me.perfectpixel.fullpvp.Storage;
import me.perfectpixel.fullpvp.clans.Clan;
import me.perfectpixel.fullpvp.clans.ClanUtilities;
import me.perfectpixel.fullpvp.files.FileCreator;
import me.perfectpixel.fullpvp.menus.Menu;
import me.perfectpixel.fullpvp.message.Message;
import me.perfectpixel.fullpvp.message.menu.MessageMenu;
import me.perfectpixel.fullpvp.user.User;

import me.yushust.inject.Inject;
import me.yushust.inject.name.Named;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import team.unnamed.gui.button.SimpleButton;
import team.unnamed.gui.item.ItemBuilder;
import team.unnamed.gui.item.LoreBuilder;
import team.unnamed.gui.item.type.SkullBuilder;
import team.unnamed.gui.menu.MenuBuilder;

import java.util.Optional;
import java.util.UUID;

public class ClanMainMenu implements Menu {

    @Inject
    private Storage<String, Clan> clanStorage;

    @Inject
    private Storage<UUID, User> userStorage;

    @Inject
    private MessageMenu messageMenu;

    @Inject
    private Message message;

    @Inject
    @Delegates
    private Message fileMessage;

    @Inject
    @Named("config")
    private FileCreator config;

    @Inject
    @Named("clan-disband")
    private Menu clanDisbandMenu;

    @Inject
    private ClanUtilities clanUtilities;

    @Override
    public MenuBuilder build(Player player) {
        String keyMenu = "clan-main";

        Optional<User> userOptional = userStorage.find(player.getUniqueId());

        if (!userOptional.isPresent()) {
            return null;
        }

        Optional<String> clanNameOptional = userOptional.get().getClanName();

        if (!clanNameOptional.isPresent()) {
            return null;
        }

        Optional<Clan> clanOptional = clanStorage.find(clanNameOptional.get());

        if (clanOptional.isPresent()) {
            Clan clan = clanOptional.get();

            MenuBuilder menuBuilder = new MenuBuilder(messageMenu.getTitle(keyMenu).replace("%clan%", clanNameOptional.get()), 6)
                    .addItem(
                            48,
                            new ItemBuilder(Material.BOOK)
                                    .name(messageMenu.getItemName(keyMenu, "information"))
                                    .lore(messageMenu.getItemLore(keyMenu, "information"))
                                    .build()
                    )
                    .addItem(
                            49,
                            new ItemBuilder(Material.HOPPER)
                                    .name(messageMenu.getItemName(keyMenu, "settings"))
                                    .lore(messageMenu.getItemLore(keyMenu, "settings"))
                                    .build()
                    )
                    .addItem(
                            50,
                            new ItemBuilder(Material.BARRIER)
                                    .name(messageMenu.getItemName(keyMenu, "close"))
                                    .lore(messageMenu.getItemLore(keyMenu, "close"))
                                    .build()
                    )
                    .addButton(
                            13,
                            new SimpleButton(click -> true)
                    );

            Optional<User> userCreatorOptional = userStorage.find(clan.getCreator());

            userCreatorOptional.ifPresent(userCreator -> {
                OfflinePlayer creatorPlayer = Bukkit.getOfflinePlayer(clan.getCreator());

                menuBuilder
                        .addItem(
                                13,
                                new SkullBuilder(Material.SKULL_ITEM, 1, (byte) 3)
                                        .name(messageMenu.getItemName(keyMenu, "creator")
                                                .replace("%name%", creatorPlayer.getName())
                                        )
                                        .lore(
                                                new LoreBuilder(messageMenu.getItemLore(keyMenu, "creator"))
                                                        .replace("%kills%", userCreator.getKills().get() + "")
                                                        .replace("%deaths%", userCreator.getDeaths().get() + "")
                                                        .replace("%kdr%", userCreator.getKDR())
                                                        .replace("%status%",
                                                                creatorPlayer.isOnline() ? messageMenu.getString(keyMenu + ".status-online") :
                                                                        messageMenu.getString(keyMenu + ".status-offline")
                                                        )
                                        )
                                        .offlinePlayer(Bukkit.getOfflinePlayer(clan.getCreator()))
                                        .buildSkull()
                        )
                        .addButton(13, new SimpleButton(click -> true));
            });

            return menuBuilder;
        }

        return null;
    }

}