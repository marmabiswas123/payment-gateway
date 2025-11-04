package com.example.gateway.util;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Hex;
public class HmacUtil {
  public static String hmacSha256Hex(String key, String payload) throws Exception {
    Mac mac = Mac.getInstance("HmacSHA256");
    mac.init(new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256"));
    byte[] raw = mac.doFinal(payload.getBytes("UTF-8"));
    return Hex.encodeHexString(raw);
  }
}
