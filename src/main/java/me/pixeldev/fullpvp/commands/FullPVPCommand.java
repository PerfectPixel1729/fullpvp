package me.pixeldev.fullpvp.commands;

import me.fixeddev.ebcm.parametric.CommandClass;
import me.fixeddev.ebcm.parametric.annotation.ACommand;
import me.fixeddev.ebcm.parametric.annotation.Injected;

import me.fixeddev.ebcm.parametric.annotation.Usage;
import me.pixeldev.fullpvp.Delegates;
import me.pixeldev.fullpvp.files.FileCreator;
import me.pixeldev.fullpvp.message.Message;

import me.pixeldev.fullpvp.utils.StringDecorator;
import org.bukkit.command.CommandSender;

import team.unnamed.inject.InjectAll;
import team.unnamed.inject.name.Named;

import java.util.Arrays;
import java.util.List;

@ACommand(names = "fullpvp")
@InjectAll
public class FullPVPCommand implements CommandClass {

    private StringDecorator stringDecorator;

    @Named("config")
    private FileCreator config;

    @Named("language")
    private FileCreator language;

    @Named("menu")
    private FileCreator menu;

    @Delegates
    private Message fileMessage;

    @ACommand(names = {"", "help"})
    public boolean runMainCommand(@Injected(true) CommandSender sender) {
        List<String> messages = getHelpMessages();

        messages.replaceAll(line -> stringDecorator.getCenteredMessage(line));

        messages.forEach(sender::sendMessage);

        return true;
    }

    @ACommand(names = "reload")
    @Usage(usage = "§8- §9[config, language, menu]")
    public boolean runReloadCommand(@Injected(true) CommandSender sender, String file) {
        if (!sender.hasPermission("fullpvp.admin")) {
            sender.sendMessage(fileMessage.getMessage(null, "i18n.no-permission"));

            return true;
        }

        String successfully = fileMessage.getMessage(null, "reload.successfully").replace("%file%", file);

        switch (file) {
            case "config":
                config.reload();
                sender.sendMessage(successfully);

                break;
            case "menu":
                menu.reload();
                sender.sendMessage(successfully);

                break;
            case "language":
                language.reload();
                sender.sendMessage(successfully);

                break;
            default:
                sender.sendMessage(fileMessage.getMessage(null, "reload.invalid"));
                break;
        }

        return true;
    }

    private List<String> getHelpMessages() {
        return Arrays.asList(
                "§9-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-",
                "§7FullPVP §9v0.0.1 §8- §7Desarrollado por §9PixelDev",
                "§9-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-"
        );
    }

}