package me.pixeldev.fullpvp.modules;

import me.perfectpixel.fullpvp.service.*;

import me.pixeldev.fullpvp.service.*;
import me.yushust.inject.bind.AbstractModule;

public class ServiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Service.class).named("clans-service").to(ClanService.class).singleton();
        bind(Service.class).named("supplierchests-service").to(SupplierChestService.class).singleton();
        bind(Service.class).named("supplierchestsviewers-service").to(SupplierChestViewerService.class).singleton();
        bind(Service.class).named("users-service").to(UserService.class).singleton();
        bind(Service.class).named("fullpvp-service").to(FullPVPService.class).singleton();
    }

}