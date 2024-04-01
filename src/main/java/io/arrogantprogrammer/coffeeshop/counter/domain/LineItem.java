package io.arrogantprogrammer.coffeeshop.counter.domain;

import io.arrogantprogrammer.coffeeshop.domain.ITEM;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

@Entity
public class LineItem extends PanacheEntity {

    @ManyToOne
    Order order;
    @Enumerated(EnumType.STRING)
    ITEM item;

    public LineItem() {
    }

    public Order getOrder() {
        return order;
    }

    public ITEM getItem() {
        return item;
    }
}
