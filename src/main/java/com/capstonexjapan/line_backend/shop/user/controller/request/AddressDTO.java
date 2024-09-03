package com.capstonexjapan.line_backend.shop.user.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@AllArgsConstructor
public class AddressDTO {
    private String receiverName;
    private String receiverPhoneNumber;
    private String address;
    private String postalCode;
    private String country;
}
