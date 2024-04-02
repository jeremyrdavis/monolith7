package io.arrogantprogrammer.coffeeshop.counter.api;

import io.arrogantprogrammer.coffeeshop.domain.RemakeTicketCommand;
import io.arrogantprogrammer.coffeeshop.domain.TicketUp;
import io.arrogantprogrammer.coffeeshop.domain.PlaceOrderCommand;

public interface OrderService {

    void orderIn(PlaceOrderCommand placeOrderCommand);

    void orderUp(TicketUp ticketUp);

    void remake(RemakeTicketCommand remakeTicketCommand);
}
