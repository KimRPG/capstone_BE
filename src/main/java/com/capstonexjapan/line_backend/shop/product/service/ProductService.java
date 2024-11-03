package com.capstonexjapan.line_backend.shop.product.service;

import com.capstonexjapan.line_backend.ai.service.AIService;
import com.capstonexjapan.line_backend.ftp.FtpServer;
import com.capstonexjapan.line_backend.shop.product.controller.request.CreateProduct;
import com.capstonexjapan.line_backend.shop.product.controller.request.UpdateProduct;
import com.capstonexjapan.line_backend.shop.product.controller.response.ReadProduct;
import com.capstonexjapan.line_backend.shop.product.entity.Product;
import com.capstonexjapan.line_backend.shop.product.repository.ProductRepo;
import com.capstonexjapan.line_backend.shop.store.entity.Store;
import com.capstonexjapan.line_backend.shop.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepo productRepo;
    private final StoreService storeService;
    private final AIService aiService;
    private final FtpServer ftpServer;

    public void addProduct(CreateProduct dto, String filename) {
        Store store = storeService.findById(dto.getStoreId());
        float[] embedding = aiService.embedding(dto.getName());
        productRepo.save(new Product().toEntity(dto, store, filename,embedding));
    }

    public void addProduct(CreateProduct dto) {
        Store store = storeService.findById(dto.getStoreId());
        float[] embedding = aiService.embedding(dto.getName());
        productRepo.save(new Product().toEntity(dto,store,embedding));
    }

    public List<ReadProduct> readAllProduct() {
        return findAllProduct()
                .stream()
                .map(entity -> new ReadProduct().toDTO(entity))
                .collect(Collectors.toList());
    }

    public List<Product> findAllProduct() {
        return productRepo.findAllBy()
                .stream()
                .map(entity -> new Product().toEntity(entity))
                .collect(Collectors.toList());
    }

    public ReadProduct readProduct(Long id) {
        Product product =findById(id);
        return new ReadProduct().toDTO(product);
    }

    public Product findById(Long id) {
        return productRepo.findById(id).orElseThrow();
    }

    @Transactional
    public void updateById(Long id, UpdateProduct dto) {
        float[] embedding = new float[1536];

        if(!dto.getName().isEmpty()){
            embedding = aiService.embedding(dto.getName());
        }
        findById(id).update(dto,embedding);
    }

    public void deleteById(Long id) {
        productRepo.deleteById(id);
    }

    @Transactional
    public void orderProduct(Product product,Integer amount) {
        product.orderProduct(amount);
    }

    public String uploadFile(MultipartFile file) throws IOException {
        UUID uuid = UUID.randomUUID();
        ftpServer.upload(file, uuid);
        return uuid+ftpServer.getFileExtension(file.getContentType());
    }

}
