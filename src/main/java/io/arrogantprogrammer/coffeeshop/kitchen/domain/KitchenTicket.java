package io.arrogantprogrammer.coffeeshop.kitchen.domain;

import io.arrogantprogrammer.coffeeshop.domain.ITEM;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
public class KitchenTicket extends PanacheEntity {
    @Enumerated(EnumType.STRING)
    ITEM item;

    String name;

    Long ttr;

    public KitchenTicket() {
    }

    public KitchenTicket(ITEM item, String name, Long ttr) {
        this.item = item;
        this.name = name;
        this.ttr = ttr;
    }

    @Override
    public String toString() {
        return "KitchenTicket{" +
                "item=" + item +
                ", name='" + name + '\'' +
                ", ttr=" + ttr +
                ", id=" + id +
                '}';
    }
}
