package me.perfectpixel.fullpvp.service;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;

import me.perfectpixel.fullpvp.loader.CommandsLoader;
import me.perfectpixel.fullpvp.loader.EventsLoader;
import me.perfectpixel.fullpvp.loader.TickLoader;

import me.yushust.inject.Inject;
import me.yushust.inject.name.Named;

public final class FullPVPService implements Service {

    @Inject
    private EventsLoader eventsLoader;

    @Inject
    private CommandsLoader commandsLoader;

    @Inject
    private TickLoader tickLoader;

    @Inject
    private PlaceholderExpansion placeholderExpansion;

    @Inject
    @Named("clans-service")
    private Service clansService;

    @Inject
    @Named("supplierchests-service")
    private Service supplierChestsService;

    @Inject
    @Named("users-service")
    private Service usersService;

    @Inject
    @Named("supplierchestsviewers-service")
    private Service supplierChestsViewersService;

    @Override
    public void start() {
        tickLoader.load();
        commandsLoader.load();
        eventsLoader.load();

        placeholderExpansion.register();

        clansService.start();
        supplierChestsService.start();
        supplierChestsViewersService.start();
        usersService.start();
    }

    @Override
    public void stop() {
        clansService.stop();
        supplierChestsService.stop();
        usersService.stop();
        supplierChestsViewersService.stop();
    }

}