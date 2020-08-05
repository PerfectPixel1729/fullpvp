package me.pixeldev.fullpvp.combatlog.announcer;

import com.google.common.base.Strings;

import me.pixeldev.fullpvp.Delegates;
import me.pixeldev.fullpvp.files.FileCreator;
import me.pixeldev.fullpvp.message.Message;

import org.bukkit.ChatColor;

import team.unnamed.inject.Inject;
import team.unnamed.inject.name.Named;
import team.unnamed.inject.process.annotation.Singleton;

@Singleton
public class CombatLogMessageDecorator {

    @Inject
    @Delegates
    private Message fileMessage;

    @Inject
    @Named("config")
    private FileCreator config;

    public String format(int currentSeconds) {
        String symbol = config.getString("combatlog.symbol");

        String greenSymbol = ChatColor.GREEN + symbol;
        String redSymbol = ChatColor.RED + symbol;

        int duration = config.getInt("combatlog.duration");

        if (duration == currentSeconds) {
            return Strings.repeat(greenSymbol, currentSeconds / 2);
        }

        return Strings.repeat(greenSymbol, currentSeconds / 2) + Strings.repeat(redSymbol, (duration / 2) - (currentSeconds / 2));
    }

    public String formatFinish() {
        return fileMessage.getMessage(null, "combatlog.finished");
    }

    public String quitMessage() {
        return fileMessage.getMessage(null, "combatlog.player-disconnect");
    }

}