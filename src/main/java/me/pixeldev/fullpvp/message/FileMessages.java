package me.pixeldev.fullpvp.message;

import me.pixeldev.fullpvp.files.FileCreator;

import team.unnamed.inject.Inject;
import team.unnamed.inject.name.Named;

import org.bukkit.entity.Player;

import java.util.List;

public class FileMessages implements Message {

    @Inject
    @Named("language")
    private FileCreator language;

    @Override
    public String getMessage(Player player, String id) {
        return language.getString(id, id).replace("%prefix%", language.getString("prefix"));
    }

    @Override
    public List<String> getMessages(Player player, String id) {
        return language.getStringList(id);
    }

}