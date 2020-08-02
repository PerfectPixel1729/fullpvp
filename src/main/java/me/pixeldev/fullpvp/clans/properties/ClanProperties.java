package me.pixeldev.fullpvp.clans.properties;

import org.bukkit.ChatColor;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ClanProperties extends ConfigurationSerializable {

    ChatColor getColor();

    void setColor(ChatColor color);

    boolean isAllowedDamage();

    void setAllowedDamage(boolean allowedDamage);

    List<String> getMessages();

    @Override
    default Map<String, Object> serialize() {
        Map<String, Object> propertiesSerializable = new HashMap<>();

        propertiesSerializable.put("color", getColor().name());
        propertiesSerializable.put("allowed-damage", isAllowedDamage());
        propertiesSerializable.put("messages", getMessages());

        return propertiesSerializable;
    }

}