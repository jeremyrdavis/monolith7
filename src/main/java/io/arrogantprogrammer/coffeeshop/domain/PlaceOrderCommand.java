package io.arrogantprogrammer.coffeeshop.domain;

import java.util.List;
import java.util.UUID;

public record PlaceOrderCommand(String uuid,
                                List<ITEM> items,
                                String name) {
}
