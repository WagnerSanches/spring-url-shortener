package com.demo.URL.Shortener.repositoy;

import com.demo.URL.Shortener.entities.ShortnerUrlEntity;
import com.demo.URL.Shortener.repository.ShortnerRepository;
import com.demo.URL.Shortener.utils.ShortCodeGeneratorUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ShortnerRepositoryTest {

    @Autowired
    private ShortnerRepository repository;

    private ShortnerUrlEntity shortnerUrlEntity;

    @BeforeAll
    public void setup() {
        shortnerUrlEntity = ShortnerUrlEntity.builder()
                .url("localhost:8080/teste")
                .shortCode(ShortCodeGeneratorUtil.generate())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .accessCount(0)
                .build();
    }

    @Test
    @Order(2)
    @Rollback(value = false)
    public void saveUrl() {
        repository.save(shortnerUrlEntity);

        System.out.println("------------------" + shortnerUrlEntity);
        Assertions.assertThat(shortnerUrlEntity.getShortnerUrlId()).isGreaterThan(0);
    }

    @Test
    @Order(1)
    public void deleteUrl() {
        System.out.println("------------------" + repository.findByShortCode(shortnerUrlEntity.getShortCode()));
        repository.delete(shortnerUrlEntity);
        System.out.println("------------------" + repository.findByShortCode(shortnerUrlEntity.getShortCode()));
    }
}
