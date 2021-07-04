package com.gb.neobot.service;

import com.gb.neobot.model.SimpleSubscribe;
import com.gb.neobot.model.SmartSubscribe;
import com.gb.neobot.repository.SmartSubscribeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SmartSubscribeService {
    private final SmartSubscribeRepository smartSubscribeRepository;

    @Transactional(readOnly = true)
    public List<SmartSubscribe> findAllByUserId(int id) {
        return smartSubscribeRepository.findAllByUserId(id);
    }
    public SmartSubscribeService(SmartSubscribeRepository smartSubscribeRepository) {
        this.smartSubscribeRepository = smartSubscribeRepository;
    }
    @Transactional(readOnly = true)
    public SmartSubscribe findByUserIdAndCoin(int id, String coin) {
        return smartSubscribeRepository.findByUserIdAndCoin(id,coin);
    }
    @Transactional(readOnly = true)
    public List<SmartSubscribe> findAll() {
        return smartSubscribeRepository.findAll();
    }
    @Transactional
    public void addSmartSubscribe(SmartSubscribe smartSubscribe) {
        smartSubscribeRepository.save(smartSubscribe);
    }
    @Transactional(readOnly = true)
    public SmartSubscribe findByUserId(long id) {
        return smartSubscribeRepository.findByUserId(id);
    }
    @Transactional
    public void deleteByUserIdAndCoin(int id, String coin){
        smartSubscribeRepository.deleteByUserIdAndCoin(id,coin);
    }
}
