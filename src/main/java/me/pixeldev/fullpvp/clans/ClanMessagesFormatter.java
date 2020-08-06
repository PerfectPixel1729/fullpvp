package me.pixeldev.fullpvp.clans;

import me.pixeldev.fullpvp.utils.StringDecorator;

import team.unnamed.inject.Inject;
import team.unnamed.inject.process.annotation.Singleton;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class ClanMessagesFormatter {

    @Inject
    private StringDecorator stringDecorator;

    public String getCentered(String line) {
        return stringDecorator.getCenteredMessage(line);
    }

    public List<String> getCenteredMessages(List<String> messages) {
        List<String> newMessages = new ArrayList<>();

        for (String line : messages) {
            if (!line.contains("(center)")) {
                newMessages.add(line);

                continue;
            }

            String replaced = line.replace("(center)", "");

            newMessages.add(getCentered(replaced));
        }

        return newMessages;
    }

}