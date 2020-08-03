package me.pixeldev.fullpvp.menus;

import me.pixeldev.fullpvp.Cache;
import me.pixeldev.fullpvp.FullPVP;
import me.pixeldev.fullpvp.Storage;
import me.pixeldev.fullpvp.chest.DefaultSupplierChest;
import me.pixeldev.fullpvp.chest.SupplierChest;
import me.pixeldev.fullpvp.chest.SupplierChestStorageManager;
import me.pixeldev.fullpvp.chest.creator.UserCreator;
import me.pixeldev.fullpvp.message.Message;
import me.pixeldev.fullpvp.message.menu.MessageMenu;

import team.unnamed.inject.Inject;

import net.wesjd.anvilgui.AnvilGUI;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import team.unnamed.gui.button.SimpleButton;
import team.unnamed.gui.item.ItemBuilder;
import team.unnamed.gui.menu.MenuBuilder;

import java.util.*;

public class ChestCreatorMenu implements Menu {

    @Inject
    private FullPVP fullPVP;

    @Inject
    private Message message;

    @Inject
    private MessageMenu simpleMessageMenu;

    @Inject
    private Cache<UUID, SupplierChest> userEditorCache;

    @Inject
    private Cache<UUID, UserCreator> userCreatorCache;

    @Inject
    private Storage<Location, SupplierChest> supplierChestStorage;

    @Override
    public MenuBuilder build(Player player) {
        String menuKey = "chest-creator";

        ItemStack fill = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (byte) 9)
                .name(simpleMessageMenu.getItemName(menuKey, "decoration"))
                .lore(simpleMessageMenu.getItemLore(menuKey, "decoration"))
                .build();

        MenuBuilder menuBuilder = new MenuBuilder(simpleMessageMenu.getTitle("chest-creator"), 5)
                .addItem(
                        44,
                        new ItemBuilder(Material.STAINED_CLAY, 1, (byte) 5)
                                .name(simpleMessageMenu.getItemName(menuKey, "save"))
                                .lore(simpleMessageMenu.getItemLore(menuKey, "save"))
                                .build()
                )
                .addItem(
                        40,
                        new ItemBuilder(Material.BARRIER)
                                .name(simpleMessageMenu.getItemName(menuKey, "cancel"))
                                .lore(simpleMessageMenu.getItemLore(menuKey, "cancel"))
                                .build()
                )
                .addItem(
                        36,
                        new ItemBuilder(Material.STAINED_CLAY, 1, (byte) 14)
                                .name(simpleMessageMenu.getItemName(menuKey, "clear"))
                                .lore(simpleMessageMenu.getItemLore(menuKey, "clear"))
                                .build()
                )
                .addButton(
                        36,
                        new SimpleButton(click -> {
                            player.playSound(player.getLocation(), Sound.CLICK, 1, 2);

                            Inventory inventory = click.getInventory();

                            getAvailableItems(inventory).forEach((slot, item) -> {
                                inventory.clear(slot);

                                player.getInventory().addItem(item);
                            });

                            return true;
                        })
                )
                .addButton(
                        40,
                        new SimpleButton(click -> {
                            cancelCreation(player);

                            return true;
                        })
                )
                .addButton(
                        44,
                        new SimpleButton(click -> {
                            player.playSound(player.getLocation(), Sound.CLICK, 1, 2);

                            if (userEditorCache.find(player.getUniqueId()).isPresent()) {
                                userEditorCache.find(player.getUniqueId()).get().setItems(getAvailableItems(click.getInventory()));

                                player.closeInventory();
                                player.sendMessage(message.getMessage(player, "chest.successfully-edition"));

                                userCreatorCache.remove(player.getUniqueId());
                                userEditorCache.remove(player.getUniqueId());

                                return true;
                            }

                            new AnvilGUI.Builder()
                                    .onClose(who -> {
                                        if (!userCreatorCache.find(player.getUniqueId()).isPresent()) {
                                            return;
                                        }

                                        cancelCreation(player);
                                    })
                                    .onComplete((who, text) -> {
                                        userCreatorCache.find(player.getUniqueId()).ifPresent(userCreator -> {
                                            if (!SupplierChestStorageManager.containsChest(text)) {
                                                supplierChestStorage.add(
                                                        userCreator.getChestLocation(),
                                                        new DefaultSupplierChest(
                                                                getAvailableItems(click.getInventory()),
                                                                text
                                                        )
                                                );

                                                userCreatorCache.remove(player.getUniqueId());
                                                player.playSound(player.getLocation(), Sound.CLICK, 1, 2);
                                                player.sendMessage(message.getMessage(player, "chest.successfully-creation"));
                                            } else {
                                                player.sendMessage(message.getMessage(player, "chest.already-name"));
                                            }

                                            player.closeInventory();
                                        });

                                        return AnvilGUI.Response.close();
                                    })
                                    .text("Introduce el nombre.")
                                    .plugin(fullPVP)
                                    .open(player);

                            return true;
                        })
                );

        for (int i = 27; i < 36; i++) {
            menuBuilder.addItem(i, fill);
            menuBuilder.addButton(i, new SimpleButton(click -> true));
        }

        return menuBuilder;
    }

    private void cancelCreation(Player player) {
        player.playSound(player.getLocation(), Sound.CLICK, 1, 2);
        player.closeInventory();

        player.sendMessage(message.getMessage(player, "chest.cancel-creator"));
        userCreatorCache.remove(player.getUniqueId());
    }

    private Map<Integer, ItemStack> getAvailableItems(Inventory inventory) {
        Map<Integer, ItemStack> items = new HashMap<>();

        for (int i = 0; i < 27; i++) {
            ItemStack item = inventory.getItem(i);

            if (item == null || item.getType() == null || item.getType() == Material.AIR) {
                continue;
            }

            items.put(i, item);
        }

        return items;
    }

}