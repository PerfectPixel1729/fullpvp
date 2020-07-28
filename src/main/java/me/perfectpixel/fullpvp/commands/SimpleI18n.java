package me.perfectpixel.fullpvp.commands;

import me.fixeddev.ebcm.NamespaceAccesor;
import me.fixeddev.ebcm.i18n.I18n;
import me.fixeddev.ebcm.i18n.Message;

import me.perfectpixel.fullpvp.files.FileCreator;

import me.yushust.inject.Inject;
import me.yushust.inject.name.Named;

import java.util.HashMap;
import java.util.Map;

public class SimpleI18n implements I18n {

    private final Map<String, String> messageMap = new HashMap<>();

    @Inject
    public SimpleI18n(@Named("language") FileCreator language) {
        messageMap.put(Message.COMMAND_NO_PERMISSIONS.getId(), language.getString("i18n.no-permission"));
        messageMap.put(Message.COMMAND_USAGE.getId(), null);
        messageMap.put(Message.INVALID_SUBCOMMAND.getId(), language.getString("i18n.invalid-subcommand"));
        messageMap.put(Message.MISSING_ARGUMENT.getId(), language.getString("i18n.missing-argument"));
        messageMap.put(Message.MISSING_SUBCOMMAND.getId(), language.getString("i18n.missing-subcommand"));
        messageMap.put("provider.invalid.int", language.getString("i18n.invalid-int"));
    }

    @Override
    public String getMessage(String messageId, NamespaceAccesor namespaceAccesor) {
        String message = messageMap.get(messageId);

        if (message != null) {
            return message;
        }

        Message messageObject = Message.findMessage(messageId);

        if (messageObject == null) {
            return null;
        }

        messageId = messageObject.getId();

        return messageMap.get(messageId);
    }

}