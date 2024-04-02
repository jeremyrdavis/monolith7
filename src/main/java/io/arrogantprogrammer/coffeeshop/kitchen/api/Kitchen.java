package io.arrogantprogrammer.coffeeshop.kitchen.api;

import io.arrogantprogrammer.coffeeshop.domain.TicketIn;
import io.arrogantprogrammer.coffeeshop.domain.TicketUp;

public interface Kitchen {

    public TicketUp ticketIn(TicketIn ticketIn);
}
