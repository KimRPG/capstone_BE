package com.capstonexjapan.line_backend.linePay.line.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
class Info {
    private final PaymentUrl paymentUrl;
    private final String transactionId;
    private final String paymentAccessToken;
}
