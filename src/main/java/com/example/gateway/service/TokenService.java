package com.example.gateway.service;

import com.example.gateway.entity.Card;
import com.example.gateway.repository.CardRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TokenService {

    private final CardRepository cardRepo;
    private final CryptoService crypto;

    public TokenService(CardRepository cardRepo, CryptoService crypto) {
        this.cardRepo = cardRepo;
        this.crypto = crypto;
    }

    public String tokenize(String merchantId, String pan, int expMonth, int expYear) throws Exception {
        byte[] iv = new byte[12];
        byte[] ct = crypto.encrypt(pan.getBytes("UTF-8"), iv);
        String token = "tok_" + UUID.randomUUID().toString().replace("-", "").substring(0,24);
        Card c = new Card();
        c.setMerchantId(merchantId);
        c.setToken(token);
        c.setEncryptedPan(ct);
        c.setIv(iv);
        c.setExpMonth(expMonth);
        c.setExpYear(expYear);
        cardRepo.save(c);
        return token;
    }

    public String revealPan(String token) throws Exception {
        Card c = cardRepo.findByToken(token);
        if (c == null) return null;
        byte[] plain = crypto.decrypt(c.getEncryptedPan(), c.getIv());
        return new String(plain, "UTF-8");
    }
}
