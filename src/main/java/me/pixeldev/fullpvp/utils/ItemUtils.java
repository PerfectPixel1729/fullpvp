package me.pixeldev.fullpvp.utils;

import team.unnamed.inject.process.annotation.Singleton;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;

import org.bukkit.inventory.ItemStack;

@Singleton
public class ItemUtils {

    public ItemStack addNBTTag(ItemStack input, String key, String value) {
        net.minecraft.server.v1_8_R3.ItemStack itemNMS = CraftItemStack.asNMSCopy(input);

        NBTTagCompound tagCompound = itemNMS.hasTag() ? itemNMS.getTag() : new NBTTagCompound();

        tagCompound.setString(key, value);

        itemNMS.setTag(tagCompound);

        return CraftItemStack.asBukkitCopy(itemNMS);
    }

}