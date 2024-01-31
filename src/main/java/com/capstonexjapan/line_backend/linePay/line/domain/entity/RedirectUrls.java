package com.capstonexjapan.line_backend.linePay.line.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RedirectUrls {
    private String confirmUrl;
    private String cancelUrl;
}
