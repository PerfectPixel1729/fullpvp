package me.pixeldev.fullpvp.service;

import me.pixeldev.fullpvp.loader.CommandsLoader;
import me.pixeldev.fullpvp.loader.EventsLoader;
import me.pixeldev.fullpvp.loader.TickLoader;

import team.unnamed.inject.InjectAll;
import team.unnamed.inject.name.Named;

@InjectAll
public final class FullPVPService implements Service {

    private EventsLoader eventsLoader;
    private CommandsLoader commandsLoader;
    private TickLoader tickLoader;

    @Named("clans-service")
    private Service clanService;

    @Named("supplierchests-service")
    private Service supplierChestService;

    @Named("users-service")
    private Service userService;

    @Named("supplierchestsviewers-service")
    private Service supplierChestViewerService;

    @Named("kits-service")
    private Service kitsService;

    @Override
    public void start() {
        tickLoader.load();
        commandsLoader.load();
        eventsLoader.load();

        clanService.start();
        kitsService.start();
        supplierChestService.start();
        supplierChestViewerService.start();
        userService.start();
    }

    @Override
    public void stop() {
        clanService.stop();
        kitsService.stop();
        supplierChestService.stop();
        userService.stop();
        supplierChestViewerService.stop();
    }

}