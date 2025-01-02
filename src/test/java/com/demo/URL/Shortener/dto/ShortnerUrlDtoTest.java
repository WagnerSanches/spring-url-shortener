package com.demo.URL.Shortener.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ShortnerUrlDtoTest {

    @Test
    public void shortnerUrlDtoTest_allArgs() {
        String url = "localhost";
        ShortenerUrlDto shortnerUrlDto = new ShortenerUrlDto(url);

        assertThat(shortnerUrlDto.getUrl()).isEqualTo(url);
    }

    @Test
    public void shortnerUrlDtoTest_noArgs() {
        ShortenerUrlDto shortnerUrlDto = new ShortenerUrlDto();

        assertThat(shortnerUrlDto).isNotNull();
    }
}
