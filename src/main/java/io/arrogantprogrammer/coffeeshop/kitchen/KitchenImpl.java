package io.arrogantprogrammer.coffeeshop.kitchen;

import io.arrogantprogrammer.coffeeshop.barista.domain.BaristaTicket;
import io.arrogantprogrammer.coffeeshop.domain.ITEM;
import io.arrogantprogrammer.coffeeshop.domain.TicketIn;
import io.arrogantprogrammer.coffeeshop.domain.TicketUp;
import io.arrogantprogrammer.coffeeshop.kitchen.api.Kitchen;
import io.arrogantprogrammer.coffeeshop.kitchen.domain.KitchenTicket;
import jakarta.enterprise.context.ApplicationScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;

@ApplicationScoped
public class KitchenImpl implements Kitchen {
    static final Logger LOGGER = LoggerFactory.getLogger(KitchenImpl.class);

    @Override
    public TicketUp ticketIn(TicketIn ticketIn) {
        Instant timeIn = Instant.now();
        try {
            Thread.sleep(calculateDelay(ticketIn.item()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Instant timeOut = Instant.now();
        Long ttr = timeOut.toEpochMilli() - timeIn.toEpochMilli();
        KitchenTicket kitchenTicket = new KitchenTicket(ticketIn.item(), ticketIn.name(), ttr);
        kitchenTicket.persist();
        LOGGER.debug("Ticket persisted: {}", kitchenTicket);
        return new TicketUp(ticketIn.uuid(), ticketIn.item(),ticketIn.name());
    }

    private long calculateDelay(ITEM item) {

        switch (item) {
            case CAKE_POP:
                return 1000;
            case CROISSANT:
                return 2000;
            case MUFFIN:
                return 3000;
            default:
                return 0;
        }
    }
}
