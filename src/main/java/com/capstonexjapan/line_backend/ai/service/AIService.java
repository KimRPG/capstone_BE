package com.capstonexjapan.line_backend.ai.service;


import com.capstonexjapan.line_backend.ai.controller.response.Answer;
import com.capstonexjapan.line_backend.ai.controller.response.Request;
import com.capstonexjapan.line_backend.ai.controller.response.ResponseDTO;
import com.capstonexjapan.line_backend.ai.entity.Recommend;
import com.capstonexjapan.line_backend.ai.repo.AiJDBC;
import com.capstonexjapan.line_backend.ai.repo.RecommendRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AIService {
    private final ChatClient chatClient;
    private final EmbeddingModel embeddingModel;
    private final RecommendRepo repo;
    private final AiJDBC aiJDBC;

    public Answer getAnswer(String request) {
        return new Answer(chatClient.prompt().user(request).call().content());
    }
    public float[] embedding(String message) {
        return embeddingModel.embedForResponse(List.of(message)).getResult().getOutput();
    }

    public void save(Request request) {
        ResponseDTO dto = new ResponseDTO(request.request(), embedding(request.request()));
        repo.save(Recommend.toEntity(dto));
    }


    public String getRecommend(String request){
        return  aiJDBC.check(embedding(request)).get(0).getName();
    }
}
