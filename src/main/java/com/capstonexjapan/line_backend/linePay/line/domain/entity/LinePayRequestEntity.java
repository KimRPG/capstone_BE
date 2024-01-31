package com.capstonexjapan.line_backend.linePay.line.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LinePayRequestEntity {

    @Id
    private String orderId;
    private int amount;
    private String currency;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private List<Packages> packages;

}
