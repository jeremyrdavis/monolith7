package io.arrogantprogrammer.coffeeshop.counter;

import io.arrogantprogrammer.coffeeshop.barista.api.Barista;
import io.arrogantprogrammer.coffeeshop.counter.api.OrderService;
import io.arrogantprogrammer.coffeeshop.counter.domain.Order;
import io.arrogantprogrammer.coffeeshop.domain.ITEM;
import io.arrogantprogrammer.coffeeshop.domain.PlaceOrderCommand;
import io.arrogantprogrammer.coffeeshop.domain.TicketIn;
import io.arrogantprogrammer.coffeeshop.domain.TicketUp;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectSpy;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@QuarkusTest
public class PlaceOrderTest {

    @Inject
    OrderService orderService;

    @InjectSpy
    Barista barista;

    @BeforeEach
    public void setup() {
        Barista mockBarista = Mockito.mock(Barista.class);
    }

    @Test
    public void testOrderIn() {
        PlaceOrderCommand placeOrderCommand = new PlaceOrderCommand(UUID.randomUUID().toString(), Collections.singletonList(ITEM.LATTE), "Joe");
        orderService.orderIn(placeOrderCommand);
        Mockito.verify(barista, Mockito.times(1)).ticketIn(any(TicketIn.class));
        assertEquals(1, Order.count());
    }
}
