package com.demo.URL.Shortener.entity;

import com.demo.URL.Shortener.dto.ShortnerUrlDto;
import com.demo.URL.Shortener.util.ShortCodeGeneratorUtil;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class ShortnerUrlEntityTest {

    @Test
    public void shortnerUrlEntity_NoArgsConstructor() {
        ShortnerUrlEntity shortnerUrlEntity = new ShortnerUrlEntity();
        assertThat(shortnerUrlEntity).isNotNull();
    }

    @Test
    public void shortnerUrlEntity_Builder() {
        String url = "locahost";
        String shortcode = "a123";

        ShortnerUrlEntity shortnerUrlEntity = ShortnerUrlEntity.builder()
                .url(url)
                .shortCode(shortcode)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .accessCount(0)
                .build();

        assertThat(shortnerUrlEntity.getUrl()).isEqualTo(url);
        assertThat(shortnerUrlEntity.getShortCode()).isEqualTo(shortcode);
    }

    @Test
    public void shortnerUrlEntity_of() {
        String url = "localhost";
        ShortnerUrlDto shortnerUrlDto = new ShortnerUrlDto(url);
        ShortnerUrlEntity shortnerUrl = ShortnerUrlEntity.of(shortnerUrlDto);

        assertThat(shortnerUrl).isNotNull();
        assertThat(shortnerUrl.getUrl()).isEqualTo(url);
    }

    @Test
    public void shortnerUrlEntity_updateUrl() {
        String url = "localhost";
        ShortnerUrlDto shortnerUrlDto = new ShortnerUrlDto(url);
        ShortnerUrlEntity shortnerUrl = ShortnerUrlEntity.of(shortnerUrlDto);

        String newurl = "localhost:10";
        shortnerUrl.updateUrl(newurl);

        assertThat(shortnerUrl).isNotNull();
        assertThat(shortnerUrl.getUrl()).isNotEqualTo(url);
        assertThat(shortnerUrl.getUrl()).isEqualTo(newurl);
    }

    @Test
    public void shortnerUrlEntity_incrementAccessCount() {
        ShortnerUrlDto shortnerUrlDto = new ShortnerUrlDto("locahost");
        ShortnerUrlEntity shortnerUrl = ShortnerUrlEntity.of(shortnerUrlDto);

        assertThat(shortnerUrl).isNotNull();
        assertThat(shortnerUrl.getAccessCount()).isEqualTo(0);

        shortnerUrl.incrementAccessCount();
        assertThat(shortnerUrl.getAccessCount()).isEqualTo(1);

    }
}
