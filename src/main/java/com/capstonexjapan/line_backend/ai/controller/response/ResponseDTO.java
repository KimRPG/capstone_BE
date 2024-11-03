package com.capstonexjapan.line_backend.ai.controller.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseDTO {
    private String name;
    private float[] embedding;

}
