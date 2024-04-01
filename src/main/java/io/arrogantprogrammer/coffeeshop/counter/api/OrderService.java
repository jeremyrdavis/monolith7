package io.arrogantprogrammer.coffeeshop.counter.api;

import io.arrogantprogrammer.coffeeshop.domain.TicketUp;
import io.arrogantprogrammer.coffeeshop.domain.PlaceOrderCommand;

public interface OrderService {

    public void orderIn(PlaceOrderCommand placeOrderCommand);

    public void orderUp(TicketUp ticketUp);
}
