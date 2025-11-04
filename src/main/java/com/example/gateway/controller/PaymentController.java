package com.example.gateway.controller;
import com.example.gateway.dto.PaymentRequest;
import com.example.gateway.dto.PaymentResponse;
import com.example.gateway.entity.Payment;
import com.example.gateway.service.PaymentService;
import com.example.gateway.util.HmacUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
  private final PaymentService paymentService;
  @Value("${app.hmac.key:merchant_secret_demo}")
  private String merchantSecret;

  public PaymentController(PaymentService paymentService) {
    this.paymentService = paymentService;
  }

  @PostMapping
  public PaymentResponse createPayment(@RequestBody PaymentRequest req,
                                       @RequestHeader("Idempotency-Key") String idempotencyKey,
                                       @RequestHeader("X-GW-Timestamp") String ts,
                                       @RequestHeader("X-GW-Signature") String signature) throws Exception {
    // Basic signature verification (demo)
    String payload = ts + "." + org.springframework.util.StreamUtils.copyToString(new java.io.ByteArrayInputStream(new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsBytes(req)), java.nio.charset.StandardCharsets.UTF_8);
    String expected = HmacUtil.hmacSha256Hex(merchantSecret, payload);
    long now = Instant.now().getEpochSecond();
    long t = Long.parseLong(ts);
    if (!expected.equals(signature) || Math.abs(now - t) > 300) {
      throw new RuntimeException("Invalid signature or timestamp");
    }

    Payment p = paymentService.createPayment(req.merchantId, req.orderId, req.amount, req.currency, req.cardToken, idempotencyKey);
    PaymentResponse r = new PaymentResponse();
    r.paymentId = p.getPaymentId();
    r.status = p.getStatus();
    r.amount = p.getAmount();
    return r;
  }
}
