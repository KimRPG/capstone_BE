package com.capstonexjapan.line_backend.linePay.line.domain.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
class Package {
    @Id
    private String packageId;
    private int amount;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "package_id")
    private List<Product> products;

    // Constructors, getters, and setters
}