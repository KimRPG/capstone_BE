package com.capstonexjapan.line_backend.linePay.line.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
class PaymentUrl {

    private final String web;
    private final String app;
}
