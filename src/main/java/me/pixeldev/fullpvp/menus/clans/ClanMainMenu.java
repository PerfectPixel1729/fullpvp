package me.pixeldev.fullpvp.menus.clans;

import me.pixeldev.fullpvp.Delegates;
import me.pixeldev.fullpvp.Storage;
import me.pixeldev.fullpvp.clans.Clan;
import me.pixeldev.fullpvp.clans.ClanUtilities;
import me.pixeldev.fullpvp.files.FileCreator;
import me.pixeldev.fullpvp.menus.Menu;
import me.pixeldev.fullpvp.message.Message;
import me.pixeldev.fullpvp.message.menu.MessageMenu;
import me.pixeldev.fullpvp.user.User;

import team.unnamed.inject.Inject;
import team.unnamed.inject.name.Named;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
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
                                    .lore(
                                            new LoreBuilder(messageMenu.getItemLore(keyMenu, "information"))
                                                    .replace("%clan%", clan.getProperties().getColor() + clan.getAlias())
                                                    .replace("%members%", clan.getMembers().size() + "")
                                                    .replace("%maxmembers%", config.getInt("clans.members.max") + "")
                                                    .replace("%kills%", clan.getStatistics().getKills().get() + "")
                                                    .replace("%deaths%", clan.getStatistics().getDeaths().get() + "")
                                                    .replace("%kdr%", clan.getStatistics().getKDR())
                                    )
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
                            48,
                            new SimpleButton(click -> {
                                player.playSound(player.getLocation(), Sound.NOTE_PLING, 1, 2);

                                return true;
                            })
                    )
                    .addButton(
                            49,
                            new SimpleButton(click -> {
                                if (!player.getUniqueId().equals(clan.getCreator())) {
                                    player.sendMessage(message.getMessage(player, "clans.no-creator"));

                                    return true;
                                }

                                player.playSound(player.getLocation(), Sound.CLICK, 1, 2);
                                player.openInventory(new ClanSettingsMenu(
                                        clanStorage,
                                        userStorage,
                                        messageMenu,
                                        message,
                                        fileMessage,
                                        config,
                                        clanDisbandMenu,
                                        this,
                                        clanUtilities)
                                        .build(player)
                                        .build()
                                );

                                return true;
                            })
                    )
                    .addButton(
                            13,
                            new SimpleButton(click -> true)
                    )
                    .addButton(
                            50,
                            new SimpleButton(click -> {
                                player.closeInventory();
                                player.playSound(player.getLocation(), Sound.CLICK, 1, 2);

                                return true;
                            })
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

            for (int i = 0; i < clan.getMembers().size(); i++) {
                UUID memberUUID = clan.getMembers().get(i);
                OfflinePlayer memberPlayer = Bukkit.getOfflinePlayer(memberUUID);

                int finalI = i;

                userStorage.find(memberUUID).ifPresent(userMember -> menuBuilder
                        .addItem(
                                finalI + 29,
                                new SkullBuilder(Material.SKULL_ITEM, 1, (byte) 3)
                                        .name(messageMenu.getItemName(keyMenu, "member")
                                                .replace("%name%", memberPlayer.getName())
                                        )
                                        .lore(
                                                new LoreBuilder(messageMenu.getItemLore(keyMenu, "member"))
                                                        .replace("%kills%", userMember.getKills().get() + "")
                                                        .replace("%deaths%", userMember.getDeaths().get() + "")
                                                        .replace("%kdr%", userMember.getKDR())
                                                        .replace("%status%",
                                                                memberPlayer.isOnline() ? messageMenu.getString(keyMenu + ".status-online") :
                                                                        messageMenu.getString(keyMenu + ".status-offline")
                                                        )
                                        )
                                        .offlinePlayer(memberPlayer)
                                        .buildSkull()
                        )
                        .addButton(finalI + 29, new SimpleButton(click -> true))
                );
            }

            return menuBuilder;
        }

        return null;
    }

}