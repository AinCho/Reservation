package com.zerobase.reservation.controller;

import com.zerobase.reservation.entity.Store;
import com.zerobase.reservation.exception.AlreadyDeletedException;
import com.zerobase.reservation.exception.StoreNotFoundException;
import com.zerobase.reservation.model.StoreDeleteInput;
import com.zerobase.reservation.model.StoreInput;
import com.zerobase.reservation.model.StoreModel;
import com.zerobase.reservation.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RequiredArgsConstructor
@RestController
public class ApiStoreController {
    private final StoreRepository storeRepository;

    @GetMapping("/store")
    public List<StoreModel> store() {
        List<StoreModel> storeList = new ArrayList<>();
        return storeList;
    }

    @PostMapping("/store")
    public Store addStore(@RequestBody StoreInput storeInput) {
        Store store = Store.builder()
                .storeName(storeInput.getStoreName())
                .location(storeInput.getLocation())
                .explanation(storeInput.getExplanation())
                .build();

        Store resultStore = storeRepository.save(store);
        return resultStore;
    }

    @GetMapping("/store/{storeName}")
    public Store store(@PathVariable String storeName) {
        Optional<Store> store = storeRepository.findByStoreName(storeName);
        if (store.isPresent()) {
            return store.get();
        }
        return null;
    }

    @ExceptionHandler(StoreNotFoundException.class)
    public ResponseEntity<String> handlerNoticeNotFoundException(StoreNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), BAD_REQUEST);
    }

    @PutMapping("/store/{id}")
    public void updateStore(@PathVariable long id, @RequestBody StoreInput storeInput) {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new StoreNotFoundException("매장이 존재하지 않습니다."));
        store.setStoreName(storeInput.getStoreName());
        store.setExplanation(storeInput.getExplanation());
        store.setLocation(storeInput.getLocation());
        storeRepository.save(store);
    }

    @ExceptionHandler(AlreadyDeletedException.class)
    public ResponseEntity<String> handlerAlreadyDeletedException(AlreadyDeletedException exception) {
        return new ResponseEntity<>(exception.getMessage(), OK);
    }

    @DeleteMapping("/store/{id}")
    public void deleteStore(@PathVariable long id) {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new StoreNotFoundException("매장이 존재하지 않습니다."));

        if (store.isDeleted()) {
            throw new AlreadyDeletedException("존재하지 않는 매장입니다.");
        }
        store.setDeleted(true);
        storeRepository.save(store);
    }

    @DeleteMapping("/store")
    public void deleteStoreName(@RequestBody StoreDeleteInput noticeDeleteInput) {
        Store store = storeRepository.findByStoreName(noticeDeleteInput.getStoreDeleteName())
                .orElseThrow(() -> new StoreNotFoundException("매장이 존재하지 않습니다."));

        store.setDeleted(true);
        storeRepository.save(store);
    }

}
