package com.demo.URL.Shortener.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ShortenerUrlDtoTest {

    @Test
    public void shortenerUrlDtoTest_allArgs() {
        String url = "localhost";
        ShortenerUrlDto shortenerUrlDto = new ShortenerUrlDto(url);

        assertThat(shortenerUrlDto.getUrl()).isEqualTo(url);
    }

    @Test
    public void shortenerUrlDtoTest_noArgs() {
        ShortenerUrlDto shortenerUrlDto = new ShortenerUrlDto();

        assertThat(shortenerUrlDto).isNotNull();
    }
}
