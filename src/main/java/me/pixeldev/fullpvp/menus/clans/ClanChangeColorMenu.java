package me.pixeldev.fullpvp.menus.clans;

import me.pixeldev.fullpvp.Storage;
import me.pixeldev.fullpvp.clans.Clan;
import me.pixeldev.fullpvp.clans.ClanUtilities;
import me.pixeldev.fullpvp.files.FileCreator;
import me.pixeldev.fullpvp.menus.Menu;
import me.pixeldev.fullpvp.message.Message;
import me.pixeldev.fullpvp.message.menu.MessageMenu;
import me.pixeldev.fullpvp.user.User;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import team.unnamed.gui.button.SimpleButton;
import team.unnamed.gui.item.ItemBuilder;
import team.unnamed.gui.item.LoreBuilder;
import team.unnamed.gui.menu.MenuBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

class ClanChangeColorMenu implements Menu {

    private final Storage<String, Clan> clanStorage;

    private final Storage<UUID, User> userStorage;

    private final MessageMenu messageMenu;

    private final Message message;

    private final Message fileMessage;

    private final FileCreator config;

    private final ClanUtilities clanUtilities;

    private final Map<ChatColor, Byte> colorByteMap = new HashMap<>();

    private final ClanSettingsMenu clanSettingsMenu;

    public ClanChangeColorMenu(
            Storage<String, Clan> clanStorage,
            Storage<UUID, User> userStorage,
            MessageMenu messageMenu,
            Message message,
            Message fileMessage,
            FileCreator config,
            ClanUtilities clanUtilities,
            ClanSettingsMenu clanSettingsMenu
    ) {

        this.clanStorage = clanStorage;
        this.userStorage = userStorage;
        this.messageMenu = messageMenu;
        this.message = message;
        this.fileMessage = fileMessage;
        this.config = config;
        this.clanUtilities = clanUtilities;
        this.clanSettingsMenu = clanSettingsMenu;
    }

    {
        colorByteMap.put(ChatColor.DARK_GRAY, (byte) 8);
        colorByteMap.put(ChatColor.BLACK, (byte) 0);
        colorByteMap.put(ChatColor.RED, (byte) 1);
        colorByteMap.put(ChatColor.GOLD, (byte) 14);
        colorByteMap.put(ChatColor.GREEN, (byte) 10);
        colorByteMap.put(ChatColor.YELLOW, (byte) 11);
        colorByteMap.put(ChatColor.LIGHT_PURPLE, (byte) 9);
        colorByteMap.put(ChatColor.WHITE, (byte) 15);
        colorByteMap.put(ChatColor.DARK_BLUE, (byte) 4);
        colorByteMap.put(ChatColor.DARK_GREEN, (byte) 2);
        colorByteMap.put(ChatColor.DARK_AQUA, (byte) 6);
        colorByteMap.put(ChatColor.GRAY, (byte) 7);
        colorByteMap.put(ChatColor.DARK_PURPLE, (byte) 5);
    }

    @Override
    public MenuBuilder build(Player player) {
        String keyMenu = "clan-change-color";

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

            MenuBuilder menuBuilder = new MenuBuilder(messageMenu.getTitle(keyMenu), 4)
                    .addItem(
                            35,
                            new ItemBuilder(Material.BARRIER)
                                    .name(messageMenu.getItemName(keyMenu, "close"))
                                    .lore(messageMenu.getItemLore(keyMenu, "close"))
                                    .build()
                    )
                    .addButton(
                            35,
                            new SimpleButton(click -> {
                                player.closeInventory();
                                player.playSound(player.getLocation(), Sound.CLICK, 1, 2);

                                return true;
                            })
                    )
                    .addItem(
                            27,
                            new ItemBuilder(Material.ARROW)
                                    .name(messageMenu.getItemName(keyMenu, "back"))
                                    .lore(messageMenu.getItemLore(keyMenu, "back"))
                                    .build()
                    )
                    .addButton(
                            27,
                            new SimpleButton(click -> {
                                player.playSound(player.getLocation(), Sound.CLICK, 1, 2);
                                player.openInventory(clanSettingsMenu.build(player).build());

                                return true;
                            })
                    );

            int index = 10;

            for (Map.Entry<ChatColor, Byte> chatColorByteEntry : colorByteMap.entrySet()) {
                if (index == 17) {
                    index = 20;
                }

                if (index == 25) {
                    break;
                }

                ChatColor currentColor = chatColorByteEntry.getKey();

                int price = config.getInt("clans.colors-price." + currentColor.name().toLowerCase());

                menuBuilder
                        .addItem(
                                index,
                                new ItemBuilder(Material.INK_SACK, 1, chatColorByteEntry.getValue())
                                        .name(messageMenu.getItemName(keyMenu, "colors")
                                                .replace("%color%", currentColor + currentColor.name())
                                        )
                                        .lore(
                                                new LoreBuilder(messageMenu.getItemLore(keyMenu, "colors"))
                                                        .replace("%price%", price + "")
                                        )
                                        .build()
                        )
                        .addButton(
                                index,
                                new SimpleButton(click -> {
                                    player.closeInventory();

                                    User user = userOptional.get();

                                    if (!user.getCoins().hasCoins()) {
                                        player.sendMessage(message.getMessage(player, "coins.no-coins"));

                                        return true;
                                    }

                                    if (!user.getCoins().hasEnoughCoins(price)) {
                                        player.sendMessage(message.getMessage(player, "coins.no-enough-coins")
                                                .replace("%missing%", String.valueOf(price - userOptional.get().getCoins().get()))
                                        );

                                        return true;
                                    }

                                    String colorReplacement = currentColor + currentColor.name();

                                    user.getCoins().remove(price);

                                    player.playSound(player.getLocation(), Sound.NOTE_PLING, 1, 2);
                                    player.sendMessage(message.getMessage(player, "clans.successfully-change-color")
                                            .replace("%color%", colorReplacement)
                                    );

                                    clanUtilities.sendMessageToMembers(clanNameOptional.get(), fileMessage.getMessage(null, "clans.successfully-change-color-members")
                                            .replace("%color%", colorReplacement)
                                    );

                                    clan.getProperties().setColor(currentColor);

                                    return true;
                                })
                        );

                if (clan.getProperties().getColor() == currentColor) {
                    menuBuilder
                            .addItem(
                                    index,
                                    new ItemBuilder(Material.INK_SACK, 1, chatColorByteEntry.getValue())
                                            .name(messageMenu.getItemName(keyMenu, "current"))
                                            .lore(messageMenu.getItemLore(keyMenu, "current"))
                                            .build()
                            )
                            .addButton(index, new SimpleButton(click -> true));
                }

                index++;
            }

            return menuBuilder;
        }

        return null;
    }

}