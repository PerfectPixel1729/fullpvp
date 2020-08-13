package me.pixeldev.fullpvp.commands;

import me.fixeddev.ebcm.parametric.CommandClass;
import me.fixeddev.ebcm.parametric.annotation.ACommand;
import me.fixeddev.ebcm.parametric.annotation.Injected;

import me.fixeddev.ebcm.parametric.annotation.Usage;
import me.pixeldev.fullpvp.Cache;
import me.pixeldev.fullpvp.Delegates;
import me.pixeldev.fullpvp.Storage;
import me.pixeldev.fullpvp.kit.Kit;
import me.pixeldev.fullpvp.menus.Menu;
import me.pixeldev.fullpvp.message.Message;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.bukkit.inventory.Inventory;
import team.unnamed.inject.InjectAll;
import team.unnamed.inject.name.Named;

import java.util.Optional;
import java.util.UUID;

@ACommand(names = {"kitcreator", "kcreator"})
@InjectAll
public class KitCommands implements CommandClass {

    private Storage<Integer, Kit> kitStorage;
    private Message message;

    @Named("kits")
    private Cache<UUID, Integer> kitCreatorsCache;

    @Delegates
    private Message fileMessage;

    @Named("kit-creator")
    private Menu menu;

    @ACommand(names = "")
    @Usage(usage = "§8- §9[level]")
    public boolean runMainCommand(@Injected(true) CommandSender sender, Integer level) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(fileMessage.getMessage(null, "no-player-sender"));

            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("fullpvp.kits.create") || !player.hasPermission("fullpvp.admin")) {
            player.sendMessage(fileMessage.getMessage(null, "i18n.no-permission"));

            return true;
        }

        Optional<Kit> kitOptional = kitStorage.find(level);

        if (kitOptional.isPresent()) {
            Inventory inventory = menu.build(player).build();
            Kit kit = kitOptional.get();

            for (int i = 0; i < kit.getContents().size(); i++) {
                inventory.setItem(i, kit.getContents().get(i));
            }

            for (int i = 36; i < 40; i++) {
                inventory.setItem(i, kit.getArmorContents().get(i - 36));
            }

            player.openInventory(inventory);
            kitCreatorsCache.add(player.getUniqueId(), level);

            player.sendMessage(message.getMessage(player, "kit.enter-editor-mode")
                    .replace("%level%", level + "")
            );

            return true;
        }

        kitCreatorsCache.add(player.getUniqueId(), level);

        player.sendMessage(message.getMessage(player, "kit.enter-creator-mode")
                .replace("%level%", level + "")
        );

        player.openInventory(menu.build(player).build());

        return true;
    }

}