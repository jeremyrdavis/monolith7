package io.arrogantprogrammer.coffeeshop.counter.domain;

import io.arrogantprogrammer.coffeeshop.domain.PlaceOrderCommand;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity @Table(name = "CoffeeshopOrders")
public class Order extends PanacheEntity {

    String uuid;
    String name;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "order", cascade = CascadeType.ALL)
    List<LineItem> lineItems = new ArrayList<>();

    public static Order from(PlaceOrderCommand placeOrderCommand) {
        Order order = new Order();
        order.uuid = placeOrderCommand.uuid();
        order.name = placeOrderCommand.name();
        placeOrderCommand.items().forEach(item -> {
            LineItem lineItem = new LineItem();
            lineItem.item = item;
            order.lineItems.add(lineItem);
        });
        return order;
    }

    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }
}
