package me.perfectpixel.fullpvp.modules;

import me.perfectpixel.fullpvp.service.*;

import me.yushust.inject.bind.AbstractModule;

public class ServiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Service.class).named("clans-service").to(ClansService.class).singleton();
        bind(Service.class).named("supplierchests-service").to(SupplierChestsService.class).singleton();
        bind(Service.class).named("supplierchestsviewers-service").to(SupplierChestViewersService.class).singleton();
        bind(Service.class).named("users-service").to(UsersService.class).singleton();
        bind(Service.class).named("fullpvp-service").to(FullPVPService.class).singleton();
    }

}