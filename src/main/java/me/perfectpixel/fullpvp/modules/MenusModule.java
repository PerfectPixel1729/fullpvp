package me.perfectpixel.fullpvp.modules;

import me.perfectpixel.fullpvp.menus.ChestCreatorMenu;
import me.perfectpixel.fullpvp.menus.Menu;

import me.yushust.inject.bind.AbstractModule;

public class MenusModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Menu.class).named("chest-creator").to(ChestCreatorMenu.class).singleton();
    }

}