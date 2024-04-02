package io.arrogantprogrammer.coffeeshop.barista;

import io.agroal.api.AgroalDataSource;
import io.arrogantprogrammer.coffeeshop.barista.api.Barista;
import io.arrogantprogrammer.coffeeshop.barista.domain.BaristaTicket;
import io.arrogantprogrammer.coffeeshop.barista.infrastructure.BaristaRESTClient;
import io.arrogantprogrammer.coffeeshop.counter.api.OrderService;
import io.arrogantprogrammer.coffeeshop.domain.RemakeTicketCommand;
import io.arrogantprogrammer.coffeeshop.domain.TicketIn;
import io.arrogantprogrammer.coffeeshop.domain.TicketUp;
import io.quarkus.agroal.DataSource;
import io.smallrye.context.api.CurrentThreadContext;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.context.ThreadContext;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

///@ApplicationScoped
public class OutpostImpl implements Barista {

    static final Logger LOGGER = LoggerFactory.getLogger(OutpostImpl.class);

    @Inject
    @Channel("barista")
    Emitter<TicketIn> baristaEmitter;

    @Override
    public Uni<TicketUp> remake(RemakeTicketCommand remakeTicketCommand) {
        return null;
    }

    @Override @CurrentThreadContext(cleared = { ThreadContext.TRANSACTION })
    public void ticketIn(TicketIn ticketIn) {
        CompletionStage<Void> ack = baristaEmitter.send(ticketIn);
        LOGGER.debug("Ticket in: {} sent to Kafka", ticketIn);
    }
}
