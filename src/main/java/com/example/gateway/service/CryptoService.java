package com.example.gateway.service;

import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Service
public class CryptoService {
    private static final String ALGO = "AES/GCM/NoPadding";
    private static final int GCM_TAG_LENGTH = 128; // bits
    private final SecretKey secretKey;

    public CryptoService() {
        String rawKey = System.getenv("APP_ENC_KEY");
        if (rawKey == null || rawKey.isBlank()) {
            throw new IllegalStateException("APP_ENC_KEY is not set");
        }

        // sanitize: remove whitespace, newlines, and any surrounding quotes
        String s = rawKey.trim();
        if ((s.startsWith("\"") && s.endsWith("\"")) || (s.startsWith("'") && s.endsWith("'"))) {
            s = s.substring(1, s.length() - 1);
        }
        s = s.replaceAll("\\s+", ""); // remove all internal whitespace

        byte[] keyBytes = null;
        // try base64 decode first
        try {
            keyBytes = Base64.getDecoder().decode(s);
        } catch (IllegalArgumentException e) {
            // not base64 — try hex decode
            keyBytes = hexStringToByteArray(s);
        }

        if (keyBytes == null || keyBytes.length != 32) {
            throw new IllegalStateException("APP_ENC_KEY must decode to 32 bytes (AES-256). Got: " +
                    (keyBytes == null ? "null" : keyBytes.length));
        }
        this.secretKey = new SecretKeySpec(keyBytes, "AES");
    }

    private static byte[] hexStringToByteArray(String s) {
        String hex = s.startsWith("0x") ? s.substring(2) : s;
        if (hex.length() % 2 != 0) {
            // invalid hex length
            throw new IllegalArgumentException("Hex string has odd length");
        }
        int len = hex.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            int hi = Character.digit(hex.charAt(i), 16);
            int lo = Character.digit(hex.charAt(i + 1), 16);
            if (hi == -1 || lo == -1) {
                throw new IllegalArgumentException("Invalid hex character");
            }
            data[i / 2] = (byte) ((hi << 4) + lo);
        }
        return data;
    }

    public byte[] encrypt(byte[] plaintext, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGO);
        GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, spec);
        return cipher.doFinal(plaintext);
    }

    public byte[] decrypt(byte[] ciphertext, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGO);
        GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, spec);
        return cipher.doFinal(ciphertext);
    }
}
