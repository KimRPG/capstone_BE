package com.capstonexjapan.line_backend.shop.user.controller.response;

import com.capstonexjapan.line_backend.shop.user.entity.Address;
import com.capstonexjapan.line_backend.shop.user.entity.UserEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetUserDTO {
    private Long userId;
    private String userEmail;
    private String name;
    private String phoneNumber;
    private Date createdAt;
    private List<Address> addresses;

    public static GetUserDTO toDTO(UserEntity user) {
        return GetUserDTO.builder()
                .userId(user.getUserId())
                .userEmail(user.getUserEmail())
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .createdAt(user.getCreatedAt())
                .addresses(user.getAddresses())
                .build();
    }

}
