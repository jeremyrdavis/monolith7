package io.arrogantprogrammer.coffeeshop.domain;

public record RemakeTicketCommand(String ticketId, ITEM item, String name) {
}
