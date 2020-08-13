package me.pixeldev.fullpvp.utils;

import me.pixeldev.fullpvp.chest.SupplierChest;

import org.bukkit.Material;
import team.unnamed.inject.Inject;
import team.unnamed.inject.process.annotation.Singleton;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class InventoryUtils {

    @Inject
    private ItemUtils itemUtils;

    public boolean hasSpace(Player player, int spaces) {
        int i = 0;

        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null) {
                continue;
            }

            i++;
        }

        return i >= spaces;
    }

    public boolean hasDefaultKit(Player player) {
        for (ItemStack item : player.getInventory().getContents()) {
            if (item == null || item.getType() == null || item.getType() == Material.AIR) {
                continue;
            }

            if (itemUtils.hasNBTTag(item, "default-kit")) {
                return true;
            }
        }

        return false;
    }

    public void addItemsToPlayer(Player player, SupplierChest supplierChest) {
        List<ItemStack> items = new ArrayList<>(supplierChest.getItems().values());

        addItemsToPlayer(player, items, true);
    }

    public void addItemsToPlayer(Player player, List<ItemStack> items, boolean dropIfNoSpace) {
        int index = items.size();

        for (ItemStack item : items) {
            index -= 1;

            if (hasSpace(player, index)) {
                player.getInventory().addItem(item);
            } else {
                if (dropIfNoSpace) {
                    player.getWorld().dropItemNaturally(player.getLocation(), item);
                }
            }
        }
    }

    public void addArmorToPlayer(Player player, List<ItemStack> armors) {
        for (ItemStack armor : armors) {
            if (!isArmor(armor)) {
                continue;
            }

            if (hasAlreadyArmor(player, armor)) {
                player.getInventory().addItem(armor);

                continue;
            }

            if (isHelmet(armor)) {
                player.getInventory().setHelmet(armor);
            } else if (isChestPlate(armor)) {
                player.getInventory().setChestplate(armor);
            } else if (isLegging(armor)) {
                player.getInventory().setLeggings(armor);
            } else if (isBoot(armor)) {
                player.getInventory().setBoots(armor);
            }
        }
    }

    private boolean hasAlreadyArmor(Player player, ItemStack item) {
        if (isHelmet(item)) {
            return player.getInventory().getHelmet() != null;
        } else if (isChestPlate(item)) {
            return player.getInventory().getChestplate() != null;
        } else if (isLegging(item)) {
            return player.getInventory().getLeggings() != null;
        } else if (isBoot(item)) {
            return player.getInventory().getBoots() != null;
        }

        return false;
    }

    private boolean isArmor(ItemStack item) {
        return isHelmet(item) || isChestPlate(item) || isLegging(item) || isBoot(item);
    }

    private boolean isHelmet(ItemStack item) {
        return item.getType().name().contains("HELMET");
    }

    private boolean isChestPlate(ItemStack item) {
        return item.getType().name().contains("CHESTPLATE");
    }

    private boolean isLegging(ItemStack item) {
        return item.getType().name().contains("LEGGINGS");
    }

    private boolean isBoot(ItemStack item) {
        return item.getType().name().contains("BOOTS");
    }

}