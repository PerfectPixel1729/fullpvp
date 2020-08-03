package me.pixeldev.fullpvp.modules;

import me.pixeldev.fullpvp.menus.ChestCreatorMenu;
import me.pixeldev.fullpvp.menus.clans.ClanDisbandMenu;
import me.pixeldev.fullpvp.menus.clans.ClanMainMenu;
import me.pixeldev.fullpvp.menus.Menu;

import team.unnamed.inject.bind.AbstractModule;

public class MenusModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Menu.class).named("chest-creator").to(ChestCreatorMenu.class).singleton();
        bind(Menu.class).named("clan-main").to(ClanMainMenu.class).singleton();
        bind(Menu.class).named("clan-disband").to(ClanDisbandMenu.class).singleton();
    }

}