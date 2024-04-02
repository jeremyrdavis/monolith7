package io.arrogantprogrammer.coffeeshop.barista.api;

import io.arrogantprogrammer.coffeeshop.domain.RemakeTicketCommand;
import io.arrogantprogrammer.coffeeshop.domain.TicketIn;
import io.arrogantprogrammer.coffeeshop.domain.TicketUp;
import io.smallrye.mutiny.Uni;

import java.util.concurrent.CompletionStage;

public interface Barista {

    public void ticketIn(TicketIn ticketIn);

    public Uni<TicketUp> remake(RemakeTicketCommand remakeTicketCommand);
}
