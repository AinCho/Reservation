package com.zerobase.reservation.service;

import com.zerobase.reservation.entity.Store;
import com.zerobase.reservation.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreService {
    public final StoreRepository storeRepository;

    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public List<Store> getStores() {
        List<Store> stores = storeRepository.findAll();
        return stores;
    }
}
