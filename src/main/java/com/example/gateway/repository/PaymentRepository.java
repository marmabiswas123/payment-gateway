package com.example.gateway.repository;

import com.example.gateway.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment findByMerchantIdAndIdempotencyKey(String merchantId, String idempotencyKey);
    Payment findByPaymentId(String paymentId);
}
