package io.arrogantprogrammer.coffeeshop.barista.domain;

import io.arrogantprogrammer.coffeeshop.domain.ITEM;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
public class BaristaTicket extends PanacheEntity {

    @Enumerated(EnumType.STRING)
    ITEM item;

    String name;

    Long ttr;

    public BaristaTicket() {
    }

    public BaristaTicket(ITEM item, String name, Long ttr) {
        this.item = item;
        this.name = name;
        this.ttr = ttr;
    }
}
