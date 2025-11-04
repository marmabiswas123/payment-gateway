package com.example.gateway.service;

import com.example.gateway.entity.Payment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class PSPClientService {

    private final RestTemplate rest = new RestTemplate();

    @Value("${psp.simulator.url:http://localhost:8080/psp/charge}")
    private String pspUrl;

    public String charge(Payment p) {
        Map<String, Object> body = new HashMap<>();
        body.put("payment_id", p.getPaymentId());
        body.put("amount", p.getAmount());
        body.put("order_id", p.getOrderId());
        return rest.postForObject(pspUrl, body, String.class);
    }
}
