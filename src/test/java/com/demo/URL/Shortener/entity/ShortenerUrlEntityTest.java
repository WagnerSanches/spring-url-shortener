package com.demo.URL.Shortener.entity;

import com.demo.URL.Shortener.dto.ShortenerUrlDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.demo.URL.Shortener.entity.ShortenerUrlEntity.of;
import static org.assertj.core.api.Assertions.assertThat;

public class ShortenerUrlEntityTest {

    @Test
    public void shortenerUrlEntity_NoArgsConstructor() {
        ShortenerUrlEntity shortenerUrlEntity = new ShortenerUrlEntity();
        assertThat(shortenerUrlEntity).isNotNull();
    }

    @Test
    public void shortenerUrlEntity_Builder() {
        String url = "localhost";
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
    public void shortenerUrlEntity_of() {
        String url = "localhost";
        ShortenerUrlDto shortenerUrlDto = new ShortenerUrlDto(url);
        ShortenerUrlEntity shortenerUrl = of(shortenerUrlDto);

        assertThat(shortenerUrl).isNotNull();
        assertThat(shortenerUrl.getUrl()).isEqualTo(url);
    }

    @Test
    public void shortenerUrlEntity_updateUrl() {
        String url = "localhost";
        ShortenerUrlDto shortenerUrlDto = new ShortenerUrlDto(url);
        ShortenerUrlEntity shortenerUrl = of(shortenerUrlDto);

        String newUrl = "localhost:10";
        shortenerUrl.updateUrl(newUrl);

        assertThat(shortenerUrl).isNotNull();
        assertThat(shortenerUrl.getUrl()).isNotEqualTo(url);
        assertThat(shortenerUrl.getUrl()).isEqualTo(newUrl);
    }

    @Test
    public void shortenerUrlEntity_incrementAccessCount() {
        ShortenerUrlDto shortenerUrlDto = new ShortenerUrlDto("localhost");
        ShortenerUrlEntity shortenerUrl = of(shortenerUrlDto);

        assertThat(shortenerUrl).isNotNull();
        assertThat(shortenerUrl.getAccessCount()).isEqualTo(0);

        shortenerUrl.incrementAccessCount();
        assertThat(shortenerUrl.getAccessCount()).isEqualTo(1);

    }
}
