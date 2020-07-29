package me.perfectpixel.fullpvp.utils;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

/**
 * Created by FixedDev
 * Taken from: https://github.com/FixedDev/EzChat/blob/master/src/main/java/me/fixeddev/ezchat/EasyTextComponent.java
 */
public class EasyTextComponent {

    private final BaseComponent builder;

    public EasyTextComponent() {
        builder = new TextComponent("");
    }

    private EasyTextComponent(BaseComponent component) {
        this.builder = component;
    }

    public EasyTextComponent appendWithNewLine(String content) {
        return appendWithNewLine(appendAll(TextComponent.fromLegacyText(content)));
    }

    public EasyTextComponent appendWithNewLine(BaseComponent component) {
        return append(component).addNewLine();
    }

    public EasyTextComponent append(String content) {
        return append(appendAll(TextComponent.fromLegacyText(content)));
    }

    public EasyTextComponent append(BaseComponent component) {
        builder.addExtra(component);

        return this;
    }

    public EasyTextComponent append(EasyTextComponent easyComponent) {
        builder.addExtra(easyComponent.builder);

        return this;
    }

    public EasyTextComponent addNewLine() {
        builder.addExtra("\n");

        return this;
    }

    public EasyTextComponent setHoverShowText(String content) {
        return setHoverShowText(TextComponent.fromLegacyText(content));
    }

    public EasyTextComponent setHoverShowText(EasyTextComponent component) {
        return setHoverShowText(new BaseComponent[]{component.builder});
    }

    public EasyTextComponent setHoverShowText(BaseComponent[] component) {
        return setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, component));
    }

    public EasyTextComponent setHoverShowItem(EasyTextComponent component) {
        return setHoverShowItem(component.builder);
    }

    public EasyTextComponent setHoverShowItem(BaseComponent component) {
        return setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new BaseComponent[]{component}));
    }

    public EasyTextComponent setHoverEvent(HoverEvent event) {
        builder.setHoverEvent(event);

        return this;
    }

    public EasyTextComponent setClickRunCommand(String command) {
        return setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command));
    }


    public EasyTextComponent setClickSuggestCommand(String command) {
        return setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, command));
    }

    public EasyTextComponent setClickOpenUrl(String url) {
        return setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url));
    }

    public EasyTextComponent setClickEvent(ClickEvent event) {
        builder.setClickEvent(event);

        return this;
    }

    public BaseComponent build() {
        return builder;
    }

    public static BaseComponent appendAll(BaseComponent[] components) {
        if (components.length == 0) {
            throw new IllegalArgumentException("Appending 0 components is not allowed!");
        }

        BaseComponent parent = null;

        for (BaseComponent component : components) {
            if (parent == null) {
                parent = component;
                continue;
            }

            parent.addExtra(component);
        }

        return parent;
    }

}