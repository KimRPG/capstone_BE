package com.capstonexjapan.line_backend.linePay.line.service;

import com.capstonexjapan.line_backend.linePay.line.controller.request.LineRequestRequest;
import com.capstonexjapan.line_backend.linePay.line.controller.response.LineRequestResponse;
import com.capstonexjapan.line_backend.linePay.line.domain.entity.RedirectUrls;
import com.capstonexjapan.line_backend.linePay.line.util.Headers;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class LineService {

    @Value("${line-pay.channelId}")
    private String channelId;

    @Value("${line-pay.channelSecretKey}")
    private String channelSecretKey;

    @Value("${line-pay.url}")
    private String url;

    @Value("${line-pay.confirmUrl}")
    private String confirmUrl;

    @Value("${line-pay.cancelUrl}")
    private String cancelUrl;

    private final String nonce = UUID.randomUUID().toString();

    // Request API
    public LineRequestResponse postRequest(LineRequestRequest request) {
        RestTemplate restTemplate = new RestTemplate();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String requestBody = objectMapper.writeValueAsString(request);
            HttpHeaders headers = Headers.postHeaders(
                    channelId,
                    channelSecretKey,
                    "/v3/payments/request",
                    requestBody,
                    nonce);

            HttpEntity<LineRequestRequest> requestEntity = new HttpEntity<>(request, headers);
            ResponseEntity<LineRequestResponse> responseEntity = restTemplate.exchange(
                    url + "/v3/payments/request",
                    HttpMethod.POST,
                    requestEntity,
                    LineRequestResponse.class
            );
            return responseEntity.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
//    public LineRequestRequest createBody(){
//        RedirectUrls urls = new RedirectUrls(confirmUrl, cancelUrl);
//
//
//    }
}
