package com.example.gateway.controller;
import com.example.gateway.entity.WebhookLog;
import com.example.gateway.repository.WebhookLogRepository;
import com.example.gateway.util.HmacUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/webhooks/psp")
public class WebhookController {
  private final WebhookLogRepository logRepo;
  @Value("${psp.simulator.secret:psp_secret_demo}")
  private String pspSecret;

  public WebhookController(WebhookLogRepository logRepo) { this.logRepo = logRepo; }

  @PostMapping
  public String receive(@RequestBody String payload, @RequestHeader("X-PSP-Signature") String sig) throws Exception {
    String expected = HmacUtil.hmacSha256Hex(pspSecret, payload);
    WebhookLog log = new WebhookLog();
    log.setSource("psp");
    log.setPayload(payload);
    log.setSignature(sig);
    log.setVerified(expected.equals(sig));
    logRepo.save(log);
    // parse payload and update payment status in DB as needed (omitted for brevity)
    return "{\"status\":\"ok\"}";
  }
}
