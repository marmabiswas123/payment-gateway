package com.example.gateway.controller;
import com.example.gateway.dto.TokenRequest;
import com.example.gateway.dto.TokenResponse;
import com.example.gateway.service.TokenService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tokens")
public class TokenController {
  private final TokenService tokenService;
  public TokenController(TokenService tokenService){ this.tokenService = tokenService; }

  @PostMapping
  public TokenResponse tokenize(@RequestBody TokenRequest req, @RequestHeader(value="X-Merchant-Id", required=false) String merchantId) throws Exception {
    if (merchantId == null) merchantId = "m_demo";
    String token = tokenService.tokenize(merchantId, req.cardNumber, Integer.parseInt(req.expMonth), Integer.parseInt(req.expYear));
    TokenResponse r = new TokenResponse();
    r.token = token;
    r.expiresAt = "2099-01-01T00:00:00Z"; // demo expiry
    return r;
  }
}
