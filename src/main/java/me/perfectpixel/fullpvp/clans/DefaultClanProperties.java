package me.perfectpixel.fullpvp.clans;

import lombok.Getter;
import lombok.Setter;

import org.bukkit.ChatColor;

import java.util.List;

@Getter
public class DefaultClanProperties implements ClanProperties {

    @Setter
    private ChatColor color;

    @Setter
    private boolean allowedDamage;

    private final List<String> messages;

    public DefaultClanProperties(String color, boolean allowedDamage, List<String> messages) {
        this.color = ChatColor.valueOf(color);
        this.allowedDamage = allowedDamage;
        this.messages = messages;
    }

}