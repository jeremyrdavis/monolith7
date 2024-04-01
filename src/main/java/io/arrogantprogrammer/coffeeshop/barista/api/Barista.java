package io.arrogantprogrammer.coffeeshop.barista.api;

import io.arrogantprogrammer.coffeeshop.domain.TicketIn;
import io.arrogantprogrammer.coffeeshop.domain.TicketUp;

public interface Barista {

    public TicketUp ticketIn(TicketIn ticketIn);
}
