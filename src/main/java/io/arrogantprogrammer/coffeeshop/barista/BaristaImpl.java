package io.arrogantprogrammer.coffeeshop.barista;

import io.agroal.api.AgroalDataSource;
import io.arrogantprogrammer.coffeeshop.barista.api.Barista;
import io.arrogantprogrammer.coffeeshop.barista.domain.BaristaTicket;
import io.arrogantprogrammer.coffeeshop.counter.api.OrderService;
import io.arrogantprogrammer.coffeeshop.domain.ITEM;
import io.arrogantprogrammer.coffeeshop.domain.RemakeTicketCommand;
import io.arrogantprogrammer.coffeeshop.domain.TicketIn;
import io.arrogantprogrammer.coffeeshop.domain.TicketUp;
import io.quarkus.agroal.DataSource;
import io.smallrye.context.api.CurrentThreadContext;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;
import org.eclipse.microprofile.context.ThreadContext;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CompletionStage;

@ApplicationScoped @Default
public class BaristaImpl implements Barista {
    static final Logger LOGGER = LoggerFactory.getLogger(BaristaImpl.class);

    @Inject
    @DataSource("baristads")
    AgroalDataSource dataSource;
    @Inject
    OrderService orderService;
    @Override
    public void ticketIn(TicketIn ticketIn) {
        LOGGER.debug("Ticket in: {}", ticketIn);
        Instant timeIn = Instant.now();
        try {
            long delayTime = calculateDelay(ticketIn.item());
            LOGGER.debug("Delay time: {}", delayTime);
            Thread.sleep(delayTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Instant timeOut = Instant.now();
        Long ttr = timeOut.toEpochMilli() - timeIn.toEpochMilli();
        BaristaTicket baristaTicket = new BaristaTicket(ticketIn.item(), ticketIn.name(), ttr);
        baristaTicket.persist();
        LOGGER.debug("Ticket persisted: {}", baristaTicket);
        orderService.orderUp(new TicketUp(ticketIn.uuid(), ticketIn.item(),ticketIn.name()));
    }

//    @Inject
//    @Channel("barista-in")
//    Emitter<TicketIn> baristaEmitter;
//
//    @Override @CurrentThreadContext(cleared = { ThreadContext.TRANSACTION })
//    public void ticketIn(TicketIn ticketIn) {
//        CompletionStage<Void> ack = baristaEmitter.send(ticketIn);
//        LOGGER.debug("Ticket in: {} sent to Kafka", ticketIn);
//    }
//
//    @Incoming("barista-up")
//    public void ticketUp(TicketUp ticketUp) {
//        LOGGER.debug("Ticket up: {} received from Kafka", ticketUp);
//        orderService.orderUp(ticketUp);
//    }


    @Override
    public Uni<TicketUp> remake(RemakeTicketCommand remakeTicketCommand) {
        return Uni
                .createFrom()
                .item(new TicketUp(remakeTicketCommand.ticketId(), remakeTicketCommand.item(), remakeTicketCommand.name()))
                .onItem()
                .delayIt().by(Duration.ofMillis(calculateDelay(remakeTicketCommand.item())));
        };

    private long calculateDelay(ITEM item) {
        switch (item) {
            case COFFEE_BLACK:
                return 3000;
            case COFFEE_WITH_ROOM:
                return 3000;
            case CAPPUCCINO:
                return 7000;
            case LATTE:
                return 7100;
            case ESPRESSO:
                return 5000;
            case ESPRESSO_DOUBLE:
                return 5100;
            default:
                return 4500;
        }
    }
}
