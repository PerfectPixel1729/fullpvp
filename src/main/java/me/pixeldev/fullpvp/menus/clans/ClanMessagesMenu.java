package me.pixeldev.fullpvp.menus.clans;

import me.pixeldev.fullpvp.Storage;
import me.pixeldev.fullpvp.clans.Clan;
import me.pixeldev.fullpvp.menus.Menu;
import me.pixeldev.fullpvp.message.Message;
import me.pixeldev.fullpvp.message.menu.MessageMenu;
import me.pixeldev.fullpvp.user.User;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import team.unnamed.gui.button.SimpleButton;
import team.unnamed.gui.item.ItemBuilder;
import team.unnamed.gui.menu.MenuBuilder;

import java.util.Optional;
import java.util.UUID;

public class ClanMessagesMenu implements Menu {

    private final Storage<String, Clan> clanStorage;

    private final Storage<UUID, User> userStorage;

    private final MessageMenu messageMenu;

    private final Message message;

    private final ClanSettingsMenu clanSettingsMenu;

    public ClanMessagesMenu(
            Storage<String, Clan> clanStorage,
            Storage<UUID, User> userStorage,
            MessageMenu messageMenu,
            Message message,
            ClanSettingsMenu clanSettingsMenu) {

        this.clanStorage = clanStorage;
        this.userStorage = userStorage;
        this.messageMenu = messageMenu;
        this.message = message;
        this.clanSettingsMenu = clanSettingsMenu;
    }

    @Override
    public MenuBuilder build(Player player) {
        String keyMenu = "clan-messages";

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
                            11,
                            new ItemBuilder(Material.FEATHER)
                                    .name(messageMenu.getItemName(keyMenu, "add"))
                                    .lore(messageMenu.getItemLore(keyMenu, "add"))
                                    .build()
                    )
                    .addItem(
                            15,
                            new ItemBuilder(Material.REDSTONE_BLOCK)
                                    .name(messageMenu.getItemName(keyMenu, "clear"))
                                    .lore(messageMenu.getItemLore(keyMenu, "clear"))
                                    .build()
                    )
                    .addItem(
                            21,
                            new ItemBuilder(Material.ARROW)
                                    .name(messageMenu.getItemName(keyMenu, "back"))
                                    .lore(messageMenu.getItemLore(keyMenu, "back"))
                                    .build()
                    )
                    .addItem(
                            23,
                            new ItemBuilder(Material.BARRIER)
                                    .name(messageMenu.getItemName(keyMenu, "close"))
                                    .lore(messageMenu.getItemLore(keyMenu, "close"))
                                    .build()
                    )
                    .addButton(
                            11,
                            new SimpleButton(click -> true)
                    )
                    .addButton(
                            15,
                            new SimpleButton(click -> {
                                player.playSound(player.getLocation(), Sound.CLICK, 1, 2);

                                if (clan.getProperties().getMessages().isEmpty()) {
                                    player.sendMessage(message.getMessage(player, "clans.already-clear-messages"));

                                    return true;
                                }

                                player.sendMessage(message.getMessage(player, "clans.successfully-clear-messages"));

                                clan.getProperties().getMessages().clear();

                                return true;
                            })
                    )
                    .addButton(
                            21,
                            new SimpleButton(click -> {
                                player.playSound(player.getLocation(), Sound.CLICK, 1, 2);
                                player.openInventory(clanSettingsMenu.build(player).build());

                                return true;
                            })
                    )
                    .addButton(
                            23,
                            new SimpleButton(click -> {
                                player.closeInventory();
                                player.playSound(player.getLocation(), Sound.CLICK, 1, 2);

                                return true;
                            })
                    );
        }

        return null;
    }

}