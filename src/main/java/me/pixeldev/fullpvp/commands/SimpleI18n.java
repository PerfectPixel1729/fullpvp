package me.pixeldev.fullpvp.commands;

import me.fixeddev.ebcm.i18n.DefaultI18n;
import me.fixeddev.ebcm.i18n.Message;

import me.pixeldev.fullpvp.Delegates;

import team.unnamed.inject.Inject;

public class SimpleI18n extends DefaultI18n {

    @Inject
    public SimpleI18n(@Delegates me.pixeldev.fullpvp.message.Message message) {
        setMessage(Message.COMMAND_NO_PERMISSIONS.getId(), message.getMessage(null, "i18n.no-permission"));
        setMessage(Message.COMMAND_USAGE.getId(), message.getMessage(null, "i18n.invalid-usage"));
        setMessage(Message.INVALID_SUBCOMMAND.getId(), message.getMessage(null,"i18n.invalid-subcommand"));
        setMessage(Message.MISSING_ARGUMENT.getId(), message.getMessage(null,"i18n.missing-argument"));
        setMessage(Message.MISSING_SUBCOMMAND.getId(), message.getMessage(null,"i18n.missing-subcommand"));
        setMessage("provider.invalid.int", message.getMessage(null,"i18n.invalid-int"));
    }

}