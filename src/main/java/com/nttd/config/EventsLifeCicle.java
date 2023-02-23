package com.nttd.config;

import org.jboss.logging.Logger;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

public class EventsLifeCicle {
    @Inject
    Logger logger;

    void onStart(@Observes StartupEvent ev) {
        logger.info("...:: Microservice Promotion is initializing ::......... ");
    }

    void onStop(@Observes ShutdownEvent ev) {
        logger.info("...:: Microservice Promotion is stopping ::......... ");
    }
}
