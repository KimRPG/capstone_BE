package com.capstonexjapan.line_backend.shop.user.controller.request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDTO {
    private String userEmail;

    private String name;

    private String phoneNumber;
}
