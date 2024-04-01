package io.arrogantprogrammer.coffeeshop.domain;

import java.util.UUID;

public record TicketUp(String uuid,
                       ITEM item,
                       String name) {
}
