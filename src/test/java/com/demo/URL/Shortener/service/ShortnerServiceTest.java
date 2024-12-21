package com.demo.URL.Shortener.service;

import com.demo.URL.Shortener.dtos.ShortnerUrlDto;
import com.demo.URL.Shortener.entities.ShortnerUrlEntity;
import com.demo.URL.Shortener.repository.ShortnerRepository;
import com.demo.URL.Shortener.service.impl.ShortnerServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
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
    private ShortnerRepository repository;

    @InjectMocks
    private ShortnerServiceImpl shortnerService;

    private String shortCode;
    private ShortnerUrlDto shortnerUrlDto;
    private ShortnerUrlEntity shortnerUrlEntity;

    @BeforeAll
    public void setup() {
        String url = "localhost:8080";
        this.shortCode = "abc1";
        this.shortnerUrlDto = new ShortnerUrlDto(url);
        this.shortnerUrlEntity = ShortnerUrlEntity.builder()
                .shortnerUrlId(1L)
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
        given(repository.findAll()).willReturn(List.of(this.shortnerUrlEntity));
        List<ShortnerUrlEntity> shortnerUrlEntities = this.shortnerService.getUrls();

        assertThat(shortnerUrlEntities).isNotEmpty();
        assertThat(shortnerUrlEntities.size()).isGreaterThan(0);
    }


    @Order(2)
    @Test
    public void createUrl() {
        given(repository.save(any(ShortnerUrlEntity.class))).willReturn(this.shortnerUrlEntity);
        ShortnerUrlEntity shortnerUrlEntity1 = this.shortnerService.createUrl(this.shortnerUrlDto);

        assertThat(shortnerUrlEntity1).isNotNull();
    }

    @Order(3)
    @Test
    public void getUrl() {
        given(repository.findByShortCode(this.shortnerUrlEntity.getShortCode())).willReturn(this.shortnerUrlEntity);
        Optional<ShortnerUrlEntity> shortnerUrlEntity1 = this.shortnerService.getUrl(this.shortnerUrlEntity.getShortCode());

        assertThat(shortnerUrlEntity1).isPresent();
    }

    @Order(4)
    @Test
    public void changeUrl() {
        given(repository.findByShortCode(this.shortnerUrlEntity.getShortCode())).willReturn(this.shortnerUrlEntity);

        this.shortnerUrlEntity.updateUrl("localhost:1234");
        given(repository.save(this.shortnerUrlEntity)).willReturn(this.shortnerUrlEntity);

        Optional<ShortnerUrlEntity> shortnerUrlEntity1 = this.shortnerService.changeUrl(this.shortCode, "localhost:1234");

        assertThat(shortnerUrlEntity1).isPresent();
    }

    @Order(5)
    @Test
    public void deleteUrl() {
        given(repository.findByShortCode(this.shortnerUrlEntity.getShortCode())).willReturn(this.shortnerUrlEntity);
        willDoNothing().given(repository).delete(this.shortnerUrlEntity);

        boolean result = this.shortnerService.deleteUrl(this.shortnerUrlEntity.getShortCode());
        Assert.isTrue(result, "Entity was not deleted!");
    }

}
