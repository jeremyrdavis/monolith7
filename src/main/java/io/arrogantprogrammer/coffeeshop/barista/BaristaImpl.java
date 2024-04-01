package io.arrogantprogrammer.coffeeshop.barista;

import io.arrogantprogrammer.coffeeshop.barista.api.Barista;
import io.arrogantprogrammer.coffeeshop.domain.ITEM;
import io.arrogantprogrammer.coffeeshop.domain.TicketIn;
import io.arrogantprogrammer.coffeeshop.domain.TicketUp;
import jakarta.enterprise.context.ApplicationScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class BaristaImpl implements Barista {
    static final Logger LOGGER = LoggerFactory.getLogger(BaristaImpl.class);
    @Override
    public TicketUp ticketIn(TicketIn ticketIn) {
        try {
            Thread.sleep(calculateDelay(ticketIn.item()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
