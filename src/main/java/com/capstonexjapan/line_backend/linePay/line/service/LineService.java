package com.capstonexjapan.line_backend.linePay.line.service;

import com.capstonexjapan.line_backend.linePay.line.controller.request.request.LineRequestRequest;
import com.capstonexjapan.line_backend.linePay.line.controller.response.LineRequestResponse;
import com.capstonexjapan.line_backend.linePay.line.repository.LineRepository;
import com.capstonexjapan.line_backend.linePay.line.util.HmacSignUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class LineService {

    private final LineRepository lineRepository;

    @Value("${line-pay.channelId}")
    private String channelId;

    @Value("${line-pay.channelSecretKey}")
    private String channelSecretKey;

    @Value("${line-pay.url}")
    private String url;

    // Request API
    public LineRequestResponse postRequest(LineRequestRequest request) {
        RestTemplate restTemplate = new RestTemplate();

        try {
            String nonce = UUID.randomUUID().toString();

            ObjectMapper objectMapper = new ObjectMapper();
            String requestStr = objectMapper.writeValueAsString(request);

            HttpHeaders headers = getHeaders("/v3/payments/request", requestStr, nonce);

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

    private HttpHeaders getHeaders(String uri, String requestStr, String nonce) throws Exception {

        String authText = HmacSignUtil.getAuthTex(channelSecretKey, uri, requestStr, nonce);
        String hmacText = HmacSignUtil.encrypt(channelSecretKey, authText);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-LINE-ChannelId", channelId);
        headers.set("X-LINE-Authorization-Nonce", nonce);
        headers.set("X-LINE-Authorization", hmacText);

        return headers;
    }


    private static String getQueryString(Map<String, List<String>> params) {
        if (params != null && !params.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            params.forEach((k, v) -> {
                if (v != null) v.forEach(s -> sb.append("&").append(k).append("=").append(s));
            });
            return sb.substring(1);
        } else {
            return null;
        }
    }
}
