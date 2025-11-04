package com.example.gateway.dto;
public class PaymentRequest {
  public String merchantId;
  public String orderId;
  public long amount;
  public String currency;
  public String cardToken;
  public boolean capture = true;
}
