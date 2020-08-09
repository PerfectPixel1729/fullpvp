package me.pixeldev.fullpvp.menus.clans;

import me.pixeldev.fullpvp.Cache;
import me.pixeldev.fullpvp.Storage;
import me.pixeldev.fullpvp.clans.Clan;
import me.pixeldev.fullpvp.clans.ClanUtilities;
import me.pixeldev.fullpvp.economy.PlayerEconomy;
import me.pixeldev.fullpvp.files.FileCreator;
import me.pixeldev.fullpvp.menus.Menu;
import me.pixeldev.fullpvp.message.Message;
import me.pixeldev.fullpvp.message.menu.MessageMenu;
import me.pixeldev.fullpvp.user.User;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import team.unnamed.gui.button.SimpleButton;
import team.unnamed.gui.item.ItemBuilder;
import team.unnamed.gui.menu.MenuBuilder;

import java.util.*;

public class ClanSettingsMenu implements Menu {

    private final Storage<String, Clan> clanStorage;
    private final Storage<UUID, User> userStorage;
    private final Cache<UUID, Clan> editMessagesCache;
    private final MessageMenu messageMenu;
    private final Message message;
    private final Message fileMessage;
    private final FileCreator config;
    private final Menu clanDisbandMenu;
    private final ClanMainMenu clanMainMenu;
    private final ClanUtilities clanUtilities;
    private final PlayerEconomy playerEconomy;

    public ClanSettingsMenu(
            Storage<String, Clan> clanStorage,
            Storage<UUID, User> userStorage,
            Cache<UUID, Clan> editMessagesCache,
            MessageMenu messageMenu,
            Message message,
            Message fileMessage,
            FileCreator config,
            Menu clanDisbandMenu,
            ClanMainMenu clanMainMenu,
            ClanUtilities clanUtilities,
            PlayerEconomy playerEconomy
    ) {

        this.editMessagesCache = editMessagesCache;
        this.clanStorage = clanStorage;
        this.userStorage = userStorage;
        this.messageMenu = messageMenu;
        this.message = message;
        this.playerEconomy = playerEconomy;
        this.fileMessage = fileMessage;
        this.config = config;
        this.clanDisbandMenu = clanDisbandMenu;
        this.clanMainMenu = clanMainMenu;
        this.clanUtilities = clanUtilities;
    }

    @Override
    public MenuBuilder build(Player player) {
        String keyMenu = "clan-settings";

        Optional<User> userOptional = userStorage.find(player.getUniqueId());

        if (!userOptional.isPresent()) {
            return null;
        }

        Optional<String> clanNameOptional = userOptional.get().getClanName();

        if (!clanNameOptional.isPresent()) {
            return null;
        }

        Optional<Clan> clanOptional = clanStorage.find(clanNameOptional.get());

        return clanOptional.map(clan -> new MenuBuilder(messageMenu.getTitle(keyMenu), 4)
                .addItem(
                        11,
                        new ItemBuilder(Material.DIAMOND_SWORD)
                                .addEnchant(Enchantment.DURABILITY, 3)
                                .addFlag(ItemFlag.HIDE_ENCHANTS)
                                .name(messageMenu.getItemName(keyMenu, "allow-damage")
                                        .replace(
                                                "%state%",
                                                clan.getProperties().isAllowedDamage() ? messageMenu.getString(keyMenu + ".items.allow-damage.state-enable") :
                                                        messageMenu.getString(keyMenu + ".items.allow-damage.state-disable")
                                        )
                                )
                                .lore(messageMenu.getItemLore(keyMenu, "allow-damage"))
                                .build()
                )
                .addItem(
                        20,
                        getColouredPanel(
                                clan.getProperties().isAllowedDamage(),
                                messageMenu.getItemName(keyMenu, "allow-damage")
                                        .replace(
                                                "%state%",
                                                clan.getProperties().isAllowedDamage() ? messageMenu.getString(keyMenu + ".items.allow-damage.state-enable") :
                                                        messageMenu.getString(keyMenu + ".items.allow-damage.state-disable")
                                        )
                                ,
                                messageMenu.getItemLore(keyMenu, "allow-damage")
                        )
                )
                .addItem(
                        13,
                        new ItemBuilder(Material.PAPER)
                                .name(messageMenu.getItemName(keyMenu, "message"))
                                .lore(messageMenu.getItemLore(keyMenu, "message"))
                                .build()
                )
                .addItem(
                        15,
                        new ItemBuilder(Material.INK_SACK, 1)
                                .name(messageMenu.getItemName(keyMenu, "change-color"))
                                .lore(messageMenu.getItemLore(keyMenu, "change-color"))
                                .build()
                )
                .addItem(
                        24,
                        new ItemBuilder(Material.REDSTONE_BLOCK)
                                .name(messageMenu.getItemName(keyMenu, "disband"))
                                .lore(messageMenu.getItemLore(keyMenu, "disband"))
                                .build()
                )
                .addItem(
                        35,
                        new ItemBuilder(Material.BARRIER)
                                .name(messageMenu.getItemName(keyMenu, "close"))
                                .lore(messageMenu.getItemLore(keyMenu, "close"))
                                .build()
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
                            player.openInventory(clanMainMenu.build(player).build());

                            return true;
                        })
                )
                .addButton(
                        11,
                        new SimpleButton(click -> {
                            fillItems(player, click.getClickedInventory(), keyMenu, clan);

                            return true;
                        })
                )
                .addButton(
                        20,
                        new SimpleButton(click -> {
                            fillItems(player, click.getClickedInventory(), keyMenu, clan);

                            return true;
                        })
                )
                .addButton(
                        35,
                        new SimpleButton(click -> {
                            player.closeInventory();
                            player.playSound(player.getLocation(), Sound.CLICK, 1, 2);

                            return true;
                        })
                )
                .addButton(
                        24,
                        new SimpleButton(click -> {
                            player.playSound(player.getLocation(), Sound.CLICK, 1, 2);
                            player.openInventory(clanDisbandMenu.build(player).build());

                            return true;
                        })
                )
                .addButton(
                        13,
                        new SimpleButton(click -> {
                            player.openInventory(new ClanMessagesMenu(
                                    clanStorage,
                                    userStorage,
                                    editMessagesCache,
                                    messageMenu,
                                    message,
                                    this
                            ).build(player).build());

                            player.playSound(player.getLocation(), Sound.CLICK, 1, 2);

                            return true;
                        })
                )
                .addButton(
                        15,
                        new SimpleButton(click -> {
                            player.openInventory(new ClanChangeColorMenu(
                                    clanStorage,
                                    userStorage,
                                    messageMenu,
                                    message,
                                    fileMessage,
                                    config,
                                    clanUtilities,
                                    this,
                                    playerEconomy
                            ).build(player).build());

                            player.playSound(player.getLocation(), Sound.CLICK, 1, 2);

                            return true;
                        })
                )
        ).orElse(null);
    }

    private ItemStack getColouredPanel(boolean bool, String name, List<String> lore) {
        byte data = 14;

        if (bool) {
            data = 5;
        }

        return new ItemBuilder(Material.STAINED_GLASS_PANE, 1, data)
                .name(name)
                .lore(lore)
                .build();
    }

    private void fillItems(Player player, Inventory inventory, String keyMenu, Clan clan) {
        player.playSound(player.getLocation(), Sound.CLICK, 1, 2);

        clan.getProperties().setAllowedDamage(!clan.getProperties().isAllowedDamage());

        inventory.setItem(
                11,
                new ItemBuilder(Material.DIAMOND_SWORD)
                        .addEnchant(Enchantment.DURABILITY, 3)
                        .addFlag(ItemFlag.HIDE_ENCHANTS)
                        .name(messageMenu.getItemName(keyMenu, "allow-damage")
                                .replace(
                                        "%state%",
                                        clan.getProperties().isAllowedDamage() ? messageMenu.getString(keyMenu + ".items.allow-damage.state-enable") :
                                                messageMenu.getString(keyMenu + ".items.allow-damage.state-disable")
                                )
                        )
                        .lore(messageMenu.getItemLore(keyMenu, "allow-damage"))
                        .build()
        );

        inventory.setItem(
                20,
                getColouredPanel(
                        clan.getProperties().isAllowedDamage(),
                        messageMenu.getItemName(keyMenu, "allow-damage")
                                .replace(
                                        "%state%",
                                        clan.getProperties().isAllowedDamage() ? messageMenu.getString(keyMenu + ".items.allow-damage.state-enable") :
                                                messageMenu.getString(keyMenu + ".items.allow-damage.state-disable")
                                )
                        ,
                        messageMenu.getItemLore(keyMenu, "allow-damage")
                )
        );
    }

}