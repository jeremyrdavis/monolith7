package io.arrogantprogrammer.coffeeshop.domain;

import java.util.UUID;

public record
TicketIn(String uuid,
         ITEM item,
         String name) {
}
