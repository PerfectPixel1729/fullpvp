package me.pixeldev.fullpvp.clans.properties;

import lombok.Getter;
import lombok.Setter;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

@Getter
public class DefaultClanProperties implements ClanProperties {

    @Setter
    private ChatColor color;

    @Setter
    private boolean allowedDamage;

    private final List<String> messages;

    public DefaultClanProperties() {
        this("GRAY", false, new ArrayList<>());
    }

    public DefaultClanProperties(String color, boolean allowedDamage, List<String> messages) {
        this.color = ChatColor.valueOf(color);
        this.allowedDamage = allowedDamage;
        this.messages = messages;
    }

}