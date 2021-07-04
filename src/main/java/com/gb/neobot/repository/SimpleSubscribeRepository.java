package com.gb.neobot.repository;

import com.gb.neobot.model.SimpleSubscribe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SimpleSubscribeRepository extends JpaRepository<SimpleSubscribe, Long> {
    List<SimpleSubscribe> findAllByUserId(int id);
    SimpleSubscribe findByUserIdAndCoin(int id,String coin);
    void deleteByUserIdAndCoin(int id, String coin);
    List<SimpleSubscribe> findAll();

}
