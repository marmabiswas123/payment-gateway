package com.example.gateway.service;

import com.example.gateway.entity.Payment;
import com.example.gateway.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepo;
    private final PSPClientService pspClient;

    public PaymentService(PaymentRepository paymentRepo, PSPClientService pspClient) {
        this.paymentRepo = paymentRepo;
        this.pspClient = pspClient;
    }

    public Payment createPayment(String merchantId, String orderId, long amount, String currency, String cardToken, String idempotencyKey) {
        Payment existing = paymentRepo.findByMerchantIdAndIdempotencyKey(merchantId, idempotencyKey);
        if (existing != null) return existing;

        Payment p = new Payment();
        p.setPaymentId("pay_" + UUID.randomUUID().toString().replace("-", "").substring(0,20));
        p.setMerchantId(merchantId);
        p.setOrderId(orderId);
        p.setAmount(amount);
        p.setCurrency(currency);
        p.setStatus("processing");
        p.setCardToken(cardToken);
        p.setIdempotencyKey(idempotencyKey);
        paymentRepo.save(p);

        // Call PSP
        try {
            String resp = pspClient.charge(p);
            p.setRawPspResponse(resp);
            p.setStatus("captured");
        } catch (Exception ex) {
            p.setStatus("failed");
            p.setRawPspResponse("{\"error\":\"" + ex.getMessage() + "\"}");
        }
        paymentRepo.save(p);
        return p;
    }
}
