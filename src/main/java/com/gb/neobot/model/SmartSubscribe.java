package com.gb.neobot.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class SmartSubscribe {
    @Id
    @GeneratedValue
    private int id;

    private int userId;
    private String coin;
    private Double price;
    private Double percent;

    public SmartSubscribe() {
    }

    public SmartSubscribe(int userId, String coin, Double price,Double percent) {
        this.userId = userId;
        this.coin = coin;
        this.price = price;
        this.percent = percent;
    }
}
