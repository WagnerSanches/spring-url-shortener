package com.demo.URL.Shortener.repositoy;

import com.demo.URL.Shortener.entity.ShortnerUrlEntity;
import com.demo.URL.Shortener.repository.ShortnerRepository;
import com.demo.URL.Shortener.util.ShortCodeGeneratorUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ShortnerRepositoryTest {

    @Autowired
    private ShortnerRepository repository;
    private ShortnerUrlEntity shortnerUrlEntity;
    private final String URL = "localhost:8080/teste";

    @BeforeAll
    public void setup() {
        this.shortnerUrlEntity = ShortnerUrlEntity.builder()
                .url(URL)
                .shortCode(ShortCodeGeneratorUtil.generate())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .accessCount(0)
                .build();
    }

    @Test
    @Order(1)
    @Rollback(value = false)
    public void saveUrl() {
        System.out.println("------------------- ShortnerRepositoryTest.saveUrl() ------------------");

        repository.save(this.shortnerUrlEntity);

        System.out.println(this.shortnerUrlEntity);
        Assertions.assertThat(this.shortnerUrlEntity.getShortnerUrlId()).isGreaterThan(0);
    }

    @Test
    @Order(2)
    public void getAllUrls() {
        System.out.println("------------------- ShortnerRepositoryTest.getAllUrls() ------------------");
        System.out.println(this.shortnerUrlEntity);
        List<ShortnerUrlEntity> urls = repository.findAll();

        Assertions.assertThat(urls)
                .isNotEmpty()
                .anyMatch(url ->
                        url.getShortCode().equals(this.shortnerUrlEntity.getShortCode()));
    }

    @Test
    @Order(3)
    public void getUrl() {
        System.out.println("------------------- ShortnerRepositoryTest.getUrl() ------------------");
        Optional<ShortnerUrlEntity> url = Optional.ofNullable(
                repository.findByShortCode(this.shortnerUrlEntity.getShortCode())
        );

        Assertions.assertThat(url).isNotEmpty();
    }

    @Test
    @Order(4)
    public void updateUrl() {
        System.out.println("------------------- ShortnerRepositoryTest.updateUrl() ------------------");

        Optional<ShortnerUrlEntity> url = Optional.ofNullable(
                repository.findByShortCode(this.shortnerUrlEntity.getShortCode())
        );

        Assertions.assertThat(url)
                .isPresent()
                .get()
                .extracting(ShortnerUrlEntity::getUrl)
                .isEqualTo(URL);

        String shortCode = this.shortnerUrlEntity.getShortCode();
        String newUrl = "localhost:3001/updated";
        this.shortnerUrlEntity.updateUrl(newUrl);
        repository.save(this.shortnerUrlEntity);

        url = Optional.ofNullable(
                repository.findByShortCode(this.shortnerUrlEntity.getShortCode())
        );

        Assertions.assertThat(url)
                .isPresent()
                .get()
                .extracting(ShortnerUrlEntity::getUrl)
                .isEqualTo(newUrl);

        Assertions.assertThat(url)
                .isPresent()
                .get()
                .extracting(ShortnerUrlEntity::getShortCode)
                .isEqualTo(shortCode);
    }

    @Test
    @Order(5)
    @Rollback(value = false)
    public void deleteUrl() {
        System.out.println("------------------- ShortnerRepositoryTest.deleteUrl() ------------------");
        System.out.println(this.shortnerUrlEntity);
        repository.delete(shortnerUrlEntity);

        Optional<ShortnerUrlEntity> deletedShortnerUrlEntity = Optional.ofNullable(
                repository.findByShortCode(this.shortnerUrlEntity.getShortCode())
        );
        Assertions.assertThat(deletedShortnerUrlEntity).isEmpty();

    }
}
