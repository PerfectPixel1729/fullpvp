package me.pixeldev.fullpvp.modules;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;

import me.pixeldev.fullpvp.Delegates;
import me.pixeldev.fullpvp.PlaceHolderHandler;
import me.pixeldev.fullpvp.message.FileMessages;
import me.pixeldev.fullpvp.message.Message;
import me.pixeldev.fullpvp.message.SimpleMessageDecorator;
import me.pixeldev.fullpvp.message.menu.MessageMenu;
import me.pixeldev.fullpvp.message.menu.SimpleMessageMenu;
import me.pixeldev.fullpvp.message.placeholder.PlaceholderApplier;
import me.pixeldev.fullpvp.message.placeholder.PlaceholderApplierDecorator;

import me.yushust.inject.bind.AbstractModule;

public class MessageModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(PlaceholderApplier.class).to(PlaceholderApplierDecorator.class).singleton();
        bind(Message.class).qualified(Delegates.class).to(FileMessages.class).singleton();
        bind(Message.class).to(SimpleMessageDecorator.class).singleton();
        bind(MessageMenu.class).to(SimpleMessageMenu.class).singleton();
        bind(PlaceholderExpansion.class).to(PlaceHolderHandler.class).singleton();
    }

}