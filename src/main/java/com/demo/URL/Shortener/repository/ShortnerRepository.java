package com.demo.URL.Shortener.repository;

import com.demo.URL.Shortener.entity.ShortnerUrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShortnerRepository extends JpaRepository<ShortnerUrlEntity, Long> {
    ShortnerUrlEntity findByShortCode(String shortCode);
}
