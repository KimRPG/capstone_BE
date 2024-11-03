package com.capstonexjapan.line_backend.ai.entity;


import com.capstonexjapan.line_backend.ai.controller.response.ResponseDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Builder
public class Recommend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(columnDefinition = "vector(1536)")
    private float[] embedding;

    public static Recommend toEntity(ResponseDTO dto) {
        return Recommend.builder()
                .name(dto.getName())
                .embedding(dto.getEmbedding())
                .build();
    }
}
