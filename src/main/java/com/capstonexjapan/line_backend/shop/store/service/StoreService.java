package com.capstonexjapan.line_backend.shop.store.service;

import com.capstonexjapan.line_backend.shop.store.controller.request.CreateStore;
import com.capstonexjapan.line_backend.shop.store.controller.response.ReadStore;
import com.capstonexjapan.line_backend.shop.store.entity.Store;
import com.capstonexjapan.line_backend.shop.store.repository.StoreRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StoreService {
    private final StoreRepo storeRepo;

    public void createStore(CreateStore store) {
        storeRepo.save(new Store().toEntity(store));
    }

    public ReadStore readStore(Long id) {
        return new ReadStore().toDTO(findById(id));
    }

    public Store findById(Long id) {
        return storeRepo.findById(id).orElseThrow();
    }

    public List<ReadStore> readAllStore() {
        return storeRepo.findAll()
                .stream()
                .map(store -> new ReadStore().toDTO(store))
                .collect(Collectors.toList());
    }

}
