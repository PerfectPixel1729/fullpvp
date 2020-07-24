package me.perfectpixel.fullpvp.modules;

import me.perfectpixel.fullpvp.Delegates;
import me.perfectpixel.fullpvp.message.FileMessages;
import me.perfectpixel.fullpvp.message.Message;
import me.perfectpixel.fullpvp.message.SimpleMessageDecorator;
import me.perfectpixel.fullpvp.message.placeholder.PlaceholderApplier;
import me.perfectpixel.fullpvp.message.placeholder.PlaceholderApplierDecorator;
import me.yushust.inject.bind.AbstractModule;

public class MessageModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(PlaceholderApplier.class).to(PlaceholderApplierDecorator.class).singleton();
        bind(Message.class).qualified(Delegates.class).to(FileMessages.class).singleton();
        bind(Message.class).to(SimpleMessageDecorator.class).singleton();
    }

}