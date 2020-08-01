package me.perfectpixel.fullpvp.menus.clans;

import me.perfectpixel.fullpvp.Delegates;
import me.perfectpixel.fullpvp.Storage;
import me.perfectpixel.fullpvp.clans.Clan;
import me.perfectpixel.fullpvp.menus.Menu;
import me.perfectpixel.fullpvp.message.Message;
import me.perfectpixel.fullpvp.message.menu.MessageMenu;
import me.perfectpixel.fullpvp.user.User;

import me.yushust.inject.Inject;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import team.unnamed.gui.button.SimpleButton;
import team.unnamed.gui.item.ItemBuilder;
import team.unnamed.gui.menu.MenuBuilder;

import java.util.Optional;
import java.util.UUID;

public class ClanDisbandMenu implements Menu {

    @Inject
    private Storage<UUID, User> userStorage;

    @Inject
    private Storage<String, Clan> clanStorage;

    @Inject
    private MessageMenu messageMenu;

    @Inject
    private Message message;

    @Inject
    @Delegates
    private Message fileMessage;

    @Override
    public MenuBuilder build(Player player) {
        String keyMenu = "clan-disband";

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

            return new MenuBuilder(messageMenu.getTitle(keyMenu), 3)
                    .addItem(
                            12,
                            new ItemBuilder(Material.STAINED_CLAY, 1, (byte) 5)
                                    .name(messageMenu.getItemName(keyMenu, "confirm"))
                                    .lore(messageMenu.getItemLore(keyMenu, "confirm"))
                                    .build()
                    )
                    .addItem(
                            14,
                            new ItemBuilder(Material.STAINED_CLAY, 1, (byte) 14)
                                    .name(messageMenu.getItemName(keyMenu, "cancel"))
                                    .lore(messageMenu.getItemLore(keyMenu, "cancel"))
                                    .build()
                    )
                    .addButton(
                            12,
                            new SimpleButton(click -> {
                                player.closeInventory();
                                player.playSound(player.getLocation(), Sound.CLICK, 1, 2);

                                player.sendMessage(message.getMessage(player, "clans.clan-disband-creator"));

                                clan.getMembers().forEach(memberUUID -> {
                                    Bukkit.getPlayer(memberUUID).sendMessage(fileMessage.getMessage(null, "clans.clan-disband-members"));

                                    userStorage.find(memberUUID).ifPresent(memberUser -> memberUser.setClanName(null));
                                });

                                userOptional.get().setClanName(null);

                                clanStorage.remove(clanNameOptional.get());

                                return true;
                            })
                    )
                    .addButton(
                            14,
                            new SimpleButton(click -> {
                                player.closeInventory();
                                player.playSound(player.getLocation(), Sound.CLICK, 1, 2);

                                player.sendMessage(message.getMessage(player, "clans.successfully-cancel-disband"));

                                return true;
                            })
                    );
        }

        return null;
    }

}