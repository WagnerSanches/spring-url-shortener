package com.demo.URL.Shortener.service;

import com.demo.URL.Shortener.dto.ShortenerUrlDto;
import com.demo.URL.Shortener.entity.ShortenerUrlEntity;
import com.demo.URL.Shortener.repository.ShortenerRepository;
import com.demo.URL.Shortener.service.impl.ShortenerServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ShortnerServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ShortenerRepository repository;

    @InjectMocks
    private ShortenerServiceImpl shortnerService;

    private String shortCode;
    private ShortenerUrlDto shortnerUrlDto;
    private ShortenerUrlEntity shortenerUrlEntity;

    @BeforeAll
    public void setup() {
        String url = "localhost:8080";
        this.shortCode = "abc1";
        this.shortnerUrlDto = new ShortenerUrlDto(url);
        this.shortenerUrlEntity = ShortenerUrlEntity.builder()
                .shortenerUrlId(1L)
                .url(url)
                .shortCode(shortCode)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .accessCount(0)
                .build();
    }

    @Order(1)
    @Test
    public void getUrls() {
        given(repository.findAll()).willReturn(List.of(this.shortenerUrlEntity));
        List<ShortenerUrlEntity> shortnerUrlEntities = this.shortnerService.getUrls();

        assertThat(shortnerUrlEntities).isNotEmpty();
        assertThat(shortnerUrlEntities.size()).isGreaterThan(0);
    }


    @Order(2)
    @Test
    public void createUrl() {
        given(repository.save(any(ShortenerUrlEntity.class))).willReturn(this.shortenerUrlEntity);
        ShortenerUrlEntity shortenerUrlEntity1 = this.shortnerService.createUrl(this.shortnerUrlDto);

        assertThat(shortenerUrlEntity1).isNotNull();
    }

    @Order(3)
    @Test
    public void getUrl() {
        given(repository.findByShortCode(this.shortenerUrlEntity.getShortCode())).willReturn(this.shortenerUrlEntity);
        Optional<ShortenerUrlEntity> shortnerUrlEntity1 = this.shortnerService.getUrl(this.shortenerUrlEntity.getShortCode());

        assertThat(shortnerUrlEntity1).isPresent();
    }

    @Order(4)
    @Test
    public void changeUrl() {
        given(repository.findByShortCode(this.shortenerUrlEntity.getShortCode())).willReturn(this.shortenerUrlEntity);

        this.shortenerUrlEntity.updateUrl("localhost:1234");
        given(repository.save(this.shortenerUrlEntity)).willReturn(this.shortenerUrlEntity);

        Optional<ShortenerUrlEntity> shortnerUrlEntity1 = this.shortnerService.changeUrl(this.shortCode, "localhost:1234");

        assertThat(shortnerUrlEntity1).isPresent();
    }

    @Order(5)
    @Test
    public void deleteUrl() {
        given(repository.findByShortCode(this.shortenerUrlEntity.getShortCode())).willReturn(this.shortenerUrlEntity);
        willDoNothing().given(repository).delete(this.shortenerUrlEntity);

        boolean result = this.shortnerService.deleteUrl(this.shortenerUrlEntity.getShortCode());
        Assert.isTrue(result, "Entity was not deleted!");
    }

}
