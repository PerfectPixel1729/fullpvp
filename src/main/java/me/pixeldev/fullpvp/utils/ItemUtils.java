package me.pixeldev.fullpvp.utils;

import team.unnamed.inject.process.annotation.Singleton;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;

import org.bukkit.inventory.ItemStack;

@Singleton
public class ItemUtils {

    public ItemStack addNBTTag(ItemStack input, String key, String value) {
        net.minecraft.server.v1_8_R3.ItemStack nmsItem = asNMSCopy(input);

        NBTTagCompound tagCompound = getNBTTag(input);

        tagCompound.setString(key, value);

        nmsItem.setTag(tagCompound);

        return asBukkitCopy(nmsItem);
    }

    public boolean hasNBTTag(ItemStack input, String key) {
        net.minecraft.server.v1_8_R3.ItemStack nmsItem = asNMSCopy(input);

        return nmsItem.hasTag() && nmsItem.getTag().hasKey(key);
    }

    private net.minecraft.server.v1_8_R3.ItemStack asNMSCopy(ItemStack input) {
        return CraftItemStack.asNMSCopy(input);
    }

    private ItemStack asBukkitCopy(net.minecraft.server.v1_8_R3.ItemStack input) {
        return CraftItemStack.asBukkitCopy(input);
    }

    private NBTTagCompound getNBTTag(ItemStack input) {
        net.minecraft.server.v1_8_R3.ItemStack nmsItem = asNMSCopy(input);

        return nmsItem.hasTag() ? nmsItem.getTag() : new NBTTagCompound();
    }

}