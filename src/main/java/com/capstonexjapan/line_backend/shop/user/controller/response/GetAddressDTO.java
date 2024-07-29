package com.capstonexjapan.line_backend.shop.user.controller.response;

import com.capstonexjapan.line_backend.shop.user.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetAddressDTO {
    private String receiverName;

    private String receiverPhoneNumber;

    private String address;

    private String postalCode;

    private String country;
    public static GetAddressDTO toDTO(Address address) {
        return GetAddressDTO.builder()
                .receiverName(address.getReceiverName())
                .receiverPhoneNumber(address.getReceiverPhoneNumber())
                .address(address.getAddress())
                .postalCode(address.getPostalCode())
                .country(address.getCountry())
                .build();
    }

}
