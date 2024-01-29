package com.capstonexjapan.line_backend.linePay.line.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class HmacSignUtil {

    public static String getAuthTex(String secretKey, String requestUrl,  String requestBody, String nonce) {
        return secretKey + requestUrl + requestBody + nonce;
    }

    public static String encrypt(String secret, String message) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
        sha256_HMAC.init(secretKey);
        return Base64.getEncoder().encodeToString(sha256_HMAC.doFinal(message.getBytes()));
    }
}
