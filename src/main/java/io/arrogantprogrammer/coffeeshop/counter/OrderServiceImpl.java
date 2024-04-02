package io.arrogantprogrammer.coffeeshop.counter;

import io.arrogantprogrammer.coffeeshop.barista.api.Barista;
import io.arrogantprogrammer.coffeeshop.counter.api.OrderService;
import io.arrogantprogrammer.coffeeshop.counter.domain.Order;
import io.arrogantprogrammer.coffeeshop.counter.domain.OrderRepository;
import io.arrogantprogrammer.coffeeshop.domain.PlaceOrderCommand;
import io.arrogantprogrammer.coffeeshop.domain.RemakeTicketCommand;
import io.arrogantprogrammer.coffeeshop.domain.TicketIn;
import io.arrogantprogrammer.coffeeshop.domain.TicketUp;
import io.vertx.core.eventbus.EventBus;
import jakarta.enterprise.context.ApplicationScoped;
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
            barista.ticketIn(new TicketIn(order.getUuid(), lineItem.getItem(), order.getName()));
        });
        LOGGER.info("Order placed: {}", order);
    }

    @Override
    public void orderUp(TicketUp ticketUp) {
        LOGGER.debug("Order up: {}", ticketUp);
        eventBus.publish(WEB_UPDATES, toJson(ticketUp));
        LOGGER.debug("Published update: {}", ticketUp);
    }

    public void remake(RemakeTicketCommand remakeTicketCommand) {

        LOGGER.debug("Remaking: {}", remakeTicketCommand);
        barista.remake(remakeTicketCommand).onItem().transform(ticketUp -> {
            LOGGER.debug("Publishing update: {}", ticketUp);
            return eventBus.publish(WEB_UPDATES, toJson(ticketUp));
        });
    }
}
