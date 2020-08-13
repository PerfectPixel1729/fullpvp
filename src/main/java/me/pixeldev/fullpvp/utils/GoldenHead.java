package me.pixeldev.fullpvp.utils;

import me.pixeldev.fullpvp.files.FileCreator;

import me.pixeldev.fullpvp.message.Message;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import team.unnamed.inject.InjectAll;
import team.unnamed.inject.name.Named;
import team.unnamed.inject.process.annotation.Singleton;

import java.util.ArrayList;
import java.util.List;

@Singleton
@InjectAll
public class GoldenHead {

    private SkullTexture skullTexture;
    private ItemUtils itemUtils;
    private Message message;

    @Named("config")
    private FileCreator config;

    public ItemStack create() {
        ItemStack skull = skullTexture.getSkull(config.getString("game.golden-head.texture"));

        ItemMeta meta = skull.getItemMeta();
        meta.setDisplayName(config.getString("game.golden-head.name"));
        meta.setLore(config.getStringList("game.golden-head.lore"));

        skull.setItemMeta(meta);

        return itemUtils.addNBTTag(skull, "golden-head", "golden-head");
    }

    public boolean isGoldenHead(ItemStack input) {
        return itemUtils.hasNBTTag(input, "golden-head");
    }

    public boolean canReceiveGoldenHead(Player player) {
        List<ItemStack> goldens = new ArrayList<>();

        for (ItemStack item : player.getInventory().getContents()) {
            if (item == null || item.getType() == null || item.getType() == Material.AIR) {
                continue;
            }

            if (!isGoldenHead(item)) {
                continue;
            }

            goldens.add(item);
        }

        if (goldens.isEmpty()) {
            return true;
        }

        return goldens.size() < 2;
    }

    public void consumeGoldenHead(Player player) {
        player.playSound(player.getLocation(), Sound.EAT, 1, 1);
        player.getInventory().setItemInHand(null);

        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 5 * 20, 0, true, true));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 3 * 20, 1, true, true));

        player.sendMessage(message.getMessage(player, "abilities.golden-head.successfully-consume"));
    }

}