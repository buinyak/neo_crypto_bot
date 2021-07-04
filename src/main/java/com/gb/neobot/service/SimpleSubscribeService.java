package com.gb.neobot.service;

import com.gb.neobot.model.SimpleSubscribe;
import com.gb.neobot.repository.SimpleSubscribeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SimpleSubscribeService {
    private final SimpleSubscribeRepository simpleSubscribeRepository;

    public SimpleSubscribeService(SimpleSubscribeRepository simpleSubscribeRepository) {
        this.simpleSubscribeRepository = simpleSubscribeRepository;
    }

    @Transactional(readOnly = true)
    public List<SimpleSubscribe> findAllByUserId(int id) {
        return simpleSubscribeRepository.findAllByUserId(id);
    }
    @Transactional(readOnly = true)
    public List<SimpleSubscribe> findAll() {
        return simpleSubscribeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public SimpleSubscribe findByUserIdAndCoin(int id, String coin) {
        return simpleSubscribeRepository.findByUserIdAndCoin(id,coin);
    }


    @Transactional
    public void addSimpleSubscribe(SimpleSubscribe simpleSubscribe) {
        simpleSubscribeRepository.save(simpleSubscribe);
    }
    @Transactional
    public void deleteByUserIdAndCoin(int id, String coin){
        simpleSubscribeRepository.deleteByUserIdAndCoin(id,coin);
    }

}
