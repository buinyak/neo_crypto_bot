package com.gb.neobot.repository;

import com.gb.neobot.model.SimpleSubscribe;
import com.gb.neobot.model.SmartSubscribe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SmartSubscribeRepository extends JpaRepository<SmartSubscribe, Long> {
    List<SmartSubscribe> findAllByUserId(int id);
    SmartSubscribe findByUserId(long id);
    SmartSubscribe findByUserIdAndCoin(int id, String coin);
    void deleteByUserIdAndCoin(int id, String coin);
    List<SmartSubscribe> findAll();
}
