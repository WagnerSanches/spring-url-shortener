package com.demo.URL.Shortener.repository;

import com.demo.URL.Shortener.entity.ShortenerUrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShortenerRepository extends JpaRepository<ShortenerUrlEntity, Long> {
    ShortenerUrlEntity findByShortCode(String shortCode);
}
