package com.capstonexjapan.line_backend.linePay.line.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class Headers {
    public static HttpHeaders postHeaders(String channelId, String channelSecretKey, String uri, String requestBody, String nonce) throws Exception {

        String authText = HmacSignUtil.getAuthTex(channelSecretKey, uri, requestBody, nonce);
        String hmacText = HmacSignUtil.encrypt(channelSecretKey, authText);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-LINE-ChannelId", channelId);
        headers.set("X-LINE-Authorization-Nonce", nonce);
        headers.set("X-LINE-Authorization", hmacText);

        return headers;
    }
}
