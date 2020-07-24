package me.perfectpixel.fullpvp.message;

import org.bukkit.entity.Player;

import java.util.List;

public interface Message {

    String getMessage(Player player, String id);

    List<String> getMessages(Player player, String id);

}