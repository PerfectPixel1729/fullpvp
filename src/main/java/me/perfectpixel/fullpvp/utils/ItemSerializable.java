package me.perfectpixel.fullpvp.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ItemSerializable {

    public static String encodeItem(ItemStack itemStack) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream)) {
            dataOutput.writeObject(itemStack);
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to save item.", exception);
        }
    }

    public static ItemStack decodeItem(String data) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
        try (BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream)) {
            return (ItemStack) dataInput.readObject();
        } catch (IOException | ClassNotFoundException exception) {
            return new ItemStack(Material.AIR, 1);
        }
    }

}