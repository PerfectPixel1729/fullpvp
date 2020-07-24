package me.perfectpixel.fullpvp.message;

import me.yushust.inject.Inject;
import me.yushust.inject.name.Named;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class FileMessages implements Message {

    @Inject
    @Named("language")
    private FileConfiguration language;

    @Override
    public String getMessage(Player player, String id) {
        return language.getString(id).replace("%prefix%", language.getString("prefix"));
    }

    @Override
    public List<String> getMessages(Player player, String id) {
        return language.getStringList(id);
    }

}