package com.example.gateway.repository;
import com.example.gateway.entity.WebhookLog;
import org.springframework.data.jpa.repository.JpaRepository;
public interface WebhookLogRepository extends JpaRepository<WebhookLog, String> {
  WebhookLog findByApiKey(String apiKey);
}
