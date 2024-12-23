package com.demo.URL.Shortener.dto;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class ErrorResponseDTOTest {

    @Test
    public void errorResponseDTOBuilder() {
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .status("NOT FOUND")
                .message("ShortCode does not exist")
                .timestamp(LocalDateTime.now())
                .build();

        assertThat(errorResponseDTO).isNotNull();
        assertThat(errorResponseDTO.getCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(errorResponseDTO.getMessage()).isEqualTo("ShortCode does not exist");
    }
}
