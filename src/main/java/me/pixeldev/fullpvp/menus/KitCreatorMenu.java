package me.pixeldev.fullpvp.menus;

import me.pixeldev.fullpvp.Cache;
import me.pixeldev.fullpvp.Storage;
import me.pixeldev.fullpvp.kit.Kit;
import me.pixeldev.fullpvp.kit.SimpleKit;
import me.pixeldev.fullpvp.message.Message;
import me.pixeldev.fullpvp.message.menu.MessageMenu;
import me.pixeldev.fullpvp.utils.ItemUtils;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import team.unnamed.gui.button.SimpleButton;
import team.unnamed.gui.item.ItemBuilder;
import team.unnamed.gui.menu.MenuBuilder;
import team.unnamed.inject.InjectAll;
import team.unnamed.inject.name.Named;

import java.util.*;

@InjectAll
public class KitCreatorMenu implements Menu {

    @Named("kits")
    private Cache<UUID, Integer> kitCreatorsCache;

    private Storage<Integer, Kit> kitStorage;
    private MessageMenu messageMenu;
    private Message message;
    private ItemUtils itemUtils;

    @Override
    public MenuBuilder build(Player player) {
        String menuKey = "kit-creator";

        ItemStack fill = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (byte) 9)
                .name(messageMenu.getItemName(menuKey, "decoration"))
                .lore(messageMenu.getItemLore(menuKey, "decoration"))
                .build();

        MenuBuilder menuBuilder = new MenuBuilder(messageMenu.getTitle(menuKey), 5)
                .addItem(
                        44,
                        new ItemBuilder(Material.STAINED_CLAY, 1, (byte) 5)
                                .name(messageMenu.getItemName(menuKey, "save"))
                                .lore(messageMenu.getItemLore(menuKey, "save"))
                                .build()
                )
                .addItem(
                        43,
                        new ItemBuilder(Material.BARRIER)
                                .name(messageMenu.getItemName(menuKey, "cancel"))
                                .lore(messageMenu.getItemLore(menuKey, "cancel"))
                                .build()
                )
                .addItem(
                        41,
                        new ItemBuilder(Material.STAINED_CLAY, 1, (byte) 14)
                                .name(messageMenu.getItemName(menuKey, "clear-contents"))
                                .lore(messageMenu.getItemLore(menuKey, "clear-contents"))
                                .build()
                )
                .addItem(
                        42,
                        new ItemBuilder(Material.STAINED_CLAY, 1, (byte) 14)
                                .name(messageMenu.getItemName(menuKey, "clear-armor"))
                                .lore(messageMenu.getItemLore(menuKey, "clear-armor"))
                                .build()
                )
                .addItem(40, fill)
                .addButton(40, new SimpleButton(click -> true))
                .addButton(
                        41,
                        new SimpleButton(click -> {
                            player.playSound(player.getLocation(), Sound.CLICK, 1, 2);

                            Inventory inventory = click.getClickedInventory();

                            getAvailableItems(inventory, 0, 27).forEach((slot, item) -> {
                                inventory.clear(slot);

                                player.getInventory().addItem(item);
                            });

                            return true;
                        })
                )
                .addButton(
                        42,
                        new SimpleButton(click -> {
                            player.playSound(player.getLocation(), Sound.CLICK, 1, 2);

                            Inventory inventory = click.getClickedInventory();

                            getAvailableItems(inventory, 36, 40).forEach((slot, item) -> {
                                inventory.clear(slot);

                                player.getInventory().addItem(item);
                            });

                            return true;
                        })
                )
                .addButton(
                        43,
                        new SimpleButton(click -> {
                            player.playSound(player.getLocation(), Sound.CLICK, 1, 2);
                            player.closeInventory();
                            player.sendMessage(message.getMessage(player, "kit.cancel-creator"));

                            kitCreatorsCache.remove(player.getUniqueId());

                            return true;
                        })
                )
                .addButton(
                        44,
                        new SimpleButton(click -> {
                            Inventory inventory = click.getClickedInventory();

                            kitCreatorsCache.find(player.getUniqueId()).ifPresent(level -> {
                                player.closeInventory();
                                player.playSound(player.getLocation(), Sound.CLICK, 1, 2);

                                if (kitStorage.find(level).isPresent()) {
                                    player.sendMessage(message.getMessage(player, "kit.successfully-edition")
                                            .replace("%level%", level + "")
                                    );

                                } else {
                                    player.sendMessage(message.getMessage(player, "kit.successfully-creation")
                                            .replace("%level%", level + "")
                                    );
                                }

                                kitStorage.add(level, new SimpleKit(
                                        parseToDefault(getAvailableItems(inventory, 36, 40)),
                                        parseToDefault(getAvailableItems(inventory, 0, 27))
                                ));

                                kitCreatorsCache.remove(player.getUniqueId());
                            });

                            return true;
                        })
                )
                .closeEvent(event -> kitCreatorsCache.remove(player.getUniqueId()));

        for (int i = 27; i < 36; i++) {
            menuBuilder.addItem(i, fill);
            menuBuilder.addButton(i, new SimpleButton(click -> true));
        }

        return menuBuilder;
    }

    private List<ItemStack> parseToDefault(Map<Integer, ItemStack> input) {
        List<ItemStack> items = new ArrayList<>();

        input.values().forEach(item -> items.add(itemUtils.addNBTTag(item, "default-kit", "default-kit")));

        return items;
    }

    private Map<Integer, ItemStack> getAvailableItems(Inventory inventory, int from, int to) {
        Map<Integer, ItemStack> items = new HashMap<>();

        for (int i = from; i < to; i++) {
            ItemStack item = inventory.getItem(i);

            if (item == null || item.getType() == null || item.getType() == Material.AIR) {
                continue;
            }

            items.put(i, item);
        }

        return items;
    }

}