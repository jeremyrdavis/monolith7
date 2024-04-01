package io.arrogantprogrammer.coffeeshop.barista;

import io.arrogantprogrammer.coffeeshop.barista.api.Barista;
import io.arrogantprogrammer.coffeeshop.barista.domain.BaristaTicket;
import io.arrogantprogrammer.coffeeshop.domain.ITEM;
import io.arrogantprogrammer.coffeeshop.domain.TicketIn;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class TicketInTest {

    static final Logger LOGGER = LoggerFactory.getLogger(TicketInTest.class);

    @Inject
    Barista barista;
    @Test @Transactional
    public void testTicketIn() {
        TicketIn ticketIn = new TicketIn(UUID.randomUUID().toString(), ITEM.ESPRESSO, "John Doe");
        barista.ticketIn(ticketIn);
        assertEquals(1, BaristaTicket.count());
        LOGGER.info("Test ticketIn");
    }

}
