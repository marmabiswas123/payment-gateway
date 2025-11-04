package com.example.gateway.entity;

import javax.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.Instant;

@Entity
@Table(name = "cards")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String merchantId;
    private String token;

    @Lob
    @Column(name = "encrypted_pan", columnDefinition = "LONGBLOB")
    private byte[] encryptedPan;

    @Lob
    @Column(name = "iv", columnDefinition = "BLOB")
    private byte[] iv;

    private String brand;
    private Integer expMonth;
    private Integer expYear;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    // Getters / Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMerchantId() { return merchantId; }
    public void setMerchantId(String merchantId) { this.merchantId = merchantId; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public byte[] getEncryptedPan() { return encryptedPan; }
    public void setEncryptedPan(byte[] encryptedPan) { this.encryptedPan = encryptedPan; }

    public byte[] getIv() { return iv; }
    public void setIv(byte[] iv) { this.iv = iv; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public Integer getExpMonth() { return expMonth; }
    public void setExpMonth(Integer expMonth) { this.expMonth = expMonth; }

    public Integer getExpYear() { return expYear; }
    public void setExpYear(Integer expYear) { this.expYear = expYear; }

    public Instant getCreatedAt() { return createdAt; }
    // no setter required, but you can keep it if you like:
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
