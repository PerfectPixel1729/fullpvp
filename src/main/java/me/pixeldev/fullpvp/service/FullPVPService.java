package me.pixeldev.fullpvp.service;

import me.pixeldev.fullpvp.loader.CommandsLoader;
import me.pixeldev.fullpvp.loader.EventsLoader;
import me.pixeldev.fullpvp.loader.TickLoader;

import team.unnamed.inject.Inject;
import team.unnamed.inject.name.Named;

public final class FullPVPService implements Service {

    @Inject
    private EventsLoader eventsLoader;

    @Inject
    private CommandsLoader commandsLoader;

    @Inject
    private TickLoader tickLoader;

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