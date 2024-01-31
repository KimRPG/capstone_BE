package com.capstonexjapan.line_backend.linePay.line.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Packages {

    @Id
    private String id;
    private int amount;

    @OneToMany
    @JoinColumn(name = "id")
    private List<Product> products;
}
