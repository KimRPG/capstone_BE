package com.capstonexjapan.line_backend.ai.controller;

import com.capstonexjapan.line_backend.ai.controller.response.Answer;
import com.capstonexjapan.line_backend.ai.controller.response.Request;
import com.capstonexjapan.line_backend.ai.service.AIService;
import com.capstonexjapan.line_backend.shop.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AiController {
    private final AIService aiService;

//    @PostMapping("/ai/embedding")
    public String embed(@RequestBody Request dto) {
        aiService.save(dto);
        return "추가됨";
    }

    @GetMapping("/ai/answer")
    public Answer answer(@RequestBody Request dto) {
        return aiService.getAnswer(dto.request());
    }

    @GetMapping("/ai/recommend")
    public String recommend(@RequestBody Request dto)throws IOException {
        return aiService.getRecommend(aiService.embedding(dto.request()));
    }

    @GetMapping("/ai/recommend/product")
    public List<Product> recommendProduct(@RequestBody Request dto){
        return aiService.getRecommendProduct(aiService.embedding(dto.request()));
    }
}


