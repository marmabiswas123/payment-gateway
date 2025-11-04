package com.example.gateway.entity;

import javax.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.Instant;

@Entity
@Table(name = "merchants")
public class Merchant {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    private String name;

    @Column(name = "api_key")
    private String apiKey;

    @Column(name = "secret_plain")
    private String secretPlain;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    // getters & setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getApiKey() { return apiKey; }
    public void setApiKey(String apiKey) { this.apiKey = apiKey; }

    public String getSecretPlain() { return secretPlain; }
    public void setSecretPlain(String secretPlain) { this.secretPlain = secretPlain; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
