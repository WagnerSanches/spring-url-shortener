package com.demo.URL.Shortener.controller;

import com.demo.URL.Shortener.dtos.ErrorResponseDTO;
import com.demo.URL.Shortener.exceptions.URLNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ShortnerExceptionController {

    @ExceptionHandler(URLNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> urlNotFoundException(URLNotFoundException URLNotFoundException) {
        return new ResponseEntity<>(ErrorResponseDTO.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .status("NOT FOUND")
                .message("ShortCode does not exist!")
                .timestamp(LocalDateTime.now())
                .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> exception(Exception exception) {
        return new ResponseEntity<>(ErrorResponseDTO.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .status("INTERNAL SERVER ERROR")
                .timestamp(LocalDateTime.now())
                .stacktrace(exception.getMessage())
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }



}
