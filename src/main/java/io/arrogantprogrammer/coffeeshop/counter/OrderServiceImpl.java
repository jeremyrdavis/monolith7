package io.arrogantprogrammer.coffeeshop.counter;

import io.arrogantprogrammer.coffeeshop.barista.api.Barista;
import io.arrogantprogrammer.coffeeshop.counter.api.OrderService;
import io.arrogantprogrammer.coffeeshop.counter.domain.Order;
import io.arrogantprogrammer.coffeeshop.counter.domain.OrderRepository;
import io.arrogantprogrammer.coffeeshop.domain.*;
import io.smallrye.mutiny.Uni;
import io.vertx.core.eventbus.EventBus;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.arrogantprogrammer.coffeeshop.infrastructure.EventBusUtil.WEB_UPDATES;
import static io.arrogantprogrammer.coffeeshop.infrastructure.EventBusUtil.toJson;

@ApplicationScoped
public class OrderServiceImpl implements OrderService {

    static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Inject
    OrderRepository orderRepository;

    @Inject
    Barista barista;

    @Inject
    EventBus eventBus;

    @Override @Transactional
    public void orderIn(PlaceOrderCommand placeOrderCommand) {

        Order order = Order.from(placeOrderCommand);
        orderRepository.persist(order);
        LOGGER.debug("Order persisted: {}", order);
        order.getLineItems().forEach(lineItem -> {
            DashboardUpdate dashboardUpdate = new DashboardUpdate(order.getUuid(), lineItem.getItem(), order.getName(), STATUS.IN_PROGRESS);
            eventBus.publish(WEB_UPDATES, toJson(dashboardUpdate));
        });
        order.getLineItems().forEach(lineItem -> {
            barista.ticketIn(new TicketIn(order.getUuid(), lineItem.getItem(), order.getName()));
        });
        LOGGER.info("Order placed: {}", order);
    }

    @Override
    public void orderUp(TicketUp ticketUp) {
        LOGGER.debug("Order up: {}", ticketUp);
        DashboardUpdate dashboardUpdate = new DashboardUpdate(ticketUp.uuid(), ticketUp.item(), ticketUp.name(), STATUS.FULFILLED);
        eventBus.publish(WEB_UPDATES, toJson(dashboardUpdate));
        LOGGER.debug("Published update: {}", dashboardUpdate);
    }


    public void remake(RemakeTicketCommand remakeTicketCommand) {

        LOGGER.debug("Remaking: {}", remakeTicketCommand);
        barista.remake(remakeTicketCommand).onItem().transform(ticketUp -> {
            LOGGER.debug("Publishing update: {}", ticketUp);
            return eventBus.publish(WEB_UPDATES, toJson(ticketUp));
        }).await();
    }
}
