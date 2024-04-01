package io.arrogantprogrammer.coffeeshop.web;

import io.smallrye.mutiny.Multi;
import io.vertx.mutiny.core.eventbus.EventBus;
import io.vertx.mutiny.core.eventbus.Message;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.arrogantprogrammer.coffeeshop.infrastructure.EventBusUtil.WEB_UPDATES;

@Path("/dashboard")
public class DashboardApiResource {

    static final Logger LOGGER = LoggerFactory.getLogger(DashboardApiResource.class);

    @Inject
    EventBus eventBus;

    @GET
    @Path("/stream")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public Multi<String> dashboardStream() {

        return eventBus.<String>consumer(WEB_UPDATES)
                .toMulti()
                .map(Message::body);
    }

}
