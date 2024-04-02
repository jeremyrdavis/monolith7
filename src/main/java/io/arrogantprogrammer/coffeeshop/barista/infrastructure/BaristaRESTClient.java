package io.arrogantprogrammer.coffeeshop.barista.infrastructure;

import io.arrogantprogrammer.coffeeshop.domain.TicketIn;
import io.arrogantprogrammer.coffeeshop.domain.TicketUp;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient
public interface BaristaRESTClient {

    @POST
    @Path("/make")
    public Uni<TicketUp> make(TicketIn ticketIn);
}
