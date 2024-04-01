package io.arrogantprogrammer.coffeeshop.barista;

import io.arrogantprogrammer.coffeeshop.barista.api.Barista;
import io.arrogantprogrammer.coffeeshop.barista.domain.BaristaTicket;
import io.arrogantprogrammer.coffeeshop.domain.ITEM;
import io.arrogantprogrammer.coffeeshop.domain.TicketIn;
import io.arrogantprogrammer.coffeeshop.domain.TicketUp;
import jakarta.enterprise.context.ApplicationScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;

@ApplicationScoped
public class BaristaImpl implements Barista {
    static final Logger LOGGER = LoggerFactory.getLogger(BaristaImpl.class);
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
        BaristaTicket baristaTicket = new BaristaTicket(ticketIn.item(), ticketIn.name(), ttr);
        baristaTicket.persist();
        LOGGER.debug("Ticket persisted: {}", baristaTicket);
        return new TicketUp(ticketIn.uuid(), ticketIn.item(),ticketIn.name());
    }

    private long calculateDelay(ITEM item) {
        switch (item) {
            case ESPRESSO:
                return 1000;
            case CAPPUCCINO:
                return 2000;
            case LATTE:
                return 3000;
            default:
                return 0;
        }
    }
}
