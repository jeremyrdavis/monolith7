package io.arrogantprogrammer.coffeeshop.web;

import io.arrogantprogrammer.coffeeshop.counter.api.OrderService;
import io.arrogantprogrammer.coffeeshop.domain.PlaceOrderCommand;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/api")
public class CoffeeshopApiResource {

    static final Logger LOGGER = LoggerFactory.getLogger(CoffeeshopApiResource.class);

    @Inject
    OrderService orderService;

    @POST
    @Path("/order")
    @Transactional
    public Response placeOrder(PlaceOrderCommand placeOrderCommand) {

        orderService.orderIn(placeOrderCommand);
        LOGGER.debug("Order placed: {}", placeOrderCommand);
        return Response.accepted().build();
    }
}
