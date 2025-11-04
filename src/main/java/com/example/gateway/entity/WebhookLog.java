package com.example.gateway.entity;

import javax.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.Instant;

@Entity
@Table(name = "webhook_logs")
public class WebhookLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String source;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String payload;

    private String signature;
    private Boolean verified;

    // NEW: apiKey used by repository queries
    @Column(name = "api_key")
    private String apiKey;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    public String getPayload() { return payload; }
    public void setPayload(String payload) { this.payload = payload; }

    public String getSignature() { return signature; }
    public void setSignature(String signature) { this.signature = signature; }

    public Boolean getVerified() { return verified; }
    public void setVerified(Boolean verified) { this.verified = verified; }

    public String getApiKey() { return apiKey; }
    public void setApiKey(String apiKey) { this.apiKey = apiKey; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
