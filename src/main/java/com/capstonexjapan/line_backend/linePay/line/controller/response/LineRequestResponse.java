package com.capstonexjapan.line_backend.linePay.line.controller.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@AllArgsConstructor
public class LineRequestResponse {
    private final String returnCode;
    private final String returnMessage;
    private final Info info;
}

@Getter
@AllArgsConstructor
class Info {
    private final PaymentUrl paymentUrl;
    private final String transactionId;
    private final String paymentAccessToken;
}

@Getter
@AllArgsConstructor
class PaymentUrl {

    private final String web;
    private final String app;
}
