package me.perfectpixel.fullpvp.message;

import me.perfectpixel.fullpvp.files.FileManager;

import me.yushust.inject.Inject;
import me.yushust.inject.name.Named;

import org.bukkit.entity.Player;

import java.util.List;

public class FileMessages implements Message {

    @Inject
    @Named("language")
    private FileManager language;

    @Override
    public String getMessage(Player player, String id) {
        return language.getString(id, id).replace("%prefix%", language.getString("prefix"));
    }

    @Override
    public List<String> getMessages(Player player, String id) {
        return language.getStringList(id);
    }

}