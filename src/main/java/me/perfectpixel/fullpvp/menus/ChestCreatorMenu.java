package me.perfectpixel.fullpvp.menus;

import me.perfectpixel.fullpvp.FullPVP;
import me.perfectpixel.fullpvp.Storage;
import me.perfectpixel.fullpvp.chest.SupplierChest;
import me.perfectpixel.fullpvp.message.Message;
import me.perfectpixel.fullpvp.message.menu.MessageMenu;

import me.yushust.inject.Inject;
import me.yushust.inject.name.Named;

import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import team.unnamed.gui.button.SimpleButton;
import team.unnamed.gui.item.ItemBuilder;
import team.unnamed.gui.menu.MenuBuilder;

import java.util.Arrays;
import java.util.UUID;

public class ChestCreatorMenu implements Menu {

    @Inject
    private FullPVP fullPVP;

    @Inject
    private Message simpleMessageDecorator;

    @Inject
    private MessageMenu simpleMessageMenu;

    @Inject
    @Named("chests-creators")
    private Storage<Location, UUID> chestCreators;

    @Inject
    @Named("chests")
    private Storage<SupplierChest, String> supplierChestStorage;

    @Override
    public MenuBuilder build() {
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
                            if (!(click.getWhoClicked() instanceof Player)) {
                                return true;
                            }

                            Player player = (Player) click.getWhoClicked();

                            player.playSound(player.getLocation(), Sound.CLICK, 1, 2);
                            click.getClickedInventory().clear();

                            return true;
                        })
                )
                .addButton(
                        40,
                        new SimpleButton(click -> {
                            if (!(click.getWhoClicked() instanceof Player)) {
                                return true;
                            }

                            Player player = (Player) click.getWhoClicked();

                            cancelCreation(player);

                            return true;
                        })
                )
                .addButton(
                        44,
                        new SimpleButton(click -> {
                            if (!(click.getWhoClicked() instanceof Player)) {
                                return true;
                            }

                            Player player = (Player) click.getWhoClicked();

                            player.playSound(player.getLocation(), Sound.CLICK, 1, 2);

                            new AnvilGUI.Builder()
                                    .onClose(who -> cancelCreation(player))
                                    .onComplete((who, text) -> {
                                        if (!supplierChestStorage.get().containsKey(text)) {
                                            player.playSound(player.getLocation(), Sound.CLICK, 1, 2);

                                            player.sendMessage(Arrays.toString(click.getInventory().getContents()));

                                            return AnvilGUI.Response.close();
                                        } else {
                                            return AnvilGUI.Response.text(simpleMessageDecorator.getMessage(player, "chest.already-name"));
                                        }
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

        player.sendMessage(simpleMessageDecorator.getMessage(player, "chest.cancel-creator"));
        chestCreators.remove(player.getUniqueId());
    }

}