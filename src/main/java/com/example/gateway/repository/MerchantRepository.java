package com.example.gateway.repository;
import com.example.gateway.entity.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
public interface MerchantRepository extends JpaRepository<Merchant, String> {
  Merchant findByApiKey(String apiKey);
}
