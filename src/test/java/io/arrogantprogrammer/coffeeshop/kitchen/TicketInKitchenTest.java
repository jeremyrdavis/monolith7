package io.arrogantprogrammer.coffeeshop.kitchen;

import io.arrogantprogrammer.coffeeshop.kitchen.api.Kitchen;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@QuarkusTest
public class TicketInKitchenTest {
    static final Logger LOGGER = LoggerFactory.getLogger(TicketInKitchenTest.class);

    @Inject
    Kitchen kitchen;

    @Test
    public void ticketInTest() {

    }
}
