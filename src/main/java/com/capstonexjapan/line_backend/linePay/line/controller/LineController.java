package com.capstonexjapan.line_backend.linePay.line.controller;

import com.capstonexjapan.line_backend.linePay.line.controller.request.LineRequestRequest;
import com.capstonexjapan.line_backend.linePay.line.controller.response.LineRequestResponse;
import com.capstonexjapan.line_backend.linePay.line.service.LineService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/payments")
public class LineController {
    private final LineService lineService;

    // Request API
    @PostMapping(path = "/request")
    public ResponseEntity<LineRequestResponse> requestPayment(@RequestBody LineRequestRequest request) {
        LineRequestResponse response = lineService.postRequest(request);

        return ResponseEntity.ok().body(response);
    }

    // Confirm API
//    @PostMapping(path = "{$transactionId}/confirm")
//    public ResponseEntity<LineConfirmResponse> confirmPayment(@RequestBody LineConfirmRequest request) {
//        LineConfirmResponse response = lineService.postConfirm(request);
//
//        return ResponseEntity.ok().body(response);
//    }

    // Capture API

    // Void API

    // Refund API

    // Payment Details API
}
