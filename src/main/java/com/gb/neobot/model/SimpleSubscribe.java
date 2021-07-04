package com.gb.neobot.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class SimpleSubscribe {
    @Id
    @GeneratedValue
    private int id;

    private int userId;
    private String coin;

    public SimpleSubscribe() {
    }

    public SimpleSubscribe(int userId, String coin) {
        this.userId = userId;
        this.coin = coin;
    }
}
