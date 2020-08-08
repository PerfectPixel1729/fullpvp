package me.pixeldev.fullpvp.listeners;

import me.pixeldev.fullpvp.Storage;
import me.pixeldev.fullpvp.event.SupplierKitReceiveEvent;
import me.pixeldev.fullpvp.kit.Kit;
import me.pixeldev.fullpvp.message.Message;
import me.pixeldev.fullpvp.user.User;
import me.pixeldev.fullpvp.utils.InventoryUtils;
import me.pixeldev.fullpvp.utils.ItemUtils;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import team.unnamed.inject.InjectAll;

import java.util.UUID;

@InjectAll
public class SupplierKitListeners implements Listener {

    private Storage<Integer, Kit> kitStorage;
    private Storage<UUID, User> userStorage;
    private InventoryUtils inventoryUtils;
    private ItemUtils itemUtils;
    private Message message;

    @EventHandler
    public void onReceive(SupplierKitReceiveEvent event) {
        Player player = event.getPlayer();

        userStorage.find(player.getUniqueId()).flatMap(user -> kitStorage.find(user.getKitLevel())).ifPresent(kit -> {
            inventoryUtils.addItemsToPlayer(player, kit.getContents(), false);
            inventoryUtils.addArmorToPlayer(player, kit.getArmorContents());

            player.sendMessage(message.getMessage(player, "kit.supplier.successfully-receive"));
        });
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        ItemStack item = event.getItemDrop().getItemStack();

        if (isDefaultItem(item)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEnchant(EnchantItemEvent event) {
        Player player = event.getEnchanter();
        ItemStack item = event.getItem();

        if (isDefaultItem(item)) {
            event.setCancelled(true);

            player.sendMessage(message.getMessage(player, "kit.can-not-enchant"));
        }
    }

    @EventHandler
    public void onReforge(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();

        if (inventory instanceof AnvilInventory) {
            if (!(event.getWhoClicked() instanceof Player)) {
                return;
            }

            AnvilInventory anvilInventory = (AnvilInventory) inventory;
            Player player = (Player) event.getWhoClicked();

            if (event.getSlotType() == InventoryType.SlotType.RESULT) {
                if (isDefaultItem(anvilInventory.getItem(0)) || isDefaultItem(anvilInventory.getItem(1))) {
                    event.setCancelled(true);

                    player.sendMessage(message.getMessage(player, "kit.can-not-reforge"));
                }
            }
        }
    }

    private boolean isDefaultItem(ItemStack item) {
        return itemUtils.hasNBTTag(item, "default-kit");
    }

}