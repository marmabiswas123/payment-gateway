package com.example.gateway.repository;

import com.example.gateway.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
    Card findByToken(String token);
}
