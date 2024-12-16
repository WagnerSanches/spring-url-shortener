package com.demo.URL.Shortener.repository;

import com.demo.URL.Shortener.entities.ShortnerUrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ShortnerRepository extends JpaRepository<ShortnerUrlEntity, Long> {
    ShortnerUrlEntity findByShortCode(String shortCode);

//    @Modifying
//    @Query("update ShortnerUrlEntity s set s.accessCount = :count where s.shortCode = :shortCode")
//    void incrementAccessCount(@Param("shortCode") String shortCode, @Param("count") int count);
}
