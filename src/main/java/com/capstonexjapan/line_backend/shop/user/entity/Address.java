package com.capstonexjapan.line_backend.shop.user.entity;

import com.capstonexjapan.line_backend.shop.user.controller.request.CreateAddressDTO;
import com.capstonexjapan.line_backend.shop.user.controller.request.CreateUserDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    private String receiverName;

    private String receiverPhoneNumber;

    private String address;

    private String postalCode;

    private String country;

    @CreationTimestamp
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private UserEntity user;

    public Address toEntity(CreateAddressDTO dto, UserEntity user) {
        return Address.builder()
                .user(user)
                .receiverName(dto.getReceiverName())
                .receiverPhoneNumber(dto.getReceiverPhoneNumber())
                .address(dto.getAddress())
                .postalCode(dto.getPostalCode())
                .country(dto.getCountry())
                .build();
    }
}
