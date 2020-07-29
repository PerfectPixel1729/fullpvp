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
    private Service clanService;

    @Inject
    @Named("supplierchests-service")
    private Service supplierChestService;

    @Inject
    @Named("users-service")
    private Service userService;

    @Inject
    @Named("supplierchestsviewers-service")
    private Service supplierChestViewerService;

    @Override
    public void start() {
        tickLoader.load();
        commandsLoader.load();
        eventsLoader.load();

        placeholderExpansion.register();

        clanService.start();
        supplierChestService.start();
        supplierChestViewerService.start();
        userService.start();
    }

    @Override
    public void stop() {
        clanService.stop();
        supplierChestService.stop();
        userService.stop();
        supplierChestViewerService.stop();
    }

}