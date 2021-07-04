package com.gb.neobot.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Null;

@Getter
@Setter
@Entity
public class User {
    @Id
    @GeneratedValue
    private int id;

    private Long chatId;
    private Integer state;
    @Null
    private String smartSubscribeCoin;

    public User() {
    }

    public User(Long chatId, Integer state) {
        this.chatId = chatId;
        this.state = state;
    }
    public User(Long chatId, Integer state,String smartSubscribe) {
        this.chatId = chatId;
        this.state = state;
        this.smartSubscribeCoin = smartSubscribe;
    }
}
