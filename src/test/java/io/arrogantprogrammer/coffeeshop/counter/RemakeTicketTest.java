package io.arrogantprogrammer.coffeeshop.counter;

import io.arrogantprogrammer.coffeeshop.counter.api.OrderService;
import io.arrogantprogrammer.coffeeshop.domain.ITEM;
import io.arrogantprogrammer.coffeeshop.domain.RemakeTicketCommand;
import io.arrogantprogrammer.coffeeshop.domain.TicketUp;
import io.arrogantprogrammer.coffeeshop.infrastructure.EventBusUtil;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.core.eventbus.EventBus;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class RemakeTicketTest {

    static final Logger LOGGER = LoggerFactory.getLogger(RemakeTicketTest.class);

    @Inject
    OrderService orderService;

    @Inject
    EventBus eventBus;

    @Test
    public void testRemake() {
        boolean received = false;
        RemakeTicketCommand remakeTicketCommand = new RemakeTicketCommand(UUID.randomUUID().toString(), ITEM.ESPRESSO, "Jane");
        orderService.remake(remakeTicketCommand);
        eventBus.consumer(EventBusUtil.WEB_UPDATES).completionHandler(voidAsyncResult -> {
            assertNotNull(voidAsyncResult);
            LOGGER.debug("EventBus received: {}", voidAsyncResult);
        });
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(false);
    }
}
