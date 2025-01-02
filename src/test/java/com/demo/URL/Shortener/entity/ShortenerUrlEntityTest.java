package com.demo.URL.Shortener.entity;

import com.demo.URL.Shortener.dto.ShortenerUrlDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class ShortenerUrlEntityTest {

    @Test
    public void shortnerUrlEntity_NoArgsConstructor() {
        ShortenerUrlEntity shortenerUrlEntity = new ShortenerUrlEntity();
        assertThat(shortenerUrlEntity).isNotNull();
    }

    @Test
    public void shortnerUrlEntity_Builder() {
        String url = "locahost";
        String shortcode = "a123";

        ShortenerUrlEntity shortenerUrlEntity = ShortenerUrlEntity.builder()
                .url(url)
                .shortCode(shortcode)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .accessCount(0)
                .build();

        assertThat(shortenerUrlEntity.getUrl()).isEqualTo(url);
        assertThat(shortenerUrlEntity.getShortCode()).isEqualTo(shortcode);
    }

    @Test
    public void shortnerUrlEntity_of() {
        String url = "localhost";
        ShortenerUrlDto shortnerUrlDto = new ShortenerUrlDto(url);
        ShortenerUrlEntity shortnerUrl = ShortenerUrlEntity.of(shortnerUrlDto);

        assertThat(shortnerUrl).isNotNull();
        assertThat(shortnerUrl.getUrl()).isEqualTo(url);
    }

    @Test
    public void shortnerUrlEntity_updateUrl() {
        String url = "localhost";
        ShortenerUrlDto shortnerUrlDto = new ShortenerUrlDto(url);
        ShortenerUrlEntity shortnerUrl = ShortenerUrlEntity.of(shortnerUrlDto);

        String newurl = "localhost:10";
        shortnerUrl.updateUrl(newurl);

        assertThat(shortnerUrl).isNotNull();
        assertThat(shortnerUrl.getUrl()).isNotEqualTo(url);
        assertThat(shortnerUrl.getUrl()).isEqualTo(newurl);
    }

    @Test
    public void shortnerUrlEntity_incrementAccessCount() {
        ShortenerUrlDto shortnerUrlDto = new ShortenerUrlDto("locahost");
        ShortenerUrlEntity shortnerUrl = ShortenerUrlEntity.of(shortnerUrlDto);

        assertThat(shortnerUrl).isNotNull();
        assertThat(shortnerUrl.getAccessCount()).isEqualTo(0);

        shortnerUrl.incrementAccessCount();
        assertThat(shortnerUrl.getAccessCount()).isEqualTo(1);

    }
}
